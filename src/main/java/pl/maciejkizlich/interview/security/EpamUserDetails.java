package pl.maciejkizlich.interview.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class EpamUserDetails extends User {

    private Long userId;

    public EpamUserDetails(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            long userId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.userId = userId;
    }

    public EpamUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, long userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public static EpamUserDetails getCurrentLogged() {
        return (EpamUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getLoggedUserId(){
        return getCurrentLogged().getUserId();
    }


}
