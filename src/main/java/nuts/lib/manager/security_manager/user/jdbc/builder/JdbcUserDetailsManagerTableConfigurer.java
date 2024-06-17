package nuts.lib.manager.security_manager.user.jdbc.builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class JdbcUserDetailsManagerTableConfigurer {

    private static final String USER_TABLE_NAME = "user_info";
    private static final String USER_NAME_FILED = "user_name";
    private static final String PASSWORD_FILED = "password";
    private static final String ENABLED_FILED = "enabled";
    private static final String AUTHORITY_TABLE_NAME = "user_auth";
    private static final String AUTHORITY_FIELD = "authority";

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserTableConfigurer {

        /**
         * Default settings. The table should be created in advance.
         */
        public static UserTableConfigurer withDefault() {
            return new UserTableConfigurer(USER_TABLE_NAME, USER_NAME_FILED, PASSWORD_FILED, ENABLED_FILED);
        }

        private String tableName;
        private String userNameField;
        private String passWordField;
        private String enabledField;

        public UserTableConfigurer tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public UserTableConfigurer userNameField(String userNameField) {
            this.userNameField = userNameField;
            return this;
        }

        public UserTableConfigurer passWordField(String passWordField) {
            this.passWordField = passWordField;
            return this;
        }

        public UserTableConfigurer enabledField(String enabledField) {
            this.enabledField = enabledField;
            return this;
        }

    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AuthorityTableConfigurer {
        /**
         * Default settings. The table should be created in advance.
         */
        public static AuthorityTableConfigurer withDefault() {
            return new AuthorityTableConfigurer(AUTHORITY_TABLE_NAME, USER_NAME_FILED, AUTHORITY_FIELD);
        }

        String authorityTableName;
        String userNameField;
        String authorityField;

        public AuthorityTableConfigurer authorityTableName(String authorityTableName) {
            this.authorityTableName = authorityTableName;
            return this;
        }

        public AuthorityTableConfigurer userNameField(String userNameField) {
            this.userNameField = userNameField;
            return this;
        }

        public AuthorityTableConfigurer authorityField(String authorityField) {
            this.authorityField = authorityField;
            return this;
        }
    }
}
