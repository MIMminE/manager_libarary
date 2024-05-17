package nuts.lib.manager.security_manager.strategy;

import nuts.lib.manager.security_manager.HttpSecurityStrategy;

public abstract class UserDetailsService {

    public static HttpSecurityStrategy dbInput(String uri) {
        return httpSecurity -> {
            httpSecurity.userDetailsService(null);
        };
    }

}
