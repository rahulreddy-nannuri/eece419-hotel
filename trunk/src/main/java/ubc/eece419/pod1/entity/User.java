package ubc.eece419.pod1.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.security.userdetails.UserDetails;

@Entity
public class User extends AbstractEntity<User> implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Column(unique=true)
	private String username;
	private String password;
	private String roles;

	public User() {
	}

	public User(String username, String plaintextPassword, String roles) {
		this.username = username;
		this.password = encryptPassword(plaintextPassword, username);
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// this must match the encoder & salt set an applicationContext.xml
	// using the username as salt means we can't allow users to change their username
	public static String encryptPassword(String password, String username) {
		return new ShaPasswordEncoder(256).encodePassword(password, username);
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Transient
	public GrantedAuthority[] getAuthorities() {
		String[] roleNames = roles.split("[,\\s]+"); // split on commas, whitespace
		GrantedAuthority[] authorities = new GrantedAuthority[roleNames.length];
		for (int i = 0; i < roleNames.length; i++) {
			authorities[i] = new GrantedAuthorityImpl(roleNames[i]);
		}
		Arrays.sort(authorities);
		return authorities;
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
