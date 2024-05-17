package nuts.lib.manager.security_manager;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface HttpSecurityStrategy{
    void apply(HttpSecurity httpSecurity) throws Exception;
}
