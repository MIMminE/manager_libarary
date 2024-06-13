package nuts.lib.manager.security_manager.authentication.token.jwt.builder;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import nuts.lib.commom.configurer.Configurer;
import nuts.lib.commom.configurer.RequiredVerificationBuilder;
import nuts.lib.manager.security_manager.authentication.token.TokenRepository;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtTokenService;
import nuts.lib.manager.security_manager.authentication.token.jwt.token_repository.JdbcTokenRepositoryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.function.Supplier;

import static nuts.lib.manager.security_manager.authentication.token.jwt.builder.JwtKeyAlgorithm.SymmetricKeyAlgorithmType;
import static nuts.lib.manager.security_manager.authentication.token.jwt.builder.JwtServiceKeyConfigurer.AsymmetricKeyConfigurer;

public class JwtTokenServiceBuilder extends RequiredVerificationBuilder {

    private TokenRepository tokenRepository;
    private JwtBuilder jwtBuilder;
    private JwtParser jwtParser;

    public JwtTokenServiceBuilder jdbcTokenRepository(Configurer<JdbcTokenRepositoryBuilder> configure, JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        JdbcTokenRepositoryBuilder jdbcTokenRepositoryBuilder = new JdbcTokenRepositoryBuilder();
        configure.config(jdbcTokenRepositoryBuilder);
        this.tokenRepository = jdbcTokenRepositoryBuilder.build(jdbcTemplate, transactionTemplate);
        return this;
    }

    public JwtTokenServiceBuilder symmetricKeyConfig(String secretKey, SymmetricKeyAlgorithmType keyAlgo) {
        this.jwtBuilder = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), keyAlgo.macAlgorithm);

        this.jwtParser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();
        return this;
    }

    //TODO
    public JwtTokenServiceBuilder asymmetricKeyConfig(AsymmetricKeyConfigurer keyAlgo) {
        return this;
    }

    public JwtTokenService build() {

        super.verify();
        return new JwtTokenService(jwtBuilder, jwtParser, tokenRepository);
    }

    @Override
    protected Supplier<HashMap<String, Object>> setVerification() {
        return () -> new HashMap<>() {{
            put("tokenRepository", tokenRepository);
            put("jwtBuilder", jwtBuilder);
            put("jwtParser", jwtParser);
        }};
    }
}
