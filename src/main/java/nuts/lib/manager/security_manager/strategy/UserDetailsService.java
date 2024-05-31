package nuts.lib.manager.security_manager.strategy;

public abstract class UserDetailsService {

    public static HttpSecurityStrategy dbInput(String uri) {
        return http -> {
            http.userDetailsService(null);
        };
    }

}
