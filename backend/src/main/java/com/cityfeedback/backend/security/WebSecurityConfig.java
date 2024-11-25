package com.cityfeedback.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Sicherheitspaket für Spring Security
 *
 * @author Ann-Kathrin Meyerhof
 */
@Configuration
@EnableWebSecurity // Ermöglicht Spring, die Klasse zu finden und automatisch auf die globale Websicherheit anzuwenden
public class WebSecurityConfig {

    @Autowired
    BenutzerDetailsService benutzerDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    //  Wird benötigt, um Email und Passwort zu authentifizieren
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(benutzerDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // Koordiniert Authentifizierungsanfragen und leitet an richtigen Provider weiter
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Um Klartext-PW für DaoAuthenticationProvider zu vermeiden
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* Konfiguration von Cross Origin Resource Sharing, Cross-Site-Request-Forgery,
     * Sitzungsverwaltung, Regeln für geschützte Ressourcen und Rollen-Konzept
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());
        /*http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/mitarbeiter", "mitarbeiter/**").hasRole("MITARBEITER") // Nur für Mitarbeiter
                        .requestMatchers("/buerger","buerger/**").hasRole("BUERGER") // Nur für Buerger
                        .requestMatchers("/").permitAll()
                );
*/
        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8081"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // erlaubte HTTP-Methoden
        // Legt die erlaubten Header-Felder fest (Authorization, Content-Type)
        //configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Erlaubt das Senden von Cookies und anderen Credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration); // Registriert die CORS-Konfiguration für alle URLs
        return source;
    }
}


