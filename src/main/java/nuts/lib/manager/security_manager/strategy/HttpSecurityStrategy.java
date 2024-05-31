package nuts.lib.manager.security_manager.strategy;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface HttpSecurityStrategy{
    void apply(HttpSecurity httpSecurity) throws Exception;
}
