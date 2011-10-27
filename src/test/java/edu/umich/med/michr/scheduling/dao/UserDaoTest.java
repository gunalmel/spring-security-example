package edu.umich.med.michr.scheduling.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Ignore;
import org.junit.Test;
public class UserDaoTest extends AbstractDaoTest {
	
	@Inject @Named("userDao")
	private UserDao userDao;
	private int numAll=10;
	private int numEnabled=9;
	private int numDisabled=1;
	private int numLocked=2;
	private int numAccExpired=1;
	private int numPwdExpired=1;
	private String expectedUserName="administrator";

	@Test
	public void testGetAll() {
		assertEquals(numAll,userDao.getAll().size());
	}
	
	@Test
	public void testGetById() {
		assertEquals(expectedUserName,userDao.getById(0L).getUsername());
	}
	
	@Test
	public void testGetByUserName() {
		assertNotNull(userDao.getByUserName(expectedUserName));
		// Test dual role assignment using enumeration of roles.
		assertEquals(2,userDao.getByUserName(expectedUserName).getRoles().size());
	}

	@Test
	public void testGetEnabled() {
		assertEquals(numEnabled,userDao.getEnabled().size());
	}

	@Test
	public void testGetDisabled() {
		assertEquals(numDisabled,userDao.getDisabled().size());
	}

	@Test
	public void testGetLocked() {
		assertEquals(numLocked,userDao.getLocked().size());
	}

	@Test
	public void testGetAccountExpired() {
		assertEquals(numAccExpired,userDao.getAccountExpired().size());
	}

	@Test
	public void testGetPasswordExpired() {
		assertEquals(numPwdExpired,userDao.getPasswordExpired().size());
	}

	@Test @Ignore
	public void testGetByExample() {
		fail("Not yet implemented");
	}

	@Test @Ignore
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test @Ignore
	public void testHardDelete() {
		fail("Not yet implemented");
	}

	@Test @Ignore
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test @Ignore
	public void testGetByPropertyValues() {
		fail("Not yet implemented");
	}
}
