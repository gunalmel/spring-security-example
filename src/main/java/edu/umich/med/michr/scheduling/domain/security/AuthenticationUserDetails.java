package edu.umich.med.michr.scheduling.domain.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.umich.med.michr.scheduling.domain.Role;
import edu.umich.med.michr.scheduling.domain.User;

/**
 * Lightweight immutable spring security authenticated user details object. If
 * the properties of this object is not enough then heavyweight User class can
 * be used to retrieve all required properties for a user. However, for
 * authentication & authorization purposes most of the time this lightweight
 * class will be good enough.
 * 
 * @author Melih Gunal
 * 
 */
public class AuthenticationUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;
	private final String login;
	private final String passwordSalt;
	private final String passwordHash;
	private final boolean enabled;
	private final boolean isAccountNonExpired;
	private final boolean isAccountNonLocked;
	private final boolean isCredentialsNonExpired;
	private final Role role;
	private HashSet<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

	public AuthenticationUserDetails(User user) {
		this.id = user.getId();
		this.login = user.getUsername();
		this.passwordSalt = user.getPasswordSalt();
		this.passwordHash = user.getPassword();
		this.enabled = user.isEnabled();
		this.isAccountNonExpired = user.isAccountNonExpired();
		this.isAccountNonLocked = user.isAccountNonLocked();
		this.isCredentialsNonExpired = user.isCredentialsNonExpired();
		if (user.getAuthorities() != null && user.getAuthorities().size() > 0) {
			this.grantedAuthorities.addAll(user.getAuthorities());
			role = user.getRole();
		} else
			role = null;
	}

	public long getId() {
		return id;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	@Override
	public String getPassword() {
		return passwordHash;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public String getLogin() {
		return login;
	}

	public Role getRole() {
		return role;
	}
}
