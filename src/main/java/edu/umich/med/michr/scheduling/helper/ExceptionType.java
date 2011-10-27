package edu.umich.med.michr.scheduling.helper;

/**
 * @author Melih Gunal
 * Enumeration used by custom exception wrappers that translates checked exceptions into unchecked exceptions.
 */
public enum ExceptionType {
	GET_GETTER_EXCEPTION,INVOKE_GETTER_EXCEPTION,GET_SETTER_EXCEPTION,INVOKE_SETTER_EXCEPTION,FIELD_TYPE_EXCEPTION,GET_SOME_METHOD_EXCEPTION
}
