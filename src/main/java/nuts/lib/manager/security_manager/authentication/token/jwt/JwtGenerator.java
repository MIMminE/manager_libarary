package nuts.lib.manager.security_manager.authentication.token.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import static nuts.lib.manager.security_manager.authentication.token.jwt.JwtGenerator.AlgorithmType.*;

public class JwtGenerator {
    private final Algorithm algorithm;
    private final AlgorithmType type;
    private final JWSSigner signer;

    public JwtGenerator(JWSAlgorithm jwsAlgorithm, JWSSigner signer) {
        this.algorithm = jwsAlgorithm;
        this.signer = signer;
        this.type = JWS;
    }

    public JwtGenerator(EncryptionMethod encryptionMethod, JWSSigner signer) {
        this.algorithm = encryptionMethod;
        this.signer = signer;
        this.type = JWE;
    }

    public JWT createToken(JWTClaimsSet claimsSet) throws JOSEException {
        if (this.type == JWS) return signedJWT(claimsSet);
        if (this.type == JWE) return encryptedJWT(claimsSet);
        throw new IllegalStateException("Invalid JWT algorithm type.");
    }

    private SignedJWT signedJWT(JWTClaimsSet claimsSet) throws JOSEException {
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder((JWSAlgorithm) algorithm).build(), claimsSet);
        signedJWT.sign(signer);
        return signedJWT;
    }

    private EncryptedJWT encryptedJWT(JWTClaimsSet claimsSet) {

        return new EncryptedJWT(new JWEHeader.Builder((EncryptionMethod) algorithm).build(), claimsSet);
    }

    enum AlgorithmType {
        JWS,
        JWE
    }
}
