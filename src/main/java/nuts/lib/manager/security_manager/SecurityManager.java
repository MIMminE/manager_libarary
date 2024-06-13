package nuts.lib.manager.security_manager;

import nuts.lib.manager.security_manager.authorization.builder.RequestAuthorizationManagerBuilder;
import nuts.lib.manager.security_manager.authorization.RequestAuthorizationManager;
import nuts.lib.manager.security_manager.user.jdbc.JdbcUserDetailsManager;
import nuts.lib.manager.security_manager.user.jdbc.builder.JdbcUserDetailsManagerBuilder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;


/**
 * This class is intended to provide a provisioned form for multiple cases used in the Spring Security filter chain.
 * Since the CSRF function is not used for simplicity, it should not be used in practice because of its weak security.
 * You can get references from various builders related to setting up Security Manager.
 *
 *
 * <pre>
 * {@code
 * @Bean
 *     RequestAuthorizationManager requestAuthorizationManager() {
 *
 *         return SecurityManager.authorizationManagerBuilder
 *                 .mappedDatabaseConfig("authority", "url_patten", "role")
 *                 .defaultPermitAll()
 *                 .build(dataSource, handlerMappingIntrospector);
 *     }
 * }
 * </pre>
 *
 * @author nuts
 * @since 2024. 06. 10
 */
public class SecurityManager {

    public static SecurityManager provision(HttpSecurity httpSecurity) {
        return new SecurityManager(httpSecurity);
    }

    public static RequestAuthorizationManagerBuilder authorizationManagerBuilder = RequestAuthorizationManager.builder;
    public static JdbcUserDetailsManagerBuilder jdbcUserDetailsManagerBuilder = JdbcUserDetailsManager.builder;

    private final HttpSecurity httpSecurity;


    public HttpSecurity authenticationDBAuthorizationInDb(AuthenticationProvider authenticationProvider,
                                                          AuthorizationManager<RequestAuthorizationContext> authorizationManager) throws Exception {

        this.httpSecurity
                .authorizeHttpRequests(request -> request.anyRequest().access(authorizationManager))
                .authenticationManager(new ProviderManager(authenticationProvider));
        return this.httpSecurity;
    }


    public HttpSecurity userDetailsInject(UserDetailsService userDetailsService) throws Exception {
        this.httpSecurity
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService);
        return this.httpSecurity;
    }

    /**
     * Security template used to manage permissions based on user information and HTTP requests.
     * <pre>
     * {@code
     *  @Bean
     *  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     *      SecurityManager.provision(http)
     *              .userAndAuthorityInDb(
     *                      userDetailsService(),
     *                      requestAuthorizationManager());
     *
     *      return http.build();
     *  }
     * }
     * </pre>
     */
    public HttpSecurity userAndAuthorityInDb(UserDetailsService userDetailsService,
                                             AuthorizationManager<RequestAuthorizationContext> authorizationManager) throws Exception {
        this.httpSecurity
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth.anyRequest().access(authorizationManager));
        return this.httpSecurity;
    }

    private SecurityManager(HttpSecurity httpSecurity) {
        this.httpSecurity = httpSecurity;
    }
}
