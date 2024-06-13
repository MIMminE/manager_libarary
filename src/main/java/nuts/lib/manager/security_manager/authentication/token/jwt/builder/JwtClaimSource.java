package nuts.lib.manager.security_manager.authentication.token.jwt.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class JwtClaimSource {

    private String issuer;
    private String subject;
    private List<String> audience;
    private String jwtId;
    private Date issuedAt;
    private Date notBefore;
    private Date expirationTime;
    private Map<String, String> customClaims;

    public JwtClaimSource issuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public JwtClaimSource subject(String subject) {
        this.subject = subject;
        return this;
    }

    public JwtClaimSource jwtId(String jwtId) {
        this.jwtId = jwtId;
        return this;
    }

    public JwtClaimSource audience(List<String> audience) {
        this.audience = audience;
        return this;
    }

    public JwtClaimSource issuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public JwtClaimSource notBefore(Date notBefore) {
        this.notBefore = notBefore;
        return this;
    }

    public JwtClaimSource expirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    public JwtClaimSource customClaims(Map<String, String> customClaims) {
        this.customClaims = customClaims;
        return this;
    }
}
