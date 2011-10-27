/**
 * 
 */
package edu.umich.med.michr.scheduling.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

import edu.umich.med.michr.scheduling.helper.PersistentEnum;

/**
 * Role enumeration to be used with role string comparison. Role ids and names
 * should match the entries in the Role table for people who are going to access
 * the db directly from the backend. This enumeration is also being used by
 * Spring Security to represent roles in the form of GrantedAuthority
 * @author Melih Gunal
 * 
 */
public enum Role implements PersistentEnum, GrantedAuthority {
	Administrator(0), Organization_Admin(1), Organization_Scheduler(2), External_Scheduler(
			3);
	private static final long serialVersionUID = 1L;
	private final int id;
	private static final Map<Integer, Role> idMap = new HashMap<Integer, Role>();
	static {
		for (Role role : Role.values()) {
			idMap.put(role.getId(), role);
		}
	}

	Role(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public static Role valueById(int id) {
		return idMap.get(id);
	}

	public static List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(Role.valueById(Administrator.getId()));
		roles.add(Role.valueById(Organization_Admin.getId()));
		roles.add(Role.valueById(Organization_Scheduler.getId()));
		roles.add(Role.valueById(External_Scheduler.getId()));
		return roles;
	}

	@Override
	public String getAuthority() {
		return this.toString();
	}
}
