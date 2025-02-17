package com.cityfeedback.backend.security;

import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.domain.model.Mitarbeiter;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Klasse für Generierung, Parsen und Validierung von JWT
 *
 * @author Ann-Kathrin Meyerhof
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}") // JWT ist 24 Std. gueltig
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;
/*
    // Erstmalige JWT-Erzeugung aus E-Mail, Datum, Ablaufdatum und "supersecure-Geheimnis" (siehe applications propperties)
    public String generateJwtToken(Authentication authentication) {

        Mitarbeiter userPrincipal = (Mitarbeiter) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }*/

    // Erstmalige JWT-Erzeugung aus E-Mail, Datum, Ablaufdatum und "supersecure-Geheimnis" (siehe applications propperties)
    public String generateJwtToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        // Bestimmt den Benutzertyp (Buerger oder Mitarbeiter) basierend auf dem Principal
        if (principal instanceof Buerger) {
            Buerger buerger = (Buerger) principal;
            return Jwts.builder()
                    .setSubject(buerger.getEmail())
                    .claim("role", "BUERGER") // Fuegt einen Claim für die Rolle hinzu
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        } else if (principal instanceof Mitarbeiter) {
            Mitarbeiter mitarbeiter = (Mitarbeiter) principal;
            return Jwts.builder()
                    .setSubject(mitarbeiter.getEmail())
                    .claim("role", "MITARBEITER")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        } else {
            throw new IllegalArgumentException("Unsupported principal type");
        }
    }


    // Manuelle JWT-Generierung (wenn Benutzerdaten geändert werden)
    public String generateJwtTokenMitEmail(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    // Email aus JWT abrufen
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    // JWT validieren & parsen
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
            return false;
        }
    }
}
