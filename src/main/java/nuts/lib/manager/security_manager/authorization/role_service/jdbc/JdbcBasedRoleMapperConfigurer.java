package nuts.lib.manager.security_manager.authorization.role_service.jdbc;

public class JdbcBasedRoleMapperConfigurer {

    String tableName;
    String urlPattenField;
    String roleField;

    public JdbcBasedRoleMapperConfigurer tableName(String tableName){
        this.tableName = tableName;
        return this;
    }

    public JdbcBasedRoleMapperConfigurer urlPattenField(String urlPattenField){
        this.urlPattenField = urlPattenField;
        return this;
    }

    public JdbcBasedRoleMapperConfigurer roleField(String roleField){
        this.roleField = roleField;
        return this;
    }
}
