package edu.umich.med.michr.scheduling.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.umich.med.michr.scheduling.dao.UserDao;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value="/secure")
public class HomeController {
	@Inject @Named ("userDao")
	UserDao userDao;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "admin/home", method = RequestMethod.GET)
	public String adminHome(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("users", userDao.getAll());
		return "secure/Administrator/home";
	}
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String userHome(Locale locale, Model model) {
		return "secure/home";
	}
}
