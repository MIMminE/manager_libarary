package nuts.lib.manager.security_manager.strategy;

import nuts.lib.manager.security_manager.HttpSecurityStrategy;
import org.springframework.security.config.http.SessionCreationPolicy;

public abstract class AuthorizeHttpRequests {

    static public HttpSecurityStrategy permitAll = http -> http.authorizeHttpRequests(auth->auth.anyRequest().permitAll());

}
