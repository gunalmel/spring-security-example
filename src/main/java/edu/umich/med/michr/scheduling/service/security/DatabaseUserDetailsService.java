package edu.umich.med.michr.scheduling.service.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.umich.med.michr.scheduling.dao.UserDao;
import edu.umich.med.michr.scheduling.domain.User;
import edu.umich.med.michr.scheduling.domain.security.AuthenticationUserDetails;

/**
 * {@link org.springframework.security.core.userdetails.UserDetailsService} implementation to be consumed by Spring Security Authentication filters.
 * @author Melih Gunal
 *
 */
@Service ("authUserDetailsGetter")
public class DatabaseUserDetailsService implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(DatabaseUserDetailsService.class);
	
	@Inject @Named ("userDao")
	UserDao userDao;

	protected DatabaseUserDetailsService() {}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		AuthenticationUserDetails aud=null;
		User u = userDao.getByUserName(username);
		if (u==null)
			throw new UsernameNotFoundException("User with the specified username: "+username+" has not been found.");
		aud =new AuthenticationUserDetails(u);
		return aud;
	}
}
