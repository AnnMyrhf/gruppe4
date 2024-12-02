package com.cityfeedback.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Sicherheitspaket für Spring Security
 *
 * @author Ann-Kathrin Meyerhof
 */
@Configuration
@EnableWebSecurity // Ermöglicht Spring, die Klasse zu finden und automatisch auf die globale Websicherheit anzuwenden
public class WebSecurityConfig {

    @Autowired
    BuergerDetailsService buergerDetailsService;

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

        authProvider.setUserDetailsService(buergerDetailsService);
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
    /* Konfiguration von Cross Origin Resource Sharing, Cross-Site-Request-Forgery,
     * Sitzungsverwaltung, Regeln fuer geschuetzte Ressourcen und Rollen-Konzept
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Deaktiviert CSRF-Schutz
        http.csrf(csrf -> csrf.disable());

        // Legt einen Handler fest, der bei fehlgeschlagener Authentifizierung aufgerufen wird
        http.exceptionHandling((exception) -> exception.authenticationEntryPoint(unauthorizedHandler));

        // Definiert Autorisierungsregeln
        http.authorizeHttpRequests(requests -> requests
                // Öffentliche Endpunkte (keine Authentifizierung erforderlich)
                .requestMatchers("/buerger-anmelden/**", "/buerger-registrieren", "/h2/**").permitAll()

                // Endpunkte, die die Rolle "BUERGER" erfordern
                .requestMatchers("/beschwerden/**", "/buerger-loeschen/**").hasRole("BUERGER")

                // Endpunkte, die die Rolle "MITARBEITER" erfordern
                .requestMatchers("/mitarbeiter-registrieren/**", "/mitarbeiter-loeschen/**", "/mitarbeiter-anmelden/**").hasRole("MITARBEITER")

                // Alle anderen Anfragen erfordern eine Authentifizierung (beliebige Rolle)
                .anyRequest().authenticated());

        // Ermöglicht iframes für die h2-Konsole
        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        // Benutzerdefinierten Authentifizierungs-Provider
        http.authenticationProvider(authenticationProvider());

        // Fügt einen benutzerdefinierten JWT-Authentifizierungsfilter vor dem UsernamePasswordAuthenticationFilter hinzu
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


