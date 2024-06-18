package nuts.lib.manager.security_manager.authentication.token.jwt.repository;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWT;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class TokenRepositoryPlugIn {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;
    protected String authorityField;

    public TokenRepositoryPlugIn(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate, String authorityField) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
        this.authorityField = authorityField;
    }

    public void insert(JWT token) throws JOSEException {

        String tokenInsertQuery = tokenInsertQueryGenerate(token);
        transactionTemplate.execute((status ->
        {
            jdbcTemplate.update(tokenInsertQuery);
            return null;
        }));
    }

    public List<? extends GrantedAuthority> getAuthority(JWT token) {

        Map<String, Object> queried = jdbcTemplate.queryForMap(tokenAuthoritySelectQueryGenerate(token));
        String roleClaim = queried.get(authorityField).toString();
        return converterRole(roleClaim);
    }

    private List<? extends GrantedAuthority> converterRole(String roleClaim) {
        String[] split = roleClaim.split(",");
        return Arrays.stream(split).map(e -> new SimpleGrantedAuthority(e.strip())).toList();
    }

    protected abstract String tokenInsertQueryGenerate(JWT token);

    protected abstract String tokenAuthoritySelectQueryGenerate(JWT token);
}
