package nuts.lib.manager.security_manager.authentication.token.jwt.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import nuts.lib.manager.security_manager.authentication.token.jwt.AbstractJwtTokenService;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtGenerator;
import nuts.lib.manager.security_manager.authentication.token.jwt.algoritim.HMACSupport;
import nuts.lib.manager.security_manager.authentication.token.jwt.algoritim.RSASupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SignJwtTokenService extends AbstractJwtTokenService {

    private final String algoType;
    private final String roleClaim = "roles";

    public SignJwtTokenService(JwtGenerator jwtGenerator, JWSVerifier jwsVerifier, String algoType) {
        super(jwtGenerator, jwsVerifier);
        this.algoType = algoType;
    }

    static public SignJwtTokenService useHMACSigner(HMACSupport hmacSupport, String secretKey) throws JOSEException {
        JwtGenerator jwtGenerator = new JwtGenerator(hmacSupport.getJwsAlgorithm(), new MACSigner(secretKey));
        MACVerifier verifier = new MACVerifier(secretKey);

        return new SignJwtTokenService(jwtGenerator, verifier, "HMAC");
    }

    static public SignJwtTokenService useRSASigner(RSASupport rsaSupport, RSAKey rsaKey) throws JOSEException {
        JwtGenerator jwtGenerator = new JwtGenerator(rsaSupport.getJwsAlgorithm(), new RSASSASigner(rsaKey));
        RSASSAVerifier verifier = new RSASSAVerifier(rsaKey);

        return new SignJwtTokenService(jwtGenerator, verifier, "RSA");
    }


    @Override
    public boolean verifyToken(String jwtToken) throws ParseException, JOSEException {

        SignedJWT signedJWT = SignedJWT.parse(jwtToken);

        if (Objects.equals(this.algoType, "HMAC")) {
            MACVerifier macVerifier = (MACVerifier) this.jwsVerifier;
            return signedJWT.verify(macVerifier);
        }

        if (Objects.equals(this.algoType, "RSA")) {
            RSASSAVerifier rsaVerifier = (RSASSAVerifier) this.jwsVerifier;
            return signedJWT.verify(rsaVerifier);
        }

        throw new RuntimeException("An unsupported type has been entered.");
    }

    @Override
    public JWT parse(String token) {
        try {
            return SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<? extends GrantedAuthority> defaultGetAuthority(JWT jwt) throws ParseException {
        JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
        String roles = jwtClaimsSet.getClaim(roleClaim).toString();

        return converterRole(roles);
    }

    private List<? extends GrantedAuthority> converterRole(String roleClaim) {
        String[] split = roleClaim.split(",");
        return Arrays.stream(split).map(e -> new SimpleGrantedAuthority(e.strip())).toList();
    }
}
