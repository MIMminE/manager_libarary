package nuts.lib.manager.security_manager.strategy;

import nuts.lib.manager.security_manager.SecurityStrategy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class csrfStrategy implements SecurityStrategy {

    @Override
    public void apply(HttpSecurity httpSecurity) {
        try {
            httpSecurity.csrf(AbstractHttpConfigurer::disable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
