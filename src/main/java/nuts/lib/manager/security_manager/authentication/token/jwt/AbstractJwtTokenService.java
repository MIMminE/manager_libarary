package nuts.lib.manager.security_manager.authentication.token.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import nuts.lib.manager.security_manager.authentication.token.jwt.repository.TokenRepositoryPlugIn;
import org.springframework.security.core.GrantedAuthority;

import java.text.ParseException;
import java.util.List;

public abstract class AbstractJwtTokenService implements JwtTokenService {
    protected JwtGenerator jwtGenerator;
    protected JWSVerifier jwsVerifier;
    protected TokenRepositoryPlugIn repositoryPlugIn;

    public AbstractJwtTokenService(JwtGenerator jwtGenerator, JWSVerifier jwsVerifier) {
        this.jwtGenerator = jwtGenerator;
        this.jwsVerifier = jwsVerifier;
    }

    @Override
    public JWT createToken(JWTClaimsSet jwtClaimsSet) throws JOSEException {
        JWT token = jwtGenerator.createToken(jwtClaimsSet);
        if (repositoryPlugIn != null) repositoryPlugIn.insert(token);
        return token;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthority(JWT jwt) throws ParseException {
        if (repositoryPlugIn != null) return repositoryPlugIn.getAuthority(jwt);

        return defaultGetAuthority(jwt);
    }

    public void plugIn(TokenRepositoryPlugIn tokenRepositoryPlugIn) {
        this.repositoryPlugIn = tokenRepositoryPlugIn;
    }


    public abstract List<? extends GrantedAuthority> defaultGetAuthority(JWT jwt) throws ParseException;
}
