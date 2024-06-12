package nuts.lib.manager.security_manager.authentication.jwt.token_repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class JdbcTokenRepositoryInformation {

    static private final String TOKEN_TABLE_NAME = "jwt_token_meta";
    static private final String TOKEN_FIELD_NAME = "token";
    static private final String TOKEN_STATUS_FIELD_NAME = "token_status";
    static private final String TOKEN_ISSUE_DATE_FIELD_NAME = "issue_date";
    static private final String TOKEN_EXPIRATION_FIELD_NAME = "expiration_date";
    static private final String TOKEN_AUTHORITY_TABLE_NAME = "jwt_token_authority";
    static private final String TOKEN_AUTHORITY_FIELD_NAME = "authority";

    @Getter
    @AllArgsConstructor
    public static class TokenTableInformation {

        String tokenTableName;

        String tokenFieldName;

        String tokenStatusFieldName;

        String tokenIssueDateFieldName;

        String tokenExpirationFieldName;


        public static TokenTableInformation withDefault() {
            return new TokenTableInformation(TOKEN_TABLE_NAME, TOKEN_FIELD_NAME, TOKEN_STATUS_FIELD_NAME, TOKEN_ISSUE_DATE_FIELD_NAME, TOKEN_EXPIRATION_FIELD_NAME);
        }

        public TokenTableInformation tokenTableName(String tokenTableName) {
            this.tokenTableName = tokenTableName;
            return this;
        }

        public TokenTableInformation tokenFieldName(String tokenFieldName) {
            this.tokenFieldName = tokenFieldName;
            return this;
        }

        public TokenTableInformation tokenStatusFieldName(String tokenStatusFieldName) {
            this.tokenStatusFieldName = tokenStatusFieldName;
            return this;
        }

        public TokenTableInformation tokenIssueDateFieldName(String tokenIssueDateFieldName) {
            this.tokenIssueDateFieldName = tokenIssueDateFieldName;
            return this;
        }

        public TokenTableInformation tokenExpirationFieldName(String tokenExpirationFieldName) {
            this.tokenExpirationFieldName = tokenExpirationFieldName;
            return this;
        }

    }

    @Getter
    @AllArgsConstructor
    public static class TokenAuthorityTableInformation {
        String tokenAuthorityTableName;

        String tokenFieldName;

        String tokenAuthorityFieldName;

        public static TokenAuthorityTableInformation withDefault() {
            return new TokenAuthorityTableInformation(TOKEN_AUTHORITY_TABLE_NAME, TOKEN_FIELD_NAME, TOKEN_AUTHORITY_FIELD_NAME);
        }

        public TokenAuthorityTableInformation tokenAuthorityTableName(String tokenAuthorityTableName) {
            this.tokenAuthorityTableName = tokenAuthorityTableName;
            return this;
        }

        public TokenAuthorityTableInformation tokenFieldName(String tokenFieldName) {
            this.tokenFieldName = tokenFieldName;
            return this;
        }

        public TokenAuthorityTableInformation tokenAuthorityFieldName(String tokenAuthorityFieldName) {
            this.tokenAuthorityFieldName = tokenAuthorityFieldName;
            return this;
        }
    }


}
