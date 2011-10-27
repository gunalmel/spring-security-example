package edu.umich.med.michr.scheduling.service.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import edu.umich.med.michr.scheduling.domain.User;
import edu.umich.med.michr.scheduling.domain.security.AuthenticationUserDetails;

public interface UserLoginService {
	/**
	 * Heavyweight method to get logged authentication information. This method may be
	 * touching the database, ergo it's heavy. Use getLoggedUserDetails for fast
	 * and light access to logged authentication data.
	 */
	User getLoggedUser();

	/**
	 * Lightweight method to get currently logged authentication details
	 */
	AuthenticationUserDetails getAuthUserDetails();

	/**
	* Method used to login following user registration. Id will become available only after user creation
	*/
	boolean login (Long userId) throws DisabledException, LockedException, BadCredentialsException;
	
	/**
	 * Logs user in using Spring Security 
	 * @param login User name.
	 * @param password Password
	 * @return Whether user is successfully logged in or not.
	 */
	boolean login (String login, String password) throws DisabledException, LockedException, BadCredentialsException;

	/**
	 * logs user out and invalidates session
	 */
	void logout();

	/**
	 * Checks whether user is logged in.
	 * @return True if user is logged in.
	 */
	boolean isLoggedIn();

	/**
	 * For the user who is logged in, depending on the user role assigned returns a string that will be used by Spring Controllers to specify a view as described by spring view resolver configuration.
	 * @return View string that will be used by Spring controllers to refer to role specific home page for the user.
	 */
	String getHomePageURLForUser();
}
