package nuts.lib.manager.security_manager.authentication.token.jwt.token_repository;

import lombok.Getter;
import nuts.lib.commom.configurer.RequiredVerificationBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.function.Supplier;

import static nuts.lib.manager.security_manager.authentication.token.jwt.token_repository.JdbcTokenRepositoryConfigurer.*;

@Getter
public class JdbcTokenRepositoryBuilder extends RequiredVerificationBuilder {
    private TokenTableConfigurer tokenTableConfigurer;
    private TokenAuthorityTableConfigurer tokenAuthorityTableConfigurer;

    public JdbcTokenRepositoryBuilder tokenTableConfigurer(TokenTableConfigurer tokenTableConfigurer) {
        this.tokenTableConfigurer = tokenTableConfigurer;
        return this;
    }

    public JdbcTokenRepositoryBuilder tokenAuthorityTableConfigurer(TokenAuthorityTableConfigurer tokenAuthorityTableConfigurer) {
        this.tokenAuthorityTableConfigurer = tokenAuthorityTableConfigurer;
        return this;
    }

    public void verify() {
        super.verify();
    }

    /**
     *
     */
    @Deprecated(since = "2024. 06. 17", forRemoval = true)
    public JdbcTokenRepository build(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        super.verify();
        return new JdbcTokenRepository(tokenTableConfigurer, tokenAuthorityTableConfigurer, jdbcTemplate, transactionTemplate);
    }

    @Override
    protected Supplier<HashMap<String, Object>> setVerification() {
        return () -> new HashMap<>() {{
            put("tokenTableConfigurer", tokenTableConfigurer);
            put("tokenAuthorityTableConfigurer", tokenAuthorityTableConfigurer);
        }};
    }
}
