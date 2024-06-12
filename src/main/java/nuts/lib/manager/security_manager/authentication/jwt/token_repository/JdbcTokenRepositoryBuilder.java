package nuts.lib.manager.security_manager.authentication.jwt.token_repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import static nuts.lib.manager.security_manager.authentication.jwt.token_repository.JdbcTokenRepositoryInformation.*;

public class JdbcTokenRepositoryBuilder {

    private TokenTableInformation tokenTableInformation;
    private TokenAuthorityTableInformation tokenAuthorityTableInformation;

    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    public JdbcTokenRepositoryBuilder tokenTableInformation(TokenTableInformation tokenTableInformation) {
        this.tokenTableInformation = tokenTableInformation;
        return this;
    }

    public JdbcTokenRepositoryBuilder tokenAuthorityTableInformation(TokenAuthorityTableInformation tokenAuthorityTableInformation) {
        this.tokenAuthorityTableInformation = tokenAuthorityTableInformation;
        return this;
    }

    public JdbcTokenRepository build(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        if (tokenTableInformation == null || tokenAuthorityTableInformation == null) {
            throw new IllegalStateException("The prerequisite for performing the build is null and cannot be executed.");
        }

        return new JdbcTokenRepository(this.tokenTableInformation, this.tokenAuthorityTableInformation, jdbcTemplate, transactionTemplate);
    }
}
