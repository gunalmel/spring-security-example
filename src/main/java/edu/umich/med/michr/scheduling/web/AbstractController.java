package edu.umich.med.michr.scheduling.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {

	protected static Logger getLogger() {
		final Throwable t = new Throwable();
		return LoggerFactory.getLogger(t.getStackTrace()[1].getClassName());
	}
}
