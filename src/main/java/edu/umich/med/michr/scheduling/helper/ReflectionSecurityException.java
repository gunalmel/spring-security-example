package edu.umich.med.michr.scheduling.helper;

/**
 * @author Melih Gunal
 * Wrapper exception for translating checked SecurityException to unchecked exception.
 */
public class ReflectionSecurityException extends RuntimeException {

	private static final long serialVersionUID = -2517612522594090304L;

	/**
	 * Accepts an error code implying the root cause of exception being thrown. Allowed set of inputs are enumerated in the form of static constant values within this class. e.g.: GET_GETTER_EXCEPTION
	 * @param errorCause A numeric exception code that determines exception message to be displayed. Constants exposed as static values within this class enumerates the possible set of values.
	 * @param cause Root cause exception of this exception.
	 */
	public ReflectionSecurityException(ExceptionType errorCause,Throwable cause){
		super(getErrorMessage(errorCause,cause),cause);
	}
	
	private static String getErrorMessage(ExceptionType errorCause, Throwable cause){
		switch (errorCause) {
		case GET_GETTER_EXCEPTION:
			return "Security exception while retrieving getter method: "+cause.getMessage();
		case INVOKE_GETTER_EXCEPTION:
			return "Security exception while invoking getter method: "+cause.getMessage();
		case GET_SETTER_EXCEPTION:
			return "Security exception while retrieving setter method: "+cause.getMessage();
		case INVOKE_SETTER_EXCEPTION:
			return "Security exception while invoking setter method: "+cause.getMessage();
		case FIELD_TYPE_EXCEPTION:
			return "Security exception while accessing field type: "+cause.getMessage();
		default:
			return "No exception message is available check cause.";
		}
	}
}
