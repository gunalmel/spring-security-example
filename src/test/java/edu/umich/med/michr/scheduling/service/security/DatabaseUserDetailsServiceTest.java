package edu.umich.med.michr.scheduling.service.security;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.umich.med.michr.scheduling.dao.UserDao;
import edu.umich.med.michr.scheduling.domain.User;

@RunWith (MockitoJUnitRunner.class)
public class DatabaseUserDetailsServiceTest {

	@Mock UserDao uDao;
	@InjectMocks UserDetailsService udService=new DatabaseUserDetailsService();
	private String userName="administrator";
	
	@Test
	public void testLoadUserByUsername() {
		User u = new User();
		u.setUsername("administrator");
		when(uDao.getByUserName(eq(userName))).thenReturn(u);
		udService.loadUserByUsername(userName);
	}
	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUserNameUserNotFound(){
		when(uDao.getByUserName(eq(userName))).thenReturn(null);
		udService.loadUserByUsername(userName);
		System.out.println("x".equals(null));
	}
}
