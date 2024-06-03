package nuts.lib.manager.security_manager;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import java.util.function.Supplier;

public abstract class SecurityCustomBean {
    public final static Supplier<WebSecurityCustomizer> ignoreSecurityFilterH2Database =
            () -> web -> web.ignoring().requestMatchers(PathRequest.toH2Console());


    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth-> auth
                        .anyRequest().authenticated())
                .httpBasic(basic-> basic.authenticationEntryPoint(new BasicAuthenticationEntryPoint()));

    }
}
