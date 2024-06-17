package nuts.lib.manager.security_manager.provision;

import nuts.lib.manager.security_manager.authentication.token.jwt.JwtAuthenticationFilter;
import nuts.lib.manager.security_manager.authorization.RequestAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class JwtFilterDsl extends AbstractHttpConfigurer<JwtFilterDsl, HttpSecurity> {

    private RequestAuthorizationManager authorizationManager;
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {


    }

    public JwtFilterDsl authorizationManager(RequestAuthorizationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
        return this;
    }

    public JwtFilterDsl jwtAuthenticationFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        return this;
    }
}
