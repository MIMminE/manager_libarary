package nuts.lib.manager.security_manager.user;

import jakarta.transaction.Transactional;
import nuts.lib.manager.security_manager.user.configurer.AuthorityTableInfoConfigurer;
import nuts.lib.manager.security_manager.user.configurer.UserTableInfoConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;
import java.util.Map;

public class JdbcUserDetailsManager implements UserDetailsManager {

    private final JdbcTemplate jdbcTemplate;
    private final UserTableInfoConfigurer userTableInfo;
    private final AuthorityTableInfoConfigurer authorityTableInfo;


    public JdbcUserDetailsManager(JdbcTemplate jdbcTemplate, UserTableInfoConfigurer userTableInfo, AuthorityTableInfoConfigurer authorityTableInfo) {
        this.jdbcTemplate = jdbcTemplate;
        this.userTableInfo = userTableInfo;
        this.authorityTableInfo = authorityTableInfo;
    }

    @Transactional
    @Override
    public void createUser(UserDetails user) {

        String userTableName = userTableInfo.getTableName();
        String userNameField = userTableInfo.getUserNameField();
        String passWordField = userTableInfo.getPassWordField();
        String enabledField = userTableInfo.getEnabledField();

        String authorityTableName = authorityTableInfo.getAuthorityTableName();
        String authorityTableUserNameField = authorityTableInfo.getUserNameField();
        String authorityField = authorityTableInfo.getAuthorityField();


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
