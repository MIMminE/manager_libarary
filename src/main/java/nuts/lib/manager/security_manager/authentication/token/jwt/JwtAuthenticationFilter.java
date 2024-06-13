package nuts.lib.manager.security_manager.authentication.token.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final static String JWT_HEADER_NAME = "Authentication";

    public JwtAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


        String jwtToken = obtainJwtToken(request);
        if (jwtToken == null) throw new AuthenticationServiceException("The JWT token header is incorrect.");

        JwtAuthenticationToken jwtAuthenticationToken = JwtAuthenticationToken.unauthenticated(jwtToken);

        return this.getAuthenticationManager().authenticate(jwtAuthenticationToken);
    }

    private String obtainJwtToken(HttpServletRequest request) {

        return request.getHeader(JWT_HEADER_NAME);
    }
}
