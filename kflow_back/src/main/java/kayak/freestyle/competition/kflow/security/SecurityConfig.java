package kayak.freestyle.competition.kflow.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuration class for Spring Security. This class configures
 * authentication, authorization, CORS, and JWT handling for the K-FLOW
 * application.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private static final String COMPETITIONS_ENDPOINT = "/competitions/**";
    private static final String CATEGORIES_ENDPOINT = "/categories/**";
    private static final String PARTICIPANTS_ENDPOINT = "/participants/**";
    private static final String RUNS_ENDPOINT = "/runs/**";
    private static final String STAGES_ENDPOINT = "/stages/**";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    /**
     * RSA public key used for JWT verification. Injected from application
     * properties.
     */
    @Value("${rsa.public-key}")
    RSAPublicKey publicKey;

    /**
     * RSA private key used for JWT signing. Injected from application
     * properties.
     */
    @Value("${rsa.private-key}")
    RSAPrivateKey privateKey;

    /**
     * Configures the security filter chain for the application. Sets up
     * authorization rules, CORS, CSRF, session management, and JWT handling.
     *
     * @param http The HttpSecurity object to configure
     * @return The configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(customizer -> customizer
                // Routes publiques
                .requestMatchers("/authenticate").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                // Routes protégées pour les utilisateurs authentifiés
                .requestMatchers(HttpMethod.GET, COMPETITIONS_ENDPOINT).hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, CATEGORIES_ENDPOINT).hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, PARTICIPANTS_ENDPOINT).hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, RUNS_ENDPOINT).hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, STAGES_ENDPOINT).hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                // Routes protégées pour les administrateurs uniquement
                .requestMatchers(HttpMethod.POST, COMPETITIONS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.PUT, COMPETITIONS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.DELETE, COMPETITIONS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, CATEGORIES_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.PUT, CATEGORIES_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.DELETE, CATEGORIES_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, PARTICIPANTS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.PUT, PARTICIPANTS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.DELETE, PARTICIPANTS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, RUNS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.PUT, RUNS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.DELETE, RUNS_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, STAGES_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.PUT, STAGES_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.DELETE, STAGES_ENDPOINT).hasAuthority(ADMIN_ROLE)
                .requestMatchers("/api/import/**").hasAuthority(ADMIN_ROLE)
                // Toutes les autres routes nécessitent une authentification
                .anyRequest().authenticated())
                .formLogin(c -> c.disable())
                .logout(c -> c.disable())
                .csrf(c -> c.disable())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(c -> c.jwt(jwt -> {
            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
            jwt.decoder(jwtDecoder());
        }));
        return http.build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings. Allows requests
     * from the frontend application running on localhost:4200.
     *
     * @return The configured CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost");
        configuration.addAllowedOrigin("http://localhost:80");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configures the JWT authentication converter. Sets up how JWT claims are
     * converted to Spring Security authorities.
     *
     * @return The configured JwtAuthenticationConverter
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("role");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    /**
     * Creates a password encoder bean. Uses Spring Security's delegating
     * password encoder.
     *
     * @return The configured PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Creates a JWT decoder bean. Uses the RSA public key for token
     * verification.
     *
     * @return The configured JwtDecoder
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    /**
     * Creates a JWT encoder bean. Uses the RSA key pair for token signing.
     *
     * @return The configured JwtEncoder
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
