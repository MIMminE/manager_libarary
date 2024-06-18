package nuts.lib.manager.security_manager.authentication.token.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import nuts.lib.manager.security_manager.authentication.token.jwt.algoritim.HMACSupport;
import nuts.lib.manager.security_manager.authentication.token.jwt.algoritim.RSASupport;
import nuts.lib.manager.security_manager.authentication.token.jwt.repository.TokenRepositoryPlugIn;
import nuts.lib.manager.security_manager.authentication.token.jwt.service.SignJwtTokenService;

import java.util.Objects;

public class JwtTokenServiceBuilder {

    private JwtGenerator jwtGenerator;
    private JWSVerifier jwsVerifier;
    private TokenRepositoryPlugIn tokenRepository;
    private String algoType;

    public JwtTokenServiceBuilder HMACSigner(HMACSupport hmacSupport, String secretKey) throws JOSEException {
        this.jwtGenerator = new JwtGenerator(hmacSupport.getJwsAlgorithm(), new MACSigner(secretKey));
        this.jwsVerifier = new MACVerifier(secretKey);
        this.algoType = "HMAC";
        return this;
    }

    public JwtTokenServiceBuilder RSASigner(RSASupport rsaSupport, RSAKey rsaKey) throws JOSEException {
        this.jwtGenerator = new JwtGenerator(rsaSupport.getJwsAlgorithm(), new RSASSASigner(rsaKey));
        this.jwsVerifier = new RSASSAVerifier(rsaKey);
        this.algoType = "RSA";
        return this;
    }

    public JwtTokenServiceBuilder plugInTokenRepository(TokenRepositoryPlugIn tokenRepository) {
        this.tokenRepository = tokenRepository;
        return this;
    }

    public JwtTokenService build() {
        SignJwtTokenService signJwtTokenService = new SignJwtTokenService(this.jwtGenerator, this.jwsVerifier, this.algoType);
        if (tokenRepository != null)
            signJwtTokenService.plugIn(tokenRepository);

        return signJwtTokenService;
    }
}
