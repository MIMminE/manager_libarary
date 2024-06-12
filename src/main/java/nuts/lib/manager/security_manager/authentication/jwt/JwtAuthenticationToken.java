package nuts.lib.manager.security_manager.authentication.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String jwtToken;

    public JwtAuthenticationToken(String jwtToken) {
        super(null);
        this.jwtToken = jwtToken;
        super.setAuthenticated(false);
    }

    public JwtAuthenticationToken(String jwtToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.jwtToken = jwtToken;
        super.setAuthenticated(true);
    }


    public static JwtAuthenticationToken unauthenticated(String jwtToken) {
        return new JwtAuthenticationToken(jwtToken);
    }

    public static JwtAuthenticationToken authenticated(String jwtToken, Collection<? extends GrantedAuthority> authorities) {
        return new JwtAuthenticationToken(jwtToken, authorities);
    }


    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
