package nuts.lib.manager.security_manager.user.jdbc.builder;

import nuts.lib.commom.configurer.Configurer;
import nuts.lib.commom.configurer.RequiredVerificationBuilder;
import nuts.lib.manager.security_manager.crypto.PasswordEncoderSupport;
import nuts.lib.manager.security_manager.user.jdbc.JdbcUserDetailsManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.function.Supplier;

import static nuts.lib.manager.security_manager.user.jdbc.builder.JdbcUserDetailsManagerTableConfigurer.AuthorityTableConfigurer;
import static nuts.lib.manager.security_manager.user.jdbc.builder.JdbcUserDetailsManagerTableConfigurer.UserTableConfigurer;


public class JdbcUserDetailsManagerBuilder extends RequiredVerificationBuilder {

    static public UserTableConfigurer userTableDefault = UserTableConfigurer.withDefault();
    static public AuthorityTableConfigurer authorityTableDefault = AuthorityTableConfigurer.withDefault();

    private UserTableConfigurer userTableConfigurer;
    private AuthorityTableConfigurer authorityTableConfigurer;

    public JdbcUserDetailsManagerBuilder userTableConfig(UserTableConfigurer userTableConfigurer) {
        this.userTableConfigurer = userTableConfigurer;
        return this;
    }

    public JdbcUserDetailsManagerBuilder authorityTableConfig(AuthorityTableConfigurer authorityTableConfigurer) {
        this.authorityTableConfigurer = authorityTableConfigurer;
        return this;
    }


    public JdbcUserDetailsManager build(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate, PasswordEncoderSupport defaultEncoder) {
        verify();
        return new JdbcUserDetailsManager(jdbcTemplate, transactionTemplate, userTableConfigurer, authorityTableConfigurer, defaultEncoder);
    }

    public JdbcUserDetailsManager build(DataSource dataSource, TransactionTemplate transactionTemplate, PasswordEncoderSupport defaultEncoder) {
        verify();
        return new JdbcUserDetailsManager(new JdbcTemplate(dataSource), transactionTemplate, userTableConfigurer, authorityTableConfigurer, defaultEncoder);
    }

    @Override
    protected Supplier<HashMap<String, Object>> setVerification() {
        return () -> new HashMap<>() {{
            put("userTableConfigurer", userTableConfigurer);
            put("authorityTableConfigurer", authorityTableConfigurer);
        }};
    }

    /**
     * High complexity due to inappropriate builder patterns.
     */
    @Deprecated(since = "2024. 06. 13")
    public JdbcUserDetailsManagerBuilder userTableInfo(Configurer<UserTableConfigurer> configurer) {
        configurer.config(userTableConfigurer);
        return this;
    }

    /**
     * High complexity due to inappropriate builder patterns.
     */
    @Deprecated(since = "2024. 06. 13")
    public JdbcUserDetailsManagerBuilder authorityTableInfo(Configurer<AuthorityTableConfigurer> configurer) {
        configurer.config(authorityTableConfigurer);
        return this;
    }
}