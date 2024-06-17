package nuts.lib.manager.security_manager.authentication.token;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class TokenAuthenticationFilter extends OncePerRequestFilter {
    protected SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    protected RequestMatcher requestMatcher;
    protected AuthenticationManager authenticationManager;

    public TokenAuthenticationFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager) {
        this.requestMatcher = requestMatcher;
        this.authenticationManager = authenticationManager;
    }
}
