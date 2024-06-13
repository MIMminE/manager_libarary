package nuts.lib.manager.security_manager.authentication.token.jwt.builder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

public abstract class JwtKeyAlgorithm {

    public enum SymmetricKeyAlgorithmType {
        HS256("Hash-based Message Authentication Code, requires 256bit key", Jwts.SIG.HS256),
        HS384("Hash-based Message Authentication Code, requires 384bit key", Jwts.SIG.HS384),
        HS512("Hash-based Message Authentication Code, requires 384bit key", Jwts.SIG.HS512);

        final String description;
        final MacAlgorithm macAlgorithm;

        SymmetricKeyAlgorithmType(String description, MacAlgorithm macAlgorithm) {
            this.description = description;
            this.macAlgorithm = macAlgorithm;
        }
    }

//    public enum AsymmetricKeyAlgorithm {
//        RS256(""),
//        RS384,
//        RS512,
//        PS256,
//        PS384,
//        PS512,
//        ES256,
//        ES384,
//        ES512,
//        EdDSA;
//
//        final String description;
//        final SignatureAlgorithm signatureAlgorithm;
//
//        SymmetricKeyAlgorithm(String description, SignatureAlgorithm signatureAlgorithm) {
//            this.description = description;
//            this.signatureAlgorithm = signatureAlgorithm;
//        }
//    }

}
