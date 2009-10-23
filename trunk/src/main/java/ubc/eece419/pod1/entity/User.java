package ubc.eece419.pod1.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.security.userdetails.UserDetails;

import ubc.eece419.pod1.security.Roles;
import ubc.eece419.pod1.validator.SupressEntityValidation;

@Entity
public class User extends AbstractEntity<User> implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String roles = Roles.USER; // default to least privilege
	private String email;
	private String address;

	public User() {
	}

	public User(String username, String plaintextPassword, String roles) {
		this.username = username;
		this.password = encryptPassword(plaintextPassword, username);
		this.roles = roles;
	}

	@Column(unique=true, nullable=false)
	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@SupressEntityValidation
	@Column(nullable = false)
	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// NG: did we want to use email as username?
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	@Override
	public GrantedAuthority[] getAuthorities() {
		String[] roleNames = roles.split("[,\\s]+"); // split on commas, whitespace
		GrantedAuthority[] authorities = new GrantedAuthority[roleNames.length];
		for (int i = 0; i < roleNames.length; i++) {
			authorities[i] = new GrantedAuthorityImpl(roleNames[i]);
		}
		Arrays.sort(authorities);
		return authorities;
	}

	@Transient
	public boolean isAdmin() {
		return roles.contains(Roles.ADMIN);
	}

	@Transient
	public boolean isStaff() {
		return roles.contains(Roles.STAFF);
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return true;
	}
}
