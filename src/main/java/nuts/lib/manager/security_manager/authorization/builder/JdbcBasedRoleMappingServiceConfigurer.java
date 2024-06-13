package nuts.lib.manager.security_manager.authorization.builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JdbcBasedRoleMappingServiceConfigurer {

    private static final String TABLE_NAME = "authority";
    private static final String URL_PATTEN_FIELD = "url_patten";
    private static final String ROLE_FIELD = "role";

    /**
     * Default settings. The table should be created in advance.
     */
    public static JdbcBasedRoleMappingServiceConfigurer withDefault() {
        return new JdbcBasedRoleMappingServiceConfigurer(TABLE_NAME, URL_PATTEN_FIELD, ROLE_FIELD);
    }

    String tableName;
    String urlPattenField;
    String roleField;

    public JdbcBasedRoleMappingServiceConfigurer tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public JdbcBasedRoleMappingServiceConfigurer urlPattenField(String urlPattenField) {
        this.urlPattenField = urlPattenField;
        return this;
    }

    public JdbcBasedRoleMappingServiceConfigurer roleField(String roleField) {
        this.roleField = roleField;
        return this;
    }

}
