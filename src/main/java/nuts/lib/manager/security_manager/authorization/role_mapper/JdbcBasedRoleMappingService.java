package nuts.lib.manager.security_manager.authorization.role_mapper;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JdbcBasedRoleMappingService implements RoleMappingService {

    private final JdbcTemplate jdbcTemplate;
    private final String tableName;
    private final String urlPattenField;
    private final String roleField;

    public JdbcBasedRoleMappingService(JdbcTemplate jdbcTemplate, String tableName, String urlPattenField, String roleField) {
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
}
