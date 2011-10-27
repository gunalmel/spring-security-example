package edu.umich.med.michr.scheduling.service.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "classpath:SpringContextSetUp/test-context-noDb-noSecurity.xml" })
//Needed for being able to inject beans in the application context
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class})
public class EncryptionServiceTest {

	@Inject
	@Named("passwordEncoder")
	private EncryptionService service;
	private String pwd = "myPassword";
	private String randomSalt ="f791a45d034da2e1";
	private String expectedEncPwd = "3e1fdb24fe1105b4649c2e5392d70e3c55e4b811fa74f6667dbaa13cedd34a11";

	@Test
	public void testGenerateSalt() {
		String salt = service.generateSalt(); 
		assertEquals(8,Hex.decode(salt).length);
	}

	@Test
	public void testEncodePasswordString() {
		Map<String,String> pwdMap = service.encodePassword(pwd);
		assertNotNull(pwdMap.get("salt"));
		assertNotNull(pwdMap.get("password"));
	}

	@Test
	public void testEncodePasswordStringString() {
		String encPwd = service.encodePassword(pwd, randomSalt);
		assertEquals(expectedEncPwd,encPwd);
	}

	@Test
	public void testVerifyPwd() {
		assertTrue(service.isPasswordValid(expectedEncPwd, pwd, randomSalt));
	}
}
