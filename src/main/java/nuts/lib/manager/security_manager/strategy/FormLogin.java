package nuts.lib.manager.security_manager.strategy;

import nuts.lib.manager.security_manager.HttpSecurityStrategy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public abstract class FormLogin {

    static public HttpSecurityStrategy disable = h -> h.formLogin(AbstractHttpConfigurer::disable);
    static public HttpSecurityStrategy enable = h -> h.formLogin(Customizer.withDefaults());
}
