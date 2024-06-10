package nuts.lib.manager.security_manager.authentication;

import lombok.RequiredArgsConstructor;
import nuts.lib.manager.security_manager.user.jdbc_user_manager.JdbcUserDetailsManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

@RequiredArgsConstructor
public class AuthenticationProviderCustom implements AuthenticationProvider {
    private final JdbcUserDetailsManager userDetailsManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String credential = authentication.getCredentials().toString();
        DelegatingPasswordEncoder passwordEncoder = userDetailsManager.getPasswordEncoder();

        UserDetails userDetails = userDetailsManager.loadUserByUsername(name);
        String password = userDetails.getPassword();
        if (!passwordEncoder.matches(credential, password)) {
            System.out.println("bad!!");
            throw new BadCredentialsException("Bad Credential!");
        }
        System.out.println("SSS");
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
