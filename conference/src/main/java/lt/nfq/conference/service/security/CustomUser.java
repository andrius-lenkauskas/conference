package lt.nfq.conference.service.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Used by spring security framework
 */
public class CustomUser implements UserDetails {
	private long id;
	private String password;
	private String username;
	private List<UserGrantedAuthority> authorities;

	public CustomUser(int id, String username, String password, List<UserGrantedAuthority> authorities) {
		this.id = id;
		this.password = password;
		this.username = username;
		this.authorities = authorities;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}
