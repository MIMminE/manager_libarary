package nuts.lib.manager.security_manager.user;

import nuts.lib.manager.security_manager.user.configurer.AuthorityTableInfoConfigurer;
import nuts.lib.manager.security_manager.user.configurer.UserTableInfoConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

public class JdbcUserDetailsManager implements UserDetailsManager {

    private final JdbcTemplate jdbcTemplate;
    private final UserTableInfoConfigurer userTableInfo;
    private final AuthorityTableInfoConfigurer authorityTableInfo;

    public JdbcUserDetailsManager(JdbcTemplate jdbcTemplate, UserTableInfoConfigurer userTableInfo, AuthorityTableInfoConfigurer authorityTableInfo) {
        this.jdbcTemplate = jdbcTemplate;
        this.userTableInfo = userTableInfo;
        this.authorityTableInfo = authorityTableInfo;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
