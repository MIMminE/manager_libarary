package nuts.lib.manager.security_manager.authorization;

import nuts.lib.manager.security_manager.authorization.role_mapper.RoleMappingService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;

import java.util.List;
import java.util.function.Supplier;

public class RequestAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    public static AuthorizationManagerBuilder builder = new AuthorizationManagerBuilder();

    static private final AuthorizationDecision DENY = new AuthorizationDecision(false);
    static private final AuthorizationDecision ADMIT = new AuthorizationDecision(true);

    List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;
    private final DelegateRequestMatcher delegateRequestMatcher;
    private final RoleMappingService mappingService;
    private AuthorizationDecision defaultDecision = DENY;


    public RequestAuthorizationManager(DelegateRequestMatcher delegateRequestMatcher, RoleMappingService mappingService) {
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
                        AuthorizationManagerProvider.getManager(entry.getValue()))).toList();
    }

}
