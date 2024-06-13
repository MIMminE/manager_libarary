package nuts.lib.manager.security_manager.authorization;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * It is a class that stores matcher classes used for security and routing settings and delegates related functions to handle them.
 * <pre>
 * {@code
 *      .antMatcher("/admin/**").hasRole("ADMIN")
 * }
 *
 * @creation 2024. 06. 13
 */
public class RequestMatcherDelegator {

    private final HandlerMappingIntrospector handlerMappingIntrospector;

    private final Map<String, Class<? extends RequestMatcher>> requestMatchers = Map.of(
            "mvc_matcher", MvcRequestMatcher.class,
            "ant_matcher", AntPathRequestMatcher.class,
            "ip_address_matcher", IpAddressMatcher.class
    );

    public RequestMatcherDelegator(HandlerMappingIntrospector handlerMappingIntrospector) {
        this.handlerMappingIntrospector = handlerMappingIntrospector;
    }

    public MvcRequestMatcher mvcRequestMatcher(String pattern) {
        try {
            return (MvcRequestMatcher) requestMatchers.get("mvc_matcher").getConstructor(HandlerMappingIntrospector.class, String.class).newInstance(handlerMappingIntrospector, pattern);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public AntPathRequestMatcher antMatcher(String pattern) {
        try {
            return (AntPathRequestMatcher) requestMatchers.get("ant_matcher").getMethod("antMatcher", String.class).invoke(null, pattern);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public AntPathRequestMatcher antMatcher(String pattern, HttpMethod method) {
        try {
            return (AntPathRequestMatcher) requestMatchers.get("ant_matcher").getMethod("antMatcher", HttpMethod.class, String.class).invoke(null, method, pattern);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public IpAddressMatcher ipAddressMatcher(String ipAddress) {
        try {
            return (IpAddressMatcher) requestMatchers.get("ip_address_matcher").getConstructor(String.class).newInstance(ipAddress);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


}
