package nuts.lib.manager.security_manager.authentication.jwt;

import io.jsonwebtoken.JwtBuilder;

public class JwtTokenService {

    private final JwtBuilder jwtBuilder;

    JwtTokenService(JwtBuilder jwtBuilder) {
        this.jwtBuilder = jwtBuilder;
    }

    public String createJwtToken(JwtTokenMetaInformation jwtTokenMetaInformation) {

        return this.jwtBuilder
                .claims(jwtTokenMetaInformation.getClaims())
                .issuer(jwtTokenMetaInformation.getIssuer())
                .issuedAt(jwtTokenMetaInformation.getIssueAt())
                .expiration(jwtTokenMetaInformation.getExpiration())
                .notBefore(jwtTokenMetaInformation.getNotBefore())
                .compact();
    }
}
