package nuts.lib.manager.security_manager.authentication.token.jwt.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nuts.lib.manager.security_manager.authentication.token.jwt.AbstractJwtTokenService;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

/**
 * TODO
 */
public class JwtAuthenticationFilter extends TokenAuthenticationFilter {

    private String JWT_HEADER_NAME = "Authorization";

    public JwtAuthenticationFilter(RequestMatcher requestMatcher, JwtTokenService jwtTokenService) {
        super(requestMatcher, createManager(jwtTokenService));
    }

    public JwtAuthenticationFilter(String matcherPatten, JwtTokenService jwtTokenService){
        super(new AntPathRequestMatcher(matcherPatten), createManager(jwtTokenService));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        if (!this.requestMatcher.matcher(req).isMatch()) {
            chain.doFilter(req, res);
            return;
        }
        String jwtToken = obtainJwtToken(req);
        if (jwtToken == null) throw new AuthenticationServiceException("The JWT token header is incorrect.");

        JwtAuthenticationToken jwtAuthenticationToken = JwtAuthenticationToken.unauthenticated(jwtToken);

        Authentication authentication = this.authenticationManager.authenticate(jwtAuthenticationToken);
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        this.securityContextHolderStrategy.setContext(context);
        chain.doFilter(req, res);
    }

    private String obtainJwtToken(HttpServletRequest request) {

        return request.getHeader(JWT_HEADER_NAME);
    }

    private static AuthenticationManager createManager(JwtTokenService jwtTokenService) {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtTokenService);
        return new ProviderManager(jwtAuthenticationProvider);
    }
}
