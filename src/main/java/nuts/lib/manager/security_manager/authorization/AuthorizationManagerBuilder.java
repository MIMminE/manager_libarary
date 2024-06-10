package nuts.lib.manager.security_manager.authorization;

import nuts.lib.commom.configurer.AnnotationVerificationBuilder;
import nuts.lib.commom.infra.Configurer;
import nuts.lib.manager.security_manager.authorization.role_mapper.JdbcBasedRoleMapperConfigurer;
import nuts.lib.manager.security_manager.authorization.role_mapper.JdbcBasedRoleMappingService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Supplier;

public class AuthorizationManagerBuilder extends AnnotationVerificationBuilder {

    private JdbcBasedRoleMapperConfigurer jdbcBasedRoleMapperConfigurer;
    private boolean onDefaultDecisionPermitAll = false;

    public AuthorizationManagerBuilder mappedDatabaseConfig(Configurer<JdbcBasedRoleMapperConfigurer> configurer) {
        this.jdbcBasedRoleMapperConfigurer = new JdbcBasedRoleMapperConfigurer();
        configurer.config(jdbcBasedRoleMapperConfigurer);
        return this;
    }

    public AuthorizationManagerBuilder mappedDatabaseConfig(String tableName, String urlPattenField, String roleField) {
        this.jdbcBasedRoleMapperConfigurer = new JdbcBasedRoleMapperConfigurer();
        this.jdbcBasedRoleMapperConfigurer.tableName(tableName).urlPattenField(urlPattenField).roleField(roleField);
        return this;
    }

    public AuthorizationManagerBuilder defaultPermitAll(){
        this.onDefaultDecisionPermitAll = true;
        return this;
    }

    public RequestAuthorizationManager build(DataSource dataSource, HandlerMappingIntrospector handlerMappingIntrospector) {
        DelegateRequestMatcher delegateRequestMatcher = new DelegateRequestMatcher(handlerMappingIntrospector);
        JdbcBasedRoleMappingService mappingService = new JdbcBasedRoleMappingService(new JdbcTemplate(dataSource),
                this.jdbcBasedRoleMapperConfigurer.getTableName(),
                this.jdbcBasedRoleMapperConfigurer.getUrlPattenField(),
                this.jdbcBasedRoleMapperConfigurer.getRoleField());

        RequestAuthorizationManager authorizationManager = new RequestAuthorizationManager(delegateRequestMatcher, mappingService);
        if (this.onDefaultDecisionPermitAll) authorizationManager.defaultPermission();

        return authorizationManager;
    }

    @Override
    protected Supplier<Map<Object, String>> setVerification() {

        return () -> Map.of(this.jdbcBasedRoleMapperConfigurer, "userTableInfoConfigurer [ Method : mappedDatabaseConfig ");
    }
}
