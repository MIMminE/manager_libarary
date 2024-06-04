package nuts.lib.manager.security_manager.authorization.role_service.jdbc;

import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

public class JdbcBasedRoleMapperConfigurer {

    @Essential
    String tableName;

    @Essential
    String urlPattenField;

    @Essential
    String roleField;

    public JdbcBasedRoleMapperConfigurer tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public JdbcBasedRoleMapperConfigurer urlPattenField(String urlPattenField) {
        this.urlPattenField = urlPattenField;
        return this;
    }

    public JdbcBasedRoleMapperConfigurer roleField(String roleField) {
        this.roleField = roleField;
        return this;
    }
}
