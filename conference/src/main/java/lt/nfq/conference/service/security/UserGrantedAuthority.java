package lt.nfq.conference.service.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Used by spring security framework. Returns users role
 */
public class UserGrantedAuthority implements GrantedAuthority {
	private String authority;

	public UserGrantedAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

}
