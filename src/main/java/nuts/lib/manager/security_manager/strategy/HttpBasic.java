package nuts.lib.manager.security_manager.strategy;

import nuts.lib.manager.security_manager.HttpSecurityStrategy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public abstract class HttpBasic {

    static public HttpSecurityStrategy disable = http -> http.httpBasic(AbstractHttpConfigurer::disable);
    static public HttpSecurityStrategy enable = http -> http.httpBasic(Customizer.withDefaults());
}
