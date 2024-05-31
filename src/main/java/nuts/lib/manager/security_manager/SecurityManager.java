package nuts.lib.manager.security_manager;

import nuts.lib.manager.security_manager.custom.HttpSecurityCustomConfiguration;
import nuts.lib.manager.security_manager.strategy.HttpSecurityStrategy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.ArrayList;
import java.util.List;

public class SecurityManager {

    private final HttpSecurity httpSecurity;
    private HttpSecurityCustomConfiguration customConfiguration;

    public SecurityManager(HttpSecurity httpSecurity) {
        this.httpSecurity = httpSecurity;
        this.customConfiguration = null;
    }

    public SecurityManager(HttpSecurity httpSecurity, HttpSecurityCustomConfiguration customConfiguration) {
        this.httpSecurity = httpSecurity;
        this.customConfiguration = customConfiguration;
    }

    public void customize(Customizer<HttpSecurityCustomConfiguration> customizer) throws Exception {

        customizer.customize(getOrApply(new HttpSecurityCustomConfiguration() {
            @Override
            protected List<HttpSecurityStrategy> strategiesInit() {
                return new ArrayList<>();
            }
        }));

        customConfiguration.apply(httpSecurity);
    }

    private HttpSecurityCustomConfiguration getOrApply(HttpSecurityCustomConfiguration configuration) {
        if (customConfiguration == null)
            this.customConfiguration = configuration;

        return this.customConfiguration;
    }
}
