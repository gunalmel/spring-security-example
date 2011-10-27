/**
 * 
 */
package edu.umich.med.michr.scheduling.helper;

import java.io.Serializable;

/**
 * Interface to be implemented by Hibernate Custom User Types for enum mapping and enum itself.
 * Helps persisting with integer id value for the enum using Hibernate.
 * 
 * @author Melih Gunal
 * 
 */
public interface PersistentEnum extends Serializable{
	/**
	 * Integer value representing enumeration in the database.
	 * 
	 * @return Id assigned to the enumeration
	 */
	int getId();
}
