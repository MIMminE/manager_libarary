package nuts.lib.manager.security_manager.authentication.token.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.GrantedAuthority;

import java.text.ParseException;
import java.util.List;

public interface JwtTokenService {

    JWT createToken(JWTClaimsSet jwtClaimsSet) throws JOSEException;

    boolean verifyToken(String jwtToken) throws ParseException, JOSEException;

    List<? extends GrantedAuthority> getAuthority(JWT jwt) throws ParseException;

    JWT parse(String token);

}
