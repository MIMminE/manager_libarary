package nuts.lib.manager.security_manager.user;

import nuts.lib.commom.configurer.AnnotationVerificationBuilder;
import nuts.lib.commom.infra.Configurer;
import nuts.lib.manager.security_manager.user.configurer.AuthorityTableInfoConfigurer;
import nuts.lib.manager.security_manager.user.configurer.UserTableInfoConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;

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

    public JdbcUserDetailsManager build(JdbcTemplate jdbcTemplate) {
        verify();
        return new JdbcUserDetailsManager(jdbcTemplate, userTableInfoConfigurer, authorityTableInfoConfigurer);
    }

    public JdbcUserDetailsManager build(DataSource dataSource) {
        verify();
        return new JdbcUserDetailsManager(new JdbcTemplate(dataSource), userTableInfoConfigurer, authorityTableInfoConfigurer);
    }

    @Override
    protected Supplier<Map<Object, String>> setVerification() {
        return () -> Map.of(
                this.userTableInfoConfigurer, "userTableInfoConfigurer [ Method : userTableInfo ]",
                this.authorityTableInfoConfigurer, "authorityTableInfoConfigurer [ Method : authorityTableInfo ]");
    }

}