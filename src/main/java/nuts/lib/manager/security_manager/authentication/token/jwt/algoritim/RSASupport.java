package nuts.lib.manager.security_manager.authentication.token.jwt.algoritim;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RSASupport {
    RS256(JWSAlgorithm.RS256),
    RS384(JWSAlgorithm.RS384),
    RS512(JWSAlgorithm.RS512);

    private final JWSAlgorithm jwsAlgorithm;
}
