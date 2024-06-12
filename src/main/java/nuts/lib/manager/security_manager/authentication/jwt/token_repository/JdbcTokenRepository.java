package nuts.lib.manager.security_manager.authentication.jwt.token_repository;

import lombok.RequiredArgsConstructor;
import nuts.lib.manager.security_manager.authentication.TokenRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static nuts.lib.manager.security_manager.authentication.jwt.token_repository.JdbcTokenRepositoryInformation.TokenAuthorityTableInformation;
import static nuts.lib.manager.security_manager.authentication.jwt.token_repository.JdbcTokenRepositoryInformation.TokenTableInformation;

@RequiredArgsConstructor
public class JdbcTokenRepository implements TokenRepository {

    public static JdbcTokenRepositoryBuilder builder = new JdbcTokenRepositoryBuilder();

    private final TokenTableInformation tokenTableInformation;
    private final TokenAuthorityTableInformation tokenAuthorityTableInformation;

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    private String defaultTokenStatus = "active";
    private long defaultExpirationDurationSecond = 1000;

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
}
