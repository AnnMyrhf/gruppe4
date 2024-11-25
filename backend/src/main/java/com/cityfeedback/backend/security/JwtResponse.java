package com.cityfeedback.backend.security;

import lombok.Data;

/**
 * Nutzdaten/Parameter für die zu überprüfenden Sicherheitsangaben und Berechtigungen
 *
 * @author Ann-Kathrin Meyerhof
 */
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer"; // Inhaber-Token, Client schickt dann bei jeder Anfrage Token im HTTP-Autorisierungsheader mit
    private Long id;
    private String email;

    public JwtResponse(String accessToken, Long benutzerId, String email) {
        this.token = accessToken;
        this.id = benutzerId;
        this.email = email;
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
