package edu.umich.med.michr.scheduling.service.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.umich.med.michr.scheduling.dao.UserDao;
import edu.umich.med.michr.scheduling.domain.User;
import edu.umich.med.michr.scheduling.domain.security.AuthenticationUserDetails;

/**
 * Service class to manage Spring security authentication operations and to fetch logged in user information from Spring Security or underlying user account data store. 
 * @author Melih Gunal
 *
 */
@Service("springLoginService")
public class SpringSecurityUserLoginService implements UserLoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(SpringSecurityUserLoginService.class);
	
	@Inject @Named("userDao")
	private UserDao userDao;
	@Inject @Named("authManager")
	private AuthenticationManager authenticationManager;
	private static final String internalHashKeyForAutomaticLoginAfterRegistration = "magicInternalHashKeyForAutomaticLoginAfterRegistration";

	@Override
	public boolean login (String login, String password) throws DisabledException, LockedException, BadCredentialsException{
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login,
						password));
		boolean isAuthenticated = isAuthenticated(authentication);
		if (isAuthenticated) {
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
			logger.debug("User is authenticated and security context is set!");
		}
		return isAuthenticated;
	}

	@Override
	public boolean login(Long userId) {
		boolean isLoginSuccesfull = false;
		User user = userDao.getById(userId);
		if (user != null) {
			AuthenticationUserDetails userDetails = new AuthenticationUserDetails(user);
			final RememberMeAuthenticationToken rememberMeAuthenticationToken = new RememberMeAuthenticationToken(
					internalHashKeyForAutomaticLoginAfterRegistration,
					userDetails, null);
			rememberMeAuthenticationToken.setAuthenticated(true);
			SecurityContextHolder.getContext().setAuthentication(rememberMeAuthenticationToken);
			logger.debug("Remember me authentication token is set within the security context");
			isLoginSuccesfull = true;
		}
		return isLoginSuccesfull;
	}

	@Override
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	@Override
	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return isAuthenticated(authentication);
	}

	@Override
	public AuthenticationUserDetails getAuthUserDetails() {
		
		AuthenticationUserDetails loggedUserDetails = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (isAuthenticated(authentication)) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof AuthenticationUserDetails) {
				loggedUserDetails = ((AuthenticationUserDetails) principal);
			} else {
				throw new RuntimeException(
						"Expected class of authentication principal is AuthenticationUserDetails. Given: "
								+ principal.getClass());
			}
		}
		return loggedUserDetails;
	}

	private boolean isAuthenticated(Authentication authentication) {
		return authentication != null
				&& !(authentication instanceof AnonymousAuthenticationToken)
				&& authentication.isAuthenticated();
	}

	@Override
	public User getLoggedUser() {

		User loggedUser = null;
		AuthenticationUserDetails userAccount = getAuthUserDetails();
		logger.debug("getLoggedUserDetails method returned:"+userAccount);
		if (userAccount != null) {
			loggedUser = userDao.getById(userAccount.getId());
		}
		return loggedUser;
	}
	@Override
	public String getHomePageURLForUser() {
		String homePage = "/";
		switch (getAuthUserDetails().getRole()) {
		case Administrator:
			homePage = "secure/admin/home";
			break;
		case Organization_Admin:
			homePage = "secure/orgadmin/home";
			break;
		case Organization_Scheduler:
			homePage = "secure/orgscheduler/home";
			break;
		case External_Scheduler:
			homePage = "secure/home";
			break;
		default:
			throw new RuntimeException("User has to be assigned a role.");
		}
		return homePage;
	}
}
