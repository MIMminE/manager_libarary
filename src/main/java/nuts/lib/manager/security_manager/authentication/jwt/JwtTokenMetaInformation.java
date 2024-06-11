package nuts.lib.manager.security_manager.authentication.jwt;

import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
public class JwtTokenMetaInformation {
    private final Map<String, String> claims;
    private final String issuer;
    private final Date issueAt;
    private final Date notBefore;
    private final Date expiration;

    public JwtTokenMetaInformation(Map<String, String> claims, String issuer, Date issueAt, Date notBefore, Date expiration) {
        this.claims = claims;
        this.issuer = issuer;
        this.issueAt = issueAt;
        this.notBefore = notBefore;
        this.expiration = expiration;
    }
}
