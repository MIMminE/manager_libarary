package nuts.lib.manager.security_manager.authentication.token.jwt.token_repository;

import lombok.Setter;
import nuts.lib.manager.security_manager.authentication.token.TokenRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static nuts.lib.manager.security_manager.authentication.token.jwt.token_repository.JdbcTokenRepositoryConfigurer.TokenAuthorityTableConfigurer;
import static nuts.lib.manager.security_manager.authentication.token.jwt.token_repository.JdbcTokenRepositoryConfigurer.TokenTableConfigurer;

public class JdbcTokenRepository implements TokenRepository {
    private final TokenTableConfigurer tokenTableInformation;

    private final TokenAuthorityTableConfigurer tokenAuthorityTableInformation;

    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    private String defaultTokenStatus = "active";
    private long defaultExpirationDurationSecond = 1000;

    public JdbcTokenRepository(TokenTableConfigurer tokenTableInformation, TokenAuthorityTableConfigurer tokenAuthorityTableInformation, JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.tokenTableInformation = tokenTableInformation;
        this.tokenAuthorityTableInformation = tokenAuthorityTableInformation;
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }


    @Override
    public void saveToken(String token, List<? extends GrantedAuthority> authorities) {
        transactionTemplate.execute(status -> {

                    LocalDateTime currentDate = LocalDateTime.now();

                    jdbcTemplate.update("INSERT INTO %s (%s, %s, %s, %s) VALUE (?, ?, ?, ?)"
                                    .formatted(tokenTableInformation.getTokenTableName(), tokenTableInformation.getTokenFieldName(),
                                            tokenTableInformation.getTokenStatusFieldName(), tokenTableInformation.getTokenIssueDateFieldName(),
                                            tokenTableInformation.getTokenExpirationFieldName()),
                            token, defaultTokenStatus, currentDate, currentDate.plusSeconds(defaultExpirationDurationSecond));


                    for (GrantedAuthority authority : authorities) {
                        jdbcTemplate.update("INSERT INTO %s (%s, %s) VALUE (?, ?)"
                                        .formatted(tokenAuthorityTableInformation.getTokenAuthorityTableName(), tokenAuthorityTableInformation.getTokenFieldName()
                                                , tokenAuthorityTableInformation.getTokenAuthorityFieldName()),
                                token, authority.toString());
                    }

                    return null;
                }
        );
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities(String token) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT Meta.*, Authority.* FROM %s Meta INNER JOIN %s Authority ON Meta.%s = Authority.%s WHERE Meta.%s = ?"
                .formatted(tokenTableInformation.getTokenTableName(), tokenAuthorityTableInformation.getTokenAuthorityTableName(),
                        tokenTableInformation.getTokenFieldName(), tokenTableInformation.getTokenFieldName(), tokenTableInformation.getTokenFieldName()), token);

        return maps.stream().map(e ->
                new SimpleGrantedAuthority(e.get(tokenAuthorityTableInformation.tokenAuthorityFieldName).toString())).toList();
    }


    public JdbcTokenRepository defaultTokenStatus(String defaultTokenStatus) {
        this.defaultTokenStatus = defaultTokenStatus;
        return this;
    }

    public JdbcTokenRepository defaultExpirationDurationSecond(long defaultExpirationDurationSecond) {
        this.defaultExpirationDurationSecond = defaultExpirationDurationSecond;
        return this;
    }


    private JdbcTokenRepository(TokenTableConfigurer tokenTableInformation, TokenAuthorityTableConfigurer tokenAuthorityTableInformation) {
        this.tokenTableInformation = tokenTableInformation;
        this.tokenAuthorityTableInformation = tokenAuthorityTableInformation;
    }
}
