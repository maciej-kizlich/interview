package pl.maciejkizlich.interview.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.maciejkizlich.interview.persistence.dao.UserRepository;
import pl.maciejkizlich.interview.persistence.model.Authority;
import pl.maciejkizlich.interview.persistence.model.User;

public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		if (null == username) {
			throw new UsernameNotFoundException("User for username " + username
					+ "was not found.");
		}

		User user = userRepository.findUserByLogin(username);

		if (null == user) {
			throw new UsernameNotFoundException("User for username " + username
					+ "was not found.");
		}

		Set<Authority> permissions = user.getAuthorities();

		if (permissions.isEmpty()) {
			throw new UsernameNotFoundException(username
					+ "has no permissions.");

		}

		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();

		for (Authority permission : permissions) {
			authorities.add(new SimpleGrantedAuthority(permission
					.getAuthority()));

		}

		return new UserPrincipal(user.getUsername(), user.getPassword(), true,
				true, true, true, authorities, user.getId());
	}
}
