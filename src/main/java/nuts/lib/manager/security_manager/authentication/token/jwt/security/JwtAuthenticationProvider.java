package nuts.lib.manager.security_manager.authentication.token.jwt.security;

import com.nimbusds.jwt.JWT;
import lombok.RequiredArgsConstructor;
import nuts.lib.manager.security_manager.authentication.token.jwt.AbstractJwtTokenService;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtTokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenService jwtTokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(JwtAuthenticationToken.class, authentication);

        String token = authentication.getCredentials().toString().split(" ")[1];
        JWT jwtToken = jwtTokenService.parse(token);

        List<? extends GrantedAuthority> grantedAuthority = null;

        try {
            grantedAuthority = jwtTokenService.getAuthority(jwtToken);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (grantedAuthority.isEmpty()) throw new AuthenticationServiceException("This is not a valid token.");

        return JwtAuthenticationToken.authenticated(token, grantedAuthority);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
