package nuts.lib.manager.security_manager.authorization;

import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

public class AuthorizationManagerProvider {

    static public AuthorizationManager<RequestAuthorizationContext> getManager(String role) {
        if (role != null) {
            if (role.startsWith("ROLE")) return AuthorityAuthorizationManager.hasAuthority(role);
            else return new WebExpressionAuthorizationManager(role);
        }
        throw new IllegalArgumentException("The wording of the permission setting is incorrect.");
    }
}
