package nuts.lib.manager.security_manager.authorization.role_service.jdbc;

import nuts.lib.commom.configurer.JdbcTemplateConfigurer;
import nuts.lib.commom.infra.Configurer;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcBasedRoleMappingServiceBuilder {

    private JdbcTemplateConfigurer jdbcTemplateConfigurer;
    private JdbcBasedRoleMapperConfigurer jdbcRoleMapperConfigConfigurer;


    public JdbcBasedRoleMappingServiceBuilder jdbcTemplate(Configurer<JdbcTemplateConfigurer> configurer) {
        this.jdbcTemplateConfigurer = new JdbcTemplateConfigurer();
        configurer.config(jdbcTemplateConfigurer);
        return this;
    }

    public JdbcBasedRoleMappingServiceBuilder jdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplateConfigurer = new JdbcTemplateConfigurer();
        this.jdbcTemplateConfigurer.setJdbcTemplate(jdbcTemplate);
        return this;
    }

    public JdbcBasedRoleMappingServiceBuilder jdbcTemplate(DataSource dataSource) {
        this.jdbcTemplateConfigurer = new JdbcTemplateConfigurer();
        this.jdbcTemplateConfigurer.dataSource(dataSource);
        return this;
    }

    public JdbcBasedRoleMappingServiceBuilder mappedDatabaseConfig(Configurer<JdbcBasedRoleMapperConfigurer> configurer) {
        this.jdbcRoleMapperConfigConfigurer = new JdbcBasedRoleMapperConfigurer();
        configurer.config(jdbcRoleMapperConfigConfigurer);
        return this;
    }

    public JdbcBasedRoleMappingServiceBuilder mappedDatabaseConfig(String tableName, String urlPattenField, String roleField) {
        this.jdbcRoleMapperConfigConfigurer = new JdbcBasedRoleMapperConfigurer();
        this.jdbcRoleMapperConfigConfigurer.tableName(tableName).urlPattenField(urlPattenField).roleField(roleField);
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
