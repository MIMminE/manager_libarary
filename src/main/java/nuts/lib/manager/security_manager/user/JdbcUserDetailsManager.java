package nuts.lib.manager.security_manager.user;

import jakarta.transaction.Transactional;
import nuts.lib.manager.security_manager.user.configurer.AuthorityTableInfoConfigurer;
import nuts.lib.manager.security_manager.user.configurer.UserTableInfoConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

public class JdbcUserDetailsManager implements UserDetailsManager {

    public static JdbcUserDetailsManagerBuilder builder = new JdbcUserDetailsManagerBuilder();
    private final JdbcTemplate jdbcTemplate;
    private final UserTableInfoConfigurer userTableInfoConfigurer;
    private final AuthorityTableInfoConfigurer authorityTableInfoConfigurer;


    JdbcUserDetailsManager(JdbcTemplate jdbcTemplate, UserTableInfoConfigurer userTableInfoConfigurer, AuthorityTableInfoConfigurer authorityTableInfoConfigurer) {
        this.jdbcTemplate = jdbcTemplate;
        this.userTableInfoConfigurer = userTableInfoConfigurer;
        this.authorityTableInfoConfigurer = authorityTableInfoConfigurer;
    }

    @Transactional
    @Override
    public void createUser(UserDetails user) {

        String userTableName = userTableInfoConfigurer.getTableName();
        String userNameField = userTableInfoConfigurer.getUserNameField();
        String passWordField = userTableInfoConfigurer.getPassWordField();
        String enabledField = userTableInfoConfigurer.getEnabledField();

        String authorityTableName = authorityTableInfoConfigurer.getAuthorityTableName();
        String authorityTableUserNameField = authorityTableInfoConfigurer.getUserNameField();
        String authorityField = authorityTableInfoConfigurer.getAuthorityField();


        if (!jdbcTemplate.queryForMap("SELECT %s from %s where %s = %s"
                .formatted(userNameField, userTableName, userNameField, user.getUsername())).isEmpty()) {

            jdbcTemplate.execute("insert into %s (%s, %s, %s) VALUES (%s, %s, %s)".formatted(userTableName, userNameField, passWordField, enabledField, user.getPassword(), user.isEnabled()));

            for (GrantedAuthority authority : user.getAuthorities())
                jdbcTemplate.execute("insert into %s (%s %s) VALUES (%s %s)".formatted(authorityTableName, authorityTableUserNameField, authorityField, user.getUsername(), authority.getAuthority()));

        } else
            throw new RuntimeException("This ID already exists.");

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
