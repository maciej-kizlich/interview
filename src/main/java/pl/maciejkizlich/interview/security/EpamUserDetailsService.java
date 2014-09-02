package pl.maciejkizlich.interview.security;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EpamUserDetailsService extends JdbcDaoImpl {


    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[]{username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                long userId = rs.getLong(4);
                return new EpamUserDetails(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES, userId);
            }

        });
    }

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {

        EpamUserDetails eud = (EpamUserDetails) userFromUserQuery;

        return new EpamUserDetails(
                eud.getUsername(),
                eud.getPassword(),
                eud.isEnabled(),
                eud.isAccountNonExpired(),
                eud.isCredentialsNonExpired(),
                eud.isAccountNonLocked(),
                combinedAuthorities,
                eud.getUserId()
        );

    }
}
