package com.cityfeedback.backend.security.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Repraesentiert die Anmeldeinformationen eines Benutzers der CityFeedback-Anwendung (Buerger oder Mitarbeiter)
 *
 * @author Ann-Kathrin Meyerhof
 */
@Data // automatisch Getter, Setter, toString() usw
@NoArgsConstructor // leerer Konstruktor fuer JPA benoetigt
@AllArgsConstructor
public class LoginDaten {
    private String email;
    private String passwort;
}
