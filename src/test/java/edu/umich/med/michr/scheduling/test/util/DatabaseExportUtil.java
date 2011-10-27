package edu.umich.med.michr.scheduling.test.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatDtdWriter;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * Make sure spring-hibernate.xml datasource points to correct source database before running.
 * Uses DbUnit to generate test data from a pre populated db. 
 * Generates 2 files: 1. DTD file describing target db schema to be exported. When a dataset xml file is to be validated against this file the order of the elements within DTD file will not be important
 * 2. Dataset XML file: The DbUnit specific test data that is generated. The elements of the xml file will be sorted with respect to the order of foreign keys imposed on the tables to avoid foreign key violation errors when DbUnit is inserting those elements into test db.   
 * @author Melih Gunal
 *
 */
public class DatabaseExportUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseExportUtil.class);
	
	private String springContextFile ="DbPrototype/protoTypeDbConnection-Context.xml";
	private String dataSourceBeanName="dataSourceProdDesign";
	private String testDbSchema="PUBLIC";
	private String dbUnitDataSetFileName="src/test/resources/DbUnitDataSets/testDbFull.xml";
	private String dtdFileName="src/test/resources/DbUnitDataSets/testDb.dtd";
	private DataSource dataSource;
	private IDatabaseConnection dbUnitConnection;

	public static void main(String[] args) throws Exception{
		DatabaseExportUtil exporter = new DatabaseExportUtil();
		exporter.dtdExport();
		exporter.fullExport();
	}
    private DatabaseExportUtil() throws Exception{
    	logger.info("Spring context file within the classpath to export db schema and data set is: "+springContextFile);
    	dataSource = (DataSource)new ClassPathXmlApplicationContext(springContextFile).getBean(dataSourceBeanName);
    	logger.info("Data source bean is fetched as specified in the specified spring context file is: "+dataSourceBeanName);
    	logger.info("Connection default schema set to: "+testDbSchema);
    	logger.info("Connection data type factory set to: "+H2DataTypeFactory.class.getName());
    	dbUnitConnection = new DatabaseConnection(DataSourceUtils.getConnection(dataSource),testDbSchema);
    	DatabaseConfig config= dbUnitConnection.getConfig();
    	config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
    	logger.info("Database connection for DbUnit extraction process is established.");
    }
	/**
	 * DbUnit extracts the whole data in the target database into a flat XML file that it can consume to set up a test Db.
	 */
	protected void fullExport() throws Exception{
		// Use filter to order tables by foreign keys so that when importing using dbunit foreign key violations can be avoided.
		ITableFilter filter = new DatabaseSequenceFilter(dbUnitConnection);
		IDataSet fullDataSet = new FilteredDataSet(filter, dbUnitConnection.createDataSet());
		
		FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream(dbUnitDataSetFileName)); 
		datasetWriter.setDocType("testDb.dtd");
		datasetWriter.write(fullDataSet);
		logger.info("DbUnit data set file is exported to: "+dbUnitDataSetFileName);
		// If you are not going to order dataset by foreign keys to avoid foreign key violations during import then you can use the following single line of code instead of the code block above.
        //FlatXmlDataSet.write(fullDataSet, new FileOutputStream(exportPath+"fullTestDbExport.xml"));
	}
	/**
	 * DbUnit selectively exports data from target database into a flat xml file.	 
	 */
	protected void partialExport() throws Exception{
		//QueryDataSet partialDataSet = new QueryDataSet(dbUnitConnection);
        //partialDataSet.addTable("FOO", "SELECT * FROM TABLE WHERE COL='VALUE'");
        //partialDataSet.addTable("BAR");
        //FlatXmlDataSet.write(partialDataSet, new FileOutputStream(exportPath+"partialExport.xml"));
	}
	/**
	 * dependent tables database export: export table X and all tables that have a PK which is a FK on X, in the right order for insertion
	 */
	protected void dependentExport() throws Exception{
//        String[] depTableNames = 
//        		TablesDependencyHelper.getAllDependentTables( dbUnitConnection, "tabelNameForWhichDependentsWillBeExtracted" );
//        IDataSet depDataSet = dbUnitConnection.createDataSet( depTableNames );
//        FlatXmlDataSet.write(depDataSet, new FileOutputStream(exportPath+"dependents.xml")); 
	}
	protected void dtdExport() throws Exception{
		
		IDataSet dataset = dbUnitConnection.createDataSet();
		Writer out = new OutputStreamWriter(new FileOutputStream(dtdFileName));
		FlatDtdWriter datasetWriter = new FlatDtdWriter(out);
		datasetWriter.setContentModel(FlatDtdWriter.CHOICE);
		logger.info("DTD content model is set to choice so that data set file records do not need to appear in the same order as table definitions appear in the referenced dtd file.");
		datasetWriter.write(dataset);
		logger.info("DTD file that is going to be referenced by the data set file is exported to: "+dtdFileName);
		
		// The following one line of code is good when the order of tables in dtd is not important. However, if you are ordering your extracted dataset with respect to order of tables by foreign keys you have tou use the above piece of code to generate dtd by choice so that order of elements in dtd will not mandate the order in the dataset. 
		//FlatDtdDataSet.write(dbUnitConnection.createDataSet(), new FileOutputStream(exportPath+"testDb.dtd"));
	}
}
