package edu.umich.med.michr.scheduling.helper;
/**
 * Exception wrapper that is utilized by ReflectionHelper class.
 * @author gunalmel
 *
 */
public class ReflectionNoSuchMethodException extends RuntimeException {

	private static final long serialVersionUID = 2616876300533962081L;

	public ReflectionNoSuchMethodException(ExceptionType errorCause, Throwable cause) {
		super(getMessage(errorCause,cause), cause);
	}

	private static String getMessage(ExceptionType errorCause, Throwable cause) {
		switch (errorCause) {
		case GET_GETTER_EXCEPTION:
			return "No such getter method exists for the given field: "+cause.getMessage();
		case GET_SETTER_EXCEPTION:
			return "No such setter method exists for the given field: "+cause.getMessage();
		case GET_SOME_METHOD_EXCEPTION:
			return "No such method exists: "+cause.getMessage();
		default:
			return "No exception message is available check cause.";
		}
	}

}
