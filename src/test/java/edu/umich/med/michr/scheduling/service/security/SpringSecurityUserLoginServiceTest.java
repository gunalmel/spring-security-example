package edu.umich.med.michr.scheduling.service.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "classpath:SpringContextSetUp/test-context-withDb-Security.xml" })
//Needed for being able to inject beans in the application context, TransactionalTestExecutionListener is needed to be able to mark test methods as transactional to bundle multiple dao method executions in a single transction (also helps avoiding lazy initialization errors)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,TransactionalTestExecutionListener.class })
//Needed for making test methods participate in the same transactions with the dao methods being called.
//@Rollback(true), @NotTransactional & @AfterTransaction are important attributes to control transactions within tests.
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") // defaultRollback makes sure that after the test method ended the transaction is rolled back.
@Transactional(propagation=Propagation.REQUIRED)
public class SpringSecurityUserLoginServiceTest {
	
	@Inject @Named("springLoginService")
	private UserLoginService ul;
	
	private String userName="administrator";
	private String password="manager";
	private String nonExistentUser="nouser";
	private String wrongPwd="wrong!";
	private String lockedUser="test_locked";
	private String disabledUser="test_disabled";
	
	public SpringSecurityUserLoginServiceTest() {}
	@Before
	public void setUp() throws Exception {	
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testLogin() {
		assertTrue(ul.login(userName, password));
		assertTrue(ul.login(0L));
		assertFalse(ul.login(20L));
	}
	@Test(expected=org.springframework.security.authentication.BadCredentialsException.class)
	public void testLoginWrongPwd(){
		ul.login(userName, wrongPwd);
	}
	@Test(expected=org.springframework.security.authentication.BadCredentialsException.class)
	public void testLoginWrongUserName(){
		ul.login(nonExistentUser, password);
	}
	@Test(expected=org.springframework.security.authentication.LockedException.class)
	public void testLoginLocked(){
		ul.login(lockedUser, "");
	}
	@Test(expected=org.springframework.security.authentication.DisabledException.class)
	public void testLoginDisabled(){
		ul.login(disabledUser, "");
	}
	@Test
	public void testLogoutAndIsLoggedIn(){
		ul.login(userName, password);
		assertTrue(ul.isLoggedIn());
		ul.logout();
		assertFalse(ul.isLoggedIn());
		
	}
	@Test
	public void testGetAuthUserDetails(){
		assertNull(ul.getAuthUserDetails());
		ul.login(userName, password);
		assertNotNull(ul.getAuthUserDetails());
		ul.logout();
	}
	@Test
	public void testGetLoggedUser(){
		assertNull(ul.getLoggedUser());
		ul.login(userName, password);
		assertNotNull(ul.getLoggedUser());
		ul.logout();
	}
}
