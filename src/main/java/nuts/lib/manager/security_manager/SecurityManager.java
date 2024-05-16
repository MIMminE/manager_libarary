package nuts.lib.manager.security_manager;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class SecurityManager {

    private SecurityCustomConfiguration securityCustomConfiguration;

    public HttpSecurity getCustomHttpBuilder(HttpSecurity http, SecurityCustomConfiguration securityCustom){

        return securityCustomConfiguration.apply(http);

    }
}
