package nuts.lib.manager.security_manager.authorization.role_mapper;

import lombok.Getter;
import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

@Getter
public class JdbcBasedRoleMapperConfigurer{

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
