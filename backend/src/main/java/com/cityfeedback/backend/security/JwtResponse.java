package com.cityfeedback.backend.security;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Nutzdaten/Parameter für die zu überprüfenden Sicherheitsangaben und Berechtigungen
 *
 * @author Ann-Kathrin Meyerhof
 */
@Data
@EqualsAndHashCode
public class JwtResponse {
    private String token;
    private String type = "Bearer"; // Inhaber-Token, Client schickt dann bei jeder Anfrage Token im HTTP-Autorisierungsheader mit
    private Long id;
    private String email;
    private Object role;

    public JwtResponse(String accessToken, Long id, String email, Object[] role) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}
