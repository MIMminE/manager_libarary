package nuts.lib.manager.security_manager.user;

import nuts.lib.commom.configurer.AnnotationVerificationBuilder;
import nuts.lib.commom.infra.Configurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Supplier;

public class JdbcUserDetailsManagerBuilder extends AnnotationVerificationBuilder {
    private final UserTableInfoConfigurer userTableInfoConfigurer = new UserTableInfoConfigurer();
    private final AuthorityTableInfoConfigurer authorityTableInfoConfigurer = new AuthorityTableInfoConfigurer();

    public JdbcUserDetailsManagerBuilder userTableInfo(Configurer<UserTableInfoConfigurer> configurer) {
        configurer.config(userTableInfoConfigurer);
        return this;
    }

    public JdbcUserDetailsManagerBuilder authorityTableInfo(Configurer<AuthorityTableInfoConfigurer> configurer) {
        configurer.config(authorityTableInfoConfigurer);
        return this;
    }

    public JdbcUserDetailsManager build(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        verify();
        return new JdbcUserDetailsManager(jdbcTemplate, transactionTemplate, userTableInfoConfigurer, authorityTableInfoConfigurer);
    }

    public JdbcUserDetailsManager build(DataSource dataSource, TransactionTemplate transactionTemplate) {
        verify();
        return new JdbcUserDetailsManager(new JdbcTemplate(dataSource), transactionTemplate, userTableInfoConfigurer, authorityTableInfoConfigurer);
    }

    @Override
    protected Supplier<Map<Object, String>> setVerification() {
        return () -> Map.of(
                this.userTableInfoConfigurer, "userTableInfoConfigurer [ Method : userTableInfo ]",
                this.authorityTableInfoConfigurer, "authorityTableInfoConfigurer [ Method : authorityTableInfo ]");
    }

}