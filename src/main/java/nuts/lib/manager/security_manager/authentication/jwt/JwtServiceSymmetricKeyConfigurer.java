package nuts.lib.manager.security_manager.authentication.jwt;

import io.jsonwebtoken.security.MacAlgorithm;
import lombok.Getter;
import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

import static nuts.lib.manager.security_manager.authentication.jwt.JwtAlgorithm.SymmetricKeyAlgorithmType;

public class JwtServiceSymmetricKeyConfigurer {

    @Essential
    private String signatureKey;

    @Essential
    private MacAlgorithm macAlgorithm;


    public JwtServiceSymmetricKeyConfigurer signatureKey(String signatureKey) {
        this.signatureKey = signatureKey;
        return this;
    }

    public JwtServiceSymmetricKeyConfigurer macAlgorithm(SymmetricKeyAlgorithmType symmetricKeyAlgorithmType) {
        this.macAlgorithm = symmetricKeyAlgorithmType.macAlgorithm;
        return this;
    }
//
//    public JwtServiceSymmetricKeyConfigurer claims(Map<String, String> claims) {
//        this.claims = claims;
//        return this;
//    }
//
//    public JwtServiceSymmetricKeyConfigurer issuer(String issuer) {
//        this.issuer = issuer;
//        return this;
//    }
//
//    // The default value is the token creation date
//    public JwtServiceSymmetricKeyConfigurer issuerAt(Date issueAt) {
//        this.issueAt = issueAt;
//        return this;
//    }
//
//    // The default value is the token creation date
//    public JwtServiceSymmetricKeyConfigurer notBefore(Date notBefore) {
//        this.notBefore = notBefore;
//        return this;
//    }
//
//    public JwtServiceSymmetricKeyConfigurer expiration(Date expiration) {
//        this.expiration = expiration;
//        return this;
//    }
//
//    public JwtServiceSymmetricKeyConfigurer expirationInNSeconds(long n){
//        this.expiration = new Date(this.issueAt.getTime() + (n * 1000));
//        return this;
//    }
}

