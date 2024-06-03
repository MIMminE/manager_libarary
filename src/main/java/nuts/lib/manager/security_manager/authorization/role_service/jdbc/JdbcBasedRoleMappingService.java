package nuts.lib.manager.security_manager.authorization.role_service.jdbc;

import nuts.lib.commom.configurer.JdbcTemplateConfigurer;
import nuts.lib.commom.infra.Configurer;
import nuts.lib.manager.security_manager.authorization.role_service.RoleMappingService;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JdbcBasedRoleMappingService implements RoleMappingService {

    public static JdbcBasedRoleMappingServiceBuilder builder = new JdbcBasedRoleMappingServiceBuilder();
    private final JdbcTemplate jdbcTemplate;
    private final String tableName;
    private final String urlPattenField;
    private final String roleField;

    private JdbcBasedRoleMappingService(JdbcTemplate jdbcTemplate, String tableName, String urlPattenField, String roleField) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
        this.urlPattenField = urlPattenField;
        this.roleField = roleField;
    }

    @Override
    public Map<String, String> getRoleMappings() {
        Map<String, String> roleMappings = new HashMap<>();

        List<Map<String, Object>> result = jdbcTemplate.queryForList("select %s, %s from %s".formatted(urlPattenField, roleField, tableName));

        result.forEach(e -> roleMappings.put(e.get(urlPattenField).toString(), e.get(roleField).toString()));

        return roleMappings;
    }


    static public class JdbcBasedRoleMappingServiceBuilder {

        private JdbcTemplateConfigurer jdbcTemplateConfigurer;
        private JdbcBasedRoleMapperConfigurer jdbcRoleMapperConfigConfigurer;


        public JdbcBasedRoleMappingServiceBuilder dateSource(Configurer<JdbcTemplateConfigurer> configurer) {
            this.jdbcTemplateConfigurer = new JdbcTemplateConfigurer();
            configurer.config(jdbcTemplateConfigurer);
            return this;
        }

        public JdbcBasedRoleMappingServiceBuilder MappedDatabaseConfig(Configurer<JdbcBasedRoleMapperConfigurer> configurer) {
            this.jdbcRoleMapperConfigConfigurer = new JdbcBasedRoleMapperConfigurer();
            configurer.config(jdbcRoleMapperConfigConfigurer);
            return this;
        }

        public JdbcBasedRoleMappingService build() {
            if (jdbcTemplateConfigurer == null || jdbcRoleMapperConfigConfigurer == null)
                throw new IllegalStateException("jdbcTemplateConfigurer, jdbcRoleMapperConfigConfigurer are required.");

            return new JdbcBasedRoleMappingService(
                    jdbcTemplateConfigurer.getJdbcTemplate(),
                    jdbcRoleMapperConfigConfigurer.tableName,
                    jdbcRoleMapperConfigConfigurer.urlPattenField,
                    jdbcRoleMapperConfigConfigurer.roleField);
        }
    }
}
