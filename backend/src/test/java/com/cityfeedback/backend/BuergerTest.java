package com.cityfeedback.backend;

import com.cityfeedback.backend.domain.Buerger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testklasse fuer Buerger
 */
public class BuergerTest {

    // Testobjekt
   Buerger buerger = new Buerger("123", "Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!");

    // Ueberprueft, ob alle Attribute gesetzt sind
    @Test
    public void testBuergerAttributes() {
        assertNotNull(buerger.getId());
        assertNotNull(buerger.getAnrede());
        assertNotNull(buerger.getVorname());
        assertNotNull(buerger.getNachname());
        assertNotNull(buerger.getTelefonnummer());
        assertNotNull(buerger.getEmail());
        assertNotNull(buerger.getPasswort());

        // RegEx
        String buchstabenRegEx = "^[a-zA-Z]+$";
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // ueberprüft, ob E-Mail-Adresse aus einem Local-Part (Buchstaben, Zahlen, Unterstriche, Bindestriche, Punkte), einem @-Zeichen und einer Domain (mindestens zwei Teile, getrennt durch Punkte, und ein TLD mit zwei bis vier Zeichen) besteht.
        String telefonnummerRegex = "^\\d+$"; // nur Zahlen
        String passwortRegEx ="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; // Passwort mit mindestens 8 Zeichen und mindestens einem Buchstaben, einer Zahl und einem Sonderzeichen

        // Ueberpruefen, ob die Werte den RegEx entsprechen
        assertTrue(buerger.getVorname().matches(buchstabenRegEx));
        assertTrue(buerger.getNachname().matches(buchstabenRegEx));
        assertTrue(buerger.getEmail().matches(emailRegex));
        assertTrue(buerger.getTelefonnummer().matches(telefonnummerRegex));
        assertTrue(buerger.getPasswort().matches(passwortRegEx));
    }
}
