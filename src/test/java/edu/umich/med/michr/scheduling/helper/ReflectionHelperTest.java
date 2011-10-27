package edu.umich.med.michr.scheduling.helper;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Id;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReflectionHelperTest {
	private static ReflectionTesterClassWithIdAnno t;
	private static ReflectionTesterClassWithoutIdAnno tId;
	private static ReflectionTesterClassWithoutId tNoId;
	// Primary identifier for the user object
	private static String expectedUserName="doejohn";
	private static String expectedUserLastName="Doe";
	private static String expectedEmail="test@yahoo.com";

	@BeforeClass
	public static void globalTestSetup(){
		t=new ReflectionTesterClassWithIdAnno();
		tId = new ReflectionTesterClassWithoutIdAnno();
		tNoId = new ReflectionTesterClassWithoutId();
		
		t.setUserName(expectedUserName);
		t.setLastName(expectedUserLastName);
		t.setEmail(expectedEmail);
		
		tId.setId(expectedUserName);
		tId.setLastName(expectedUserLastName);
		tId.setEmail(expectedEmail);
		
		tNoId.setUserName(expectedUserName);
		tNoId.setLastName(expectedUserLastName);
		tNoId.setEmail(expectedEmail);
	}

	@Test
	public void testGetPrimaryKeyValueForEntity() {
		String actual = ReflectionHelper.<ReflectionTesterClassWithIdAnno,String>getPrimaryKeyValueForEntity(t);
		assertEquals(expectedUserName,actual);
		actual = ReflectionHelper.<ReflectionTesterClassWithoutIdAnno,String>getPrimaryKeyValueForEntity(tId);
		assertEquals(expectedUserName,actual);
		actual = ReflectionHelper.<ReflectionTesterClassWithoutId,String>getPrimaryKeyValueForEntity(tNoId);
		assertEquals(null,actual);
	}

	@Test
	public void testGetFieldClassType() {
		Class<?> clazz = ReflectionHelper.getFieldClassType(ReflectionTesterClassWithIdAnno.class, "userName");
		assertEquals(String.class,clazz);
	}

	@Test
	public void testSetProperty() {
		ReflectionHelper.setProperty(t, "lastName", "test");
		assertEquals("test",t.getLastName());
	}

	@Test
	public void testSetProperties() {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("userName", "testUserName");
		m.put("lastName", "testLastName");
		m.put("email", "testEmail");
		ReflectionHelper.setProperties(t, m);
		assertEquals("testUserName",t.getUserName());
		assertEquals("testLastName",t.getLastName());
		assertEquals("testEmail",t.getEmail());
	}
	
	@Test(expected=ReflectionNoSuchMethodException.class)
	public void testReflectionNoSuchMethodException(){
		ReflectionHelper.setProperty(tNoId, "setterWithoutSetMethod", "fail");
	}
	@Test(expected=RuntimeException.class)
	public void testRuntimeException(){
		ReflectionHelper.getFieldClassType(String.class, "hey");
	}
	
	public static class ReflectionTesterClassWithIdAnno{
		private String userName;
		private String lastName;
		private String email;
		@Id
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
	
	public static class ReflectionTesterClassWithoutIdAnno{
		private String id;
		private String lastName;
		private String email;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
	
	public static class ReflectionTesterClassWithoutId{
		private String userName;
		private String lastName;
		private String email;
		private String setterWithoutSetMethod;
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getSetterWithoutSetMethod() {
			return setterWithoutSetMethod;
		}
		public void putSetterWithoutSetMethod(String setterWithoutSetMethod) {
			this.setterWithoutSetMethod = setterWithoutSetMethod;
		}
		
	}
}
