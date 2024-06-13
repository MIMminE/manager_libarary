package nuts.lib.manager.security_manager.authentication.token.jwt.token_repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class JdbcTokenRepositoryConfigurer {

    static private final String TOKEN_TABLE_NAME = "jwt_token_meta";
    static private final String TOKEN_FIELD_NAME = "token";
    static private final String TOKEN_STATUS_FIELD_NAME = "token_status";
    static private final String TOKEN_ISSUE_DATE_FIELD_NAME = "issue_date";
    static private final String TOKEN_EXPIRATION_FIELD_NAME = "expiration_date";
    static private final String TOKEN_AUTHORITY_TABLE_NAME = "jwt_token_authority";
    static private final String TOKEN_AUTHORITY_FIELD_NAME = "authority";

    @Getter
    @AllArgsConstructor
    public static class TokenTableConfigurer {

        String tokenTableName;

        String tokenFieldName;

        String tokenStatusFieldName;

        String tokenIssueDateFieldName;

        String tokenExpirationFieldName;


        public static TokenTableConfigurer withDefault() {
            return new TokenTableConfigurer(TOKEN_TABLE_NAME, TOKEN_FIELD_NAME, TOKEN_STATUS_FIELD_NAME, TOKEN_ISSUE_DATE_FIELD_NAME, TOKEN_EXPIRATION_FIELD_NAME);
        }

        public TokenTableConfigurer tokenTableName(String tokenTableName) {
            this.tokenTableName = tokenTableName;
            return this;
        }

        public TokenTableConfigurer tokenFieldName(String tokenFieldName) {
            this.tokenFieldName = tokenFieldName;
            return this;
        }

        public TokenTableConfigurer tokenStatusFieldName(String tokenStatusFieldName) {
            this.tokenStatusFieldName = tokenStatusFieldName;
            return this;
        }

        public TokenTableConfigurer tokenIssueDateFieldName(String tokenIssueDateFieldName) {
            this.tokenIssueDateFieldName = tokenIssueDateFieldName;
            return this;
        }

        public TokenTableConfigurer tokenExpirationFieldName(String tokenExpirationFieldName) {
            this.tokenExpirationFieldName = tokenExpirationFieldName;
            return this;
        }

    }

    @Getter
    @AllArgsConstructor
    public static class TokenAuthorityTableConfigurer {
        String tokenAuthorityTableName;

        String tokenFieldName;

        String tokenAuthorityFieldName;

        public static TokenAuthorityTableConfigurer withDefault() {
            return new TokenAuthorityTableConfigurer(TOKEN_AUTHORITY_TABLE_NAME, TOKEN_FIELD_NAME, TOKEN_AUTHORITY_FIELD_NAME);
        }

        public TokenAuthorityTableConfigurer tokenAuthorityTableName(String tokenAuthorityTableName) {
            this.tokenAuthorityTableName = tokenAuthorityTableName;
            return this;
        }

        public TokenAuthorityTableConfigurer tokenFieldName(String tokenFieldName) {
            this.tokenFieldName = tokenFieldName;
            return this;
        }

        public TokenAuthorityTableConfigurer tokenAuthorityFieldName(String tokenAuthorityFieldName) {
            this.tokenAuthorityFieldName = tokenAuthorityFieldName;
            return this;
        }
    }


}
