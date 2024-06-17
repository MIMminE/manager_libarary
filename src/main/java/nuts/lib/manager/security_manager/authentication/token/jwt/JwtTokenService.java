package nuts.lib.manager.security_manager.authentication.token.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import nuts.lib.manager.security_manager.authentication.token.TokenRepository;
import nuts.lib.manager.security_manager.authentication.token.jwt.builder.JwtClaimSource;
import nuts.lib.manager.security_manager.authentication.token.jwt.builder.JwtTokenServiceBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * TODO
 */
public class JwtTokenService {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final TokenRepository tokenRepository;

    public static JwtTokenServiceBuilder builder = new JwtTokenServiceBuilder();

    public JwtTokenService(JwtBuilder jwtBuilder, JwtParser jwtParser, TokenRepository tokenRepository) {
        this.jwtBuilder = jwtBuilder;
        this.jwtParser = jwtParser;
        this.tokenRepository = tokenRepository;
    }

    public String createJwtToken(JwtClaimSource jwtClaimSource, List<? extends GrantedAuthority> authorities) {

        String token = this.jwtBuilder
                .claims(jwtClaimSource.getCustomClaims())
                .id(jwtClaimSource.getJwtId())
                .subject(jwtClaimSource.getSubject())
                .issuer(jwtClaimSource.getIssuer())
                .issuedAt(jwtClaimSource.getIssuedAt())
                .audience().add(jwtClaimSource.getAudience()).and()
                .expiration(jwtClaimSource.getExpirationTime())
                .notBefore(jwtClaimSource.getNotBefore())
                .compact();

        tokenRepository.saveToken(token, authorities);

        return token;
    }

    public boolean validationJwtToken(String jwtToken) {
        return jwtParser.isSigned(jwtToken);
    }

    public Claims getPayload(String jwtToken) {
        Jws<Claims> claimsJws = jwtParser.parseSignedClaims(jwtToken);
        return claimsJws.getPayload();
    }

    public List<? extends GrantedAuthority> getGrantedAuthority(String jwtToken) {
        return tokenRepository.getAuthorities(jwtToken);
    }
}
