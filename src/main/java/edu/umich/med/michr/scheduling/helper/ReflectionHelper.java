package edu.umich.med.michr.scheduling.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Melih Gunal
 * Helper class that is used to reflect on business entity and data access objects supporting abstract data access object methods.
 */
@SuppressWarnings("unchecked")
public class ReflectionHelper {
	static Logger logger = LoggerFactory.getLogger("ReflectionHelper");
	/**
	 * Extracts the primary identifier value assigned to a persistent hibernate object. First searches for a getter method called getId by convention and if it can not find a method then it will search for a getter method "get<FiledName>" annotated with javax.persistence.Id annotation. If all fails will return null.
	 * @param <TEntity> Type of the object whose primary identifier values is to be extracted
	 * @param <TPK> Type of the primary identifier for the persistent object
	 * @param e Persistent entity object whose primary identifier value is to be extracted.
	 * @return Primary identifier value for the persistent object specified as method argument. Returns null if it can not find a way to determine the value for primary key.
	 */
	public static <TEntity,TPK extends Serializable> TPK getPrimaryKeyValueForEntity(TEntity e){
		TPK id;
		Class<TEntity> clazz = (Class<TEntity>) e.getClass();
		Method m = getPrimaryKeyGetterMethod(clazz);
		if(m==null)
			return null;
		try {
			id = (TPK)m.invoke(e);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException("The getter method may be expecting an input argument: "+ex.getMessage());
		} catch (IllegalAccessException ex) {
			throw new RuntimeException("Security exception while reflection is trying to access the getId method of the target object");
		} catch (InvocationTargetException ex) {
			throw new RuntimeException("Getter method for the target object has thrown an exception: "+ex.getMessage());
		}
		return id;
	}
	private static <TEntity> Method getPrimaryKeyGetterMethod(
			Class<TEntity> clazz) {
		Method m = null;
		try{
			m= getGetIdMethod(clazz);
		}catch(ReflectionNoSuchMethodException ex){
			m=getPrimaryKeyGetterOrSetter(clazz);
		}
		return m;
	}
	/**
	 * Retrieves getId method on the object of the class for invoking that method to retrieve primary key value.
	 * @param clazz Entity class for which getId method is going to be searched
	 * @return returns getId method for the given identity assuming entity conforms with the convention that getter for primary key is named as getId
	 */
	private static <TEntity> Method getGetIdMethod(Class<TEntity> clazz){
		Method m=null;
		try {
			m = clazz.getMethod("getId", new Class[]{});
		} catch (SecurityException ex) {
			throw new ReflectionSecurityException(ExceptionType.GET_GETTER_EXCEPTION,ex);
		} catch (NoSuchMethodException ex) {
			throw new ReflectionNoSuchMethodException(ExceptionType.GET_GETTER_EXCEPTION,ex);
		}
		return m;
	}
	/**
	 * Scans through all methods of a given class, returns the method with javax.persistence.Id annotation
	 * @param clazz Class type whose primary key getter is going to be returned.
	 * @return Getter method which had javax.persistence.Id Returns null if the method is not starting with get.
	 */
	private static <TEntity> Method getPrimaryKeyGetterOrSetter(Class<TEntity> clazz){	
		Method[] methods = clazz.getMethods();
		Method keyGetter=null;
		for (Method meth : methods){
			Annotation[] annotations = meth.getAnnotations();
			for(Annotation ann: annotations)
				if(ann instanceof javax.persistence.Id){
					keyGetter=meth;
					break;
				}
			if(keyGetter!=null)
				break;
		}
		if(keyGetter!=null&&keyGetter.getName().indexOf("get")==-1)
			keyGetter=null;
		return keyGetter;
	}
	/**
	 * Returns a Class object that identifies the declared type for the field with the field name specified.
	 * @param clazz Class for which the field type with the fieldName would be retrieved
	 * @param fieldName Name of the field whose type is going to be retrieved.
	 * @return Type of the field specified by fieldName argument of the persistent object of the type specified by the clazz argument.
	 */
	public static Class<?> getFieldClassType(Class<?> clazz,String fieldName){
		try {
			return clazz.getDeclaredField(fieldName).getType();
		} catch (SecurityException ex) {
			throw new ReflectionSecurityException(ExceptionType.FIELD_TYPE_EXCEPTION,ex);
		} catch (NoSuchFieldException ex) {
			try{
				return clazz.getSuperclass().getDeclaredField(fieldName).getType();
			} catch (SecurityException exSub) {
				throw new ReflectionSecurityException(ExceptionType.FIELD_TYPE_EXCEPTION,ex);
			}catch (NoSuchFieldException exSub) {
				throw new RuntimeException("Target object class or its immediate parent class does not have the field "+fieldName);
			}
		}
	}
	/**
	 * Sets the property which is identified by pName for the object e to the value implied by value parameter.
	 * @e The object whose property is to be set
	 * @pName Name of the property of the object e.
	 * @value The value which the property is to be set to.
	 */
	public static <TEntity> void setProperty(TEntity e, String pName, Object value){
		Class<TEntity> clazz = (Class<TEntity>) e.getClass();
		Class<?> fieldType = getFieldClassType(e.getClass(),pName);
		Method setter =null;
		
		try {
			setter = clazz.getMethod("set"+initCap(pName), new Class[]{fieldType});
		} catch (SecurityException ex) {
			throw new ReflectionSecurityException(ExceptionType.GET_SETTER_EXCEPTION,ex);
		} catch (NoSuchMethodException ex) {
			throw new ReflectionNoSuchMethodException(ExceptionType.GET_SETTER_EXCEPTION,ex);
		}
		try {
			setter.invoke(e, value);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException("Invoking Method Exception: Illegal argument has been provided to setter method.");
		} catch (IllegalAccessException ex) {
			throw new RuntimeException("Invoking Method Exception: Security exception has been raised while invoking the setter method.");
		} catch (InvocationTargetException ex) {
			throw new RuntimeException("Invoking Method Exception: The object may not have the setter method to be invoked.");
		}
	}
	/**
	 * Sets the properties of an object implied by the keys of the Map parameter to the values corresponding to the keys of that Mapp 
	 * @param <TEntity> The type of the object for which properties are going to be set
	 * @param e Object whose properties are to be set
	 * @param pMap Map storing the key value pairs whose keys represent property names.
	 */
	public static <TEntity> void setProperties(TEntity e, Map<String,Object> pMap){
		for(Map.Entry<String, Object> entry:pMap.entrySet()){
			setProperty(e,entry.getKey(),entry.getValue());
		}
	}
	/**
	 * Capitalizes the first letter of a given string
	 * @param s The string whose first letter is to be capitalized
	 * @return The string with the first letter capitalized.
	 */
	private static String initCap(String s){	
		logger.debug("initCap is being called to capitalize the first letter of the string parameter most probably to generate getter or setter method name from field name");
		return Character.toUpperCase(s.charAt(0))+s.substring(1);
	}
}
