package ubc.eece419.pod1.security;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;

import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import ubc.eece419.pod1.entity.User;

public abstract class SecurityUtils {

	public static void assertUser() {
		assertHasRole(Roles.USER);
	}

	public static void assertStaff() {
		assertHasRole(Roles.STAFF);
	}

	public static void assertAdmin() {
		assertHasRole(Roles.ADMIN);
	}

	public static boolean isUser() {
		return currentUserHasRole(Roles.USER);
	}

	public static boolean isStaff() {
		return currentUserHasRole(Roles.STAFF);
	}

	public static boolean isAdmin() {
		return currentUserHasRole(Roles.ADMIN);
	}

	public static void assertHasRole(String role) {
		if (!currentUserHasRole(role)) {
			// throwing an AccessDeniedException will redirect to the login page if currently anonymous,
			// else show a 403 error page
			throw new AccessDeniedException("action requires role " + role);
		}
	}

	public static boolean currentUserHasRole(String role) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority ga : auth.getAuthorities()) {
			if (role.equals(ga.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasRole(User user, String role) {
		for (GrantedAuthority ga : user.getAuthorities()) {
			if (role.equals(ga.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	public static User getCurrentUserOrNull() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		}
		return null;
	}

	public static boolean currentUserIsAnonymous() {
		return getCurrentUserOrNull() == null;
	}

	// can't use @Secured here, because these methods are static; accessed outside the spring context
	public static User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		}
		throw new AccessDeniedException("SecurityContextHolder held " + principal + ", not a User");
	}

	public static void login(User user) {
		if (currentUserIsAnonymous()) {
			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword(),user.getAuthorities()));
		}
	}
}
