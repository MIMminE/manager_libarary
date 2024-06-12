package nuts.lib.manager.security_manager.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import nuts.lib.manager.security_manager.authentication.TokenRepository;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JwtTokenService {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final TokenRepository tokenRepository;

    public JwtTokenService(JwtBuilder jwtBuilder, JwtParser jwtParser, TokenRepository tokenRepository) {
        this.jwtBuilder = jwtBuilder;
        this.jwtParser = jwtParser;
        this.tokenRepository = tokenRepository;
    }

    public String createJwtToken(JwtTokenMetaInformation jwtTokenMetaInformation, List<? extends GrantedAuthority> authorities) {
        String token = this.jwtBuilder
                .claims(jwtTokenMetaInformation.getClaims())
                .issuer(jwtTokenMetaInformation.getIssuer())
                .issuedAt(jwtTokenMetaInformation.getIssueAt())
                .expiration(jwtTokenMetaInformation.getExpiration())
                .notBefore(jwtTokenMetaInformation.getNotBefore())
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
