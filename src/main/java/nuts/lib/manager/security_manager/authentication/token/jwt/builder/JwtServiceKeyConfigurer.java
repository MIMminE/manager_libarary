package nuts.lib.manager.security_manager.authentication.token.jwt.builder;

import io.jsonwebtoken.security.MacAlgorithm;
import nuts.lib.manager.verification_manager.annotation_verifier.Essential;

public class JwtServiceKeyConfigurer {


    public static class SymmetricKeyConfigurer {

        @Essential
        private String signatureKey;

        @Essential
        private MacAlgorithm macAlgorithm;


        public SymmetricKeyConfigurer signatureKey(String signatureKey) {
            this.signatureKey = signatureKey;
            return this;
        }

        public SymmetricKeyConfigurer macAlgorithm(JwtKeyAlgorithm.SymmetricKeyAlgorithmType symmetricKeyAlgorithmType) {
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

    public static class AsymmetricKeyConfigurer {
    }
}
