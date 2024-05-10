package nuts.lib.manager.security_manager;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import java.util.function.Supplier;

public abstract class SecurityCustomBean {
    public final static Supplier<WebSecurityCustomizer> ignoreSecurityFilterH2Database =
            () -> web -> web.ignoring().requestMatchers(PathRequest.toH2Console());
}
