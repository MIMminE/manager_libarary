package nuts.lib.manager.security_manager.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

public class JdbcUserDetailsManager implements UserDetailsManager {

    public static JdbcUserDetailsManagerBuilder builder = new JdbcUserDetailsManagerBuilder();
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;
    private final UserTableInfoConfigurer userTableInfoConfigurer;
    private final AuthorityTableInfoConfigurer authorityTableInfoConfigurer;
    private final PasswordEncoder passwordEncoder;

    public JdbcUserDetailsManager(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate, UserTableInfoConfigurer userTableInfoConfigurer, AuthorityTableInfoConfigurer authorityTableInfoConfigurer) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
        this.userTableInfoConfigurer = userTableInfoConfigurer;
        this.authorityTableInfoConfigurer = authorityTableInfoConfigurer;
    }

    @Override
    public void createUser(UserDetails user) {

        String userTableName = userTableInfoConfigurer.getTableName();
        String userNameField = userTableInfoConfigurer.getUserNameField();
        String passWordField = userTableInfoConfigurer.getPassWordField();
        String enabledField = userTableInfoConfigurer.getEnabledField();

        String authorityTableName = authorityTableInfoConfigurer.getAuthorityTableName();
        String authorityTableUserNameField = authorityTableInfoConfigurer.getUserNameField();
        String authorityField = authorityTableInfoConfigurer.getAuthorityField();

        transactionTemplate.execute(status ->
                {
                    boolean active = TransactionSynchronizationManager.isActualTransactionActive();
                    System.out.println(active);
                    if (jdbcTemplate.queryForList("SELECT %s from %s where %s = ?"
                            .formatted(userNameField, userTableName, userNameField), user.getUsername()).isEmpty()) {

                        jdbcTemplate.update("insert into %s (%s, %s, %s) VALUES (?, ?, ?)"
                                        .formatted(userTableName, userNameField, passWordField, enabledField),
                                user.getUsername(), user.getPassword(), user.isEnabled());

                        for (GrantedAuthority authority : user.getAuthorities())
                            jdbcTemplate.update("insert into %s (%s, %s) VALUES (?, ?)"
                                            .formatted(authorityTableName, authorityTableUserNameField, authorityField),
                                    user.getUsername(), authority.getAuthority());

                    } else
                        throw new RuntimeException("This username already exists.");
                    return null;
                }
        );
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
