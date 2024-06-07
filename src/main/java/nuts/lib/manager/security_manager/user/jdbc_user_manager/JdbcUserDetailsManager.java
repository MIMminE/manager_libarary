package nuts.lib.manager.security_manager.user.jdbc_user_manager;

import lombok.extern.slf4j.Slf4j;
import nuts.lib.manager.security_manager.crypto.PasswordEncoderSupplier;
import nuts.lib.manager.security_manager.crypto.PasswordEncoderSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;


@Slf4j
public class JdbcUserDetailsManager implements UserDetailsService {

    public static JdbcUserDetailsManagerBuilder builder = new JdbcUserDetailsManagerBuilder();
    protected final JdbcTemplate jdbcTemplate;
    protected final TransactionTemplate transactionTemplate;
    protected final DelegatingPasswordEncoder passwordEncoder;

    // Custom Variable
    protected final String userTableName;
    protected final String userNameField;
    protected final String passWordField;
    protected final String enabledField;

    protected final String authorityTableName;
    protected final String authorityTableUserNameField;
    protected final String authorityField;

    public JdbcUserDetailsManager(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                                  UserTableInfoConfigurer userTableInfoConfigurer, AuthorityTableInfoConfigurer authorityTableInfoConfigurer,
                                  PasswordEncoderSupport defaultEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
        this.passwordEncoder = PasswordEncoderSupplier.delegatingPasswordEncoder(defaultEncoder);

        this.userTableName = userTableInfoConfigurer.getTableName();
        this.userNameField = userTableInfoConfigurer.getUserNameField();
        this.passWordField = userTableInfoConfigurer.getPassWordField();
        this.enabledField = userTableInfoConfigurer.getEnabledField();

        this.authorityTableName = authorityTableInfoConfigurer.getAuthorityTableName();
        this.authorityTableUserNameField = authorityTableInfoConfigurer.getUserNameField();
        this.authorityField = authorityTableInfoConfigurer.getAuthorityField();

    }

    public void createUser(JdbcUserDetails user) {
        if (this.userExists(user.getUsername())) throw new RuntimeException("This username already exists.");

        transactionTemplate.execute(status ->
                {
                    jdbcTemplate.update("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)"
                                    .formatted(userTableName, userNameField, passWordField, enabledField),
                            user.getUsername(), passwordEncoder.encode(user.getPassword()), user.isEnabled());

                    for (GrantedAuthority authority : user.getAuthorities())
                        jdbcTemplate.update("INSERT INTO %s (%s, %s) VALUES (?, ?)"
                                        .formatted(authorityTableName, authorityTableUserNameField, authorityField),
                                user.getUsername(), authority.getAuthority());
                    return null;
                }
        );
    }

    public void createAdminUser(String adminUserName, String adminPassword, String adminRoleName) {
        if (this.userExists(adminUserName)) {
            log.info("The administrator name already exists. [ %s ]".formatted(adminUserName));
            return;
        }

        if (!adminRoleName.startsWith("ROLE"))
            throw new IllegalArgumentException("The name of the permission must start with 'ROLE_'");

        transactionTemplate.execute(status ->
        {
            jdbcTemplate.update("INSERT INTO %s (%s, %s) VALUES (?, ?)".formatted(userTableName, userNameField, passWordField),
                    adminUserName, passwordEncoder.encode(adminPassword));

            jdbcTemplate.update("INSERT INTO %s (%s, %s) VALUES (?, ?)".formatted(authorityTableName, authorityTableUserNameField, authorityField),
                    adminUserName, adminRoleName);

            return null;
        });


    }

    public void updateUser(JdbcUserDetails user) {
        jdbcTemplate.update("UPDATE %s SET %s = ? WHERE %s = ?".formatted(userTableName, enabledField, userNameField), user.isEnabled(), user.getUsername());
    }

    public void deleteUser(String username) {
        if (this.userExists(username)) throw new RuntimeException("There are no users with that name.");

        transactionTemplate.execute(status -> {
                    jdbcTemplate.update("DELETE FROM %s WHERE %s = ?".formatted(userTableName, userNameField), username);
                    jdbcTemplate.update("DELETE FROM %s WHERE %s = ?".formatted(authorityTableName, userNameField), username);
                    return null;
                }
        );

    }

    public void changePassword(String username, String oldPassword, String newPassword) {

        String encryptNewPassword = passwordEncoder.encode(newPassword);


        Map<String, Object> passwordMap = jdbcTemplate.queryForMap("SELECT %s FROM %s WHERE %s = ?".formatted(passWordField, userTableName, userNameField), username);

        if (passwordEncoder.matches(oldPassword, passwordMap.get(passWordField).toString())) {
            transactionTemplate.execute(status -> {
                        int updateCount = jdbcTemplate.update("UPDATE %s SET %s = ? WHERE %s = ?"
                                .formatted(userTableName, passWordField, userNameField), encryptNewPassword, username);
                        if (updateCount != 1)
                            throw new RuntimeException("Invalid request. There is no match, or there are several.");
                        return null;
                    }
            );
        } else throw new RuntimeException("The user information is incorrect.");

    }

    public boolean userExists(String username) {
        return !jdbcTemplate.queryForList("SELECT %s FROM %s WHERE %s = ?".formatted(userNameField, userTableName, userNameField), username).isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> userInfo = jdbcTemplate.queryForMap("SELECT * FROM %s WHERE %s = ?".formatted(userTableName, userNameField), username);
        List<Map<String, Object>> authInfo = jdbcTemplate.queryForList("SELECT * FROM %s WHERE %s = ?".formatted(authorityTableName, authorityTableUserNameField), username);

        List<SimpleGrantedAuthority> authorities = authInfo.stream().map(e
                -> new SimpleGrantedAuthority(e.get(authorityField).toString())).toList();

        return new User(userInfo.get(userNameField).toString(), userInfo.get(passWordField).toString(), authorities);
    }
}
