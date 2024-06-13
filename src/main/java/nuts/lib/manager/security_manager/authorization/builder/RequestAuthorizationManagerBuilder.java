package nuts.lib.manager.security_manager.authorization.builder;

import nuts.lib.commom.configurer.Configurer;
import nuts.lib.commom.configurer.RequiredVerificationBuilder;
import nuts.lib.manager.security_manager.authorization.RequestMatcherDelegator;
import nuts.lib.manager.security_manager.authorization.RequestAuthorizationManager;
import nuts.lib.manager.security_manager.authorization.mapper.JdbcBasedRoleMappingService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.function.Supplier;

public class RequestAuthorizationManagerBuilder extends RequiredVerificationBuilder {

    private JdbcBasedRoleMappingServiceConfigurer jdbcBasedRoleMapperConfigurer;
    private boolean onDefaultDecisionPermitAll = false;

    public RequestAuthorizationManagerBuilder defaultPermitAll() {
        this.onDefaultDecisionPermitAll = true;
        return this;
    }

    public RequestAuthorizationManagerBuilder jdbcRoleMapperConfig(JdbcBasedRoleMappingServiceConfigurer jdbcBasedRoleMapperConfigurer) {
        this.jdbcBasedRoleMapperConfigurer = jdbcBasedRoleMapperConfigurer;
        return this;
    }

    public RequestAuthorizationManager build(DataSource dataSource, HandlerMappingIntrospector handlerMappingIntrospector) {
        RequestMatcherDelegator delegateRequestMatcher = new RequestMatcherDelegator(handlerMappingIntrospector);
        JdbcBasedRoleMappingService mappingService = new JdbcBasedRoleMappingService(new JdbcTemplate(dataSource),
                this.jdbcBasedRoleMapperConfigurer.getTableName(),
                this.jdbcBasedRoleMapperConfigurer.getUrlPattenField(),
                this.jdbcBasedRoleMapperConfigurer.getRoleField());

        RequestAuthorizationManager authorizationManager = new RequestAuthorizationManager(delegateRequestMatcher, mappingService);
        if (this.onDefaultDecisionPermitAll) authorizationManager.defaultPermission();

        return authorizationManager;
    }

    @Override
    protected Supplier<HashMap<String, Object>> setVerification() {
        return () -> new HashMap<>() {{
            put("jdbcBasedRoleMapperConfigurer", jdbcBasedRoleMapperConfigurer);
        }};
    }

    /**
     * High complexity due to inappropriate builder patterns.
     */
    @Deprecated(since = "2024. 06. 13")
    public RequestAuthorizationManagerBuilder mappedDatabaseConfig(Configurer<JdbcBasedRoleMappingServiceConfigurer> configurer) {
//        this.jdbcBasedRoleMapperConfigurer = new JdbcBasedRoleMappingServiceConfigurer();
        configurer.config(jdbcBasedRoleMapperConfigurer);
        return this;
    }

    /**
     * High complexity due to inappropriate builder patterns.
     */
    @Deprecated(since = "2024. 06. 13")
    public RequestAuthorizationManagerBuilder mappedDatabaseConfig(String tableName, String urlPattenField, String roleField) {
//        this.jdbcBasedRoleMapperConfigurer = new JdbcBasedRoleMappingServiceConfigurer();
        this.jdbcBasedRoleMapperConfigurer.tableName(tableName).urlPattenField(urlPattenField).roleField(roleField);
        return this;
    }


}
