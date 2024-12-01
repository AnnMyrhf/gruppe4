package com.cityfeedback.backend.security.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // automatisch Getter, Setter, toString() usw
@NoArgsConstructor // leerer Konstruktor fuer JPA benoetigt
@AllArgsConstructor
public class LoginDaten {
    private String email;
    private String passwort;
}
