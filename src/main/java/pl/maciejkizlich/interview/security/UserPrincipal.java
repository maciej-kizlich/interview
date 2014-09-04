package pl.maciejkizlich.interview.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@SuppressWarnings("serial")
public class UserPrincipal extends User {

	private Long userId;

	public UserPrincipal(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, Long userId) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);

		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public static UserPrincipal getCurrentLogged() {
		return (UserPrincipal) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

	public static long getLoggedUserId() {
		return getCurrentLogged().getUserId();
	}
}
