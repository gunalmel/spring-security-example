package edu.umich.med.michr.scheduling.helper;

import edu.umich.med.michr.scheduling.domain.Role;

/**
 * Helper class to be used by @Type annotation to map Role enum to the database
 * using enum integer values.
 * 
 * @author Melih Gunal
 * 
 */
public class RoleUserType extends PersistentEnumUserType<Role> {

	@Override
	public Class<Role> returnedClass() {
		return Role.class;
	}
}
