package nuts.lib.manager.security_manager.authentication.token.jwt.algoritim;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HMACSupport {

    HS256(JWSAlgorithm.HS256),
    HS384(JWSAlgorithm.HS384),
    HS512(JWSAlgorithm.HS512);

    private final JWSAlgorithm jwsAlgorithm;
}
