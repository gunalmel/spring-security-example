package edu.umich.med.michr.scheduling.web;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.umich.med.michr.scheduling.domain.User;
import edu.umich.med.michr.scheduling.service.security.UserLoginService;

@Controller
@RequestMapping(value = "/")
public class LoginController extends AbstractController {
	private static final Logger logger = getLogger();

	@Inject
	@Named("springLoginService")
	private UserLoginService loginService;

	@ModelAttribute(value = "user")
	public UserDetails newRequest() {
		return (new User());
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String displayLogin(Locale locale) {
		logger.debug("Welcome login page! the client locale is "+ locale.toString());
		return "public/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") UserDetails user, BindingResult result) {
		if (loginService.isLoggedIn())
			loginService.logout();
		if (result.hasErrors())
			return "public/login";
		try {
			loginService.login(user.getUsername(), user.getPassword());
		} catch (AuthenticationException ex) {
			logger.info("Authentication failed: ", ex);
		}
		return "redirect:"+loginService.getHomePageURLForUser();
	}


}
