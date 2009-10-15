package ubc.eece419.pod1.security;

// this should be an enum, or Set<GrantedAuthority>, or something
// but everybody wants strings
public interface Roles {
	String ADMIN = "ROLE_ADMIN";
	String STAFF = "ROLE_STAFF";
	String USER = "ROLE_USER";
}
