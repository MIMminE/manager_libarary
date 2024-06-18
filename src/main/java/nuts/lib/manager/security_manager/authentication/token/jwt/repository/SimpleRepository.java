package nuts.lib.manager.security_manager.authentication.token.jwt.repository;

import com.nimbusds.jwt.JWT;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

public class SimpleRepository extends TokenRepositoryPlugIn {

    public SimpleRepository(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate, String authorityField) {
        super(jdbcTemplate, transactionTemplate, authorityField);
    }

    @Override
    protected String tokenInsertQueryGenerate(JWT token) {
        return String.format("INSERT INTO token (token, user, authority) VALUES ('%s', '%s', '%s')", token.serialize(), "tester", "admin, user");
    }

    @Override
    protected String tokenAuthoritySelectQueryGenerate(JWT token) {
        return "SELECT * FROM token";
    }

}
