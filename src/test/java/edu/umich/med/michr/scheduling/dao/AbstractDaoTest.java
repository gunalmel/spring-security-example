package edu.umich.med.michr.scheduling.dao;

import java.io.File;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "classpath:SpringContextSetUp/test-context-withDb.xml" })
// Needed for being able to inject beans in the application context,
// TransactionalTestExecutionListener is needed to be able to mark test methods
// as transactional to bundle multiple dao method executions in a single
// transaction (also helps avoiding lazy initialization errors)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,TransactionalTestExecutionListener.class })
// Needed for making test methods participate in the same transactions with the dao methods being called.
// @Rollback(true), @NotTransactional & @AfterTransaction are important
// attributes to control transactions within tests.
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
// defaultRollback makes sure that after the test method ended the transaction
// is rolled back.
@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractDaoTest {

	protected final String schemaName = "PUBLIC";
	protected final String dbUnitDefaultDataSetFile = "src/test/resources/DbUnitDataSets/testDbFull.xml";

	// Using a single dbunit connection when possible during multiple method
	// testing is recommended.
	protected IDatabaseConnection dbUnitConnection;
	protected IDataSet dataSet;

	@Inject
	@Named("dataSource")
	DataSource dataSource;

	public AbstractDaoTest() {
	}

	public void setUpDbUnit() throws Exception, Exception {
		setUpDbUnit(dbUnitDefaultDataSetFile);
	}

	protected void setUpDbUnit(String dbUnitDataSetFile) throws Exception,
			Exception {
		dbUnitConnection = new DatabaseConnection(
				DataSourceUtils.getConnection(dataSource), schemaName);
		DatabaseConfig dbConfig = dbUnitConnection.getConfig();
		dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new H2DataTypeFactory());
		dataSet = new FlatXmlDataSetBuilder()
				.build(new File(dbUnitDataSetFile));
		DatabaseOperation.INSERT.execute(dbUnitConnection, dataSet);
	}

	public void tearDownDbUnit() throws SQLException, Exception {
		// If you need to clear out dbunit populated data at the end of each
		// test uncomment the code below.
		DatabaseOperation.DELETE.execute(dbUnitConnection, dataSet);
		// Releases the connection but does not close it since method is
		// executed under transaction
		DataSourceUtils.releaseConnection(dbUnitConnection.getConnection(),
				dataSource);
	}

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public abstract void testGetByPropertyValues();

	public abstract void testHardDelete();

	@DirtiesContext
	public abstract void testUpdate();

	@DirtiesContext
	public abstract void testSave();

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public abstract void testGetByExample();

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public abstract void testGetById();

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public abstract void testGetAll();
}