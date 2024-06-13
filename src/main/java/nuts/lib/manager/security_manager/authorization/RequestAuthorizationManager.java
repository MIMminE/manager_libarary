package nuts.lib.manager.security_manager.authorization;

import nuts.lib.manager.security_manager.authorization.builder.RequestAuthorizationManagerBuilder;
import nuts.lib.manager.security_manager.authorization.mapper.RoleMappingService;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;

import java.util.List;
import java.util.function.Supplier;

/**
 * Spring Security Accreditation Manager.
 * <p>
 * Map the required permissions for the URL request.
 * <p>
 * A Mapping service is provided in various ways through the {@link RoleMappingService} interface implementation.
 *
 * @creation 2024. 06. 13
 */

public class RequestAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    public static RequestAuthorizationManagerBuilder builder = new RequestAuthorizationManagerBuilder();

    static private final AuthorizationDecision DENY = new AuthorizationDecision(false);
    static private final AuthorizationDecision ADMIT = new AuthorizationDecision(true);

    List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;
    private final RequestMatcherDelegator delegateRequestMatcher;
    private final RoleMappingService mappingService;
    private AuthorizationDecision defaultDecision = DENY;


    public RequestAuthorizationManager(RequestMatcherDelegator delegateRequestMatcher, RoleMappingService mappingService) {
        this.delegateRequestMatcher = delegateRequestMatcher;
        this.mappingService = mappingService;
        this.mapping();
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : mappings) {
            RequestMatcher.MatchResult matchResult = mapping.getRequestMatcher().matcher(requestAuthorizationContext.getRequest());
            if (matchResult.isMatch())
                return mapping.getEntry().check(authentication, new RequestAuthorizationContext(requestAuthorizationContext.getRequest(), matchResult.getVariables()));
        }

        return defaultDecision;
    }

    public void defaultPermission() {
        this.defaultDecision = ADMIT;
    }

    private void mapping() {
        mappings = mappingService.getRoleMappings().entrySet().stream()
                .map(entry -> new RequestMatcherEntry<>(delegateRequestMatcher.mvcRequestMatcher(entry.getKey()),
                        getManager(entry.getValue()))).toList();
    }

    private AuthorizationManager<RequestAuthorizationContext> getManager(String role) {
        if (role != null) {
            if (role.startsWith("ROLE")) return AuthorityAuthorizationManager.hasAuthority(role);
            else return new WebExpressionAuthorizationManager(role);
        }
        throw new IllegalArgumentException("The wording of the permission setting is incorrect.");
    }
}