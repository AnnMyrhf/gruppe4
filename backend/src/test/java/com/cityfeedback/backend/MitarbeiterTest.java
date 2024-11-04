package com.cityfeedback.backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cityfeedback.backend.domain.Mitarbeiter;

public class MitarbeiterTest {

    // Testobjekt
    Mitarbeiter mitarbeiter = new Mitarbeiter("1234","Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef");

    @Test
    void testAttributeValues() {
        // Überprüfen, ob die Attribute korrekt initialisiert sind
        assertEquals("1234", mitarbeiter.getId());
        assertEquals("Frau", mitarbeiter.getAnrede());
        assertEquals("Anna", mitarbeiter.getVorname());
        assertEquals("Müller", mitarbeiter.getNachname());
        assertEquals("123456", mitarbeiter.getTelefonnummer());
        assertEquals("Hallo@web.com", mitarbeiter.getEmail());
        assertEquals("Hallo12!", mitarbeiter.getPasswort());
        assertEquals("Verwaltung", mitarbeiter.getAbteilung());
        assertEquals("Chef", mitarbeiter.getPosition());

        // Sicherstellen, dass das Mitarbeiter-Objekt nicht null ist
        assertNotNull(mitarbeiter);
    }

    @Test
    void testVorname(){
        String buchstabenRegEx = "^[a-zA-ZäöüÄÖÜß-]+$";

        assertTrue(mitarbeiter.getVorname().matches(buchstabenRegEx));
    }

    @Test
    void testNachname(){
        String buchstabenRegEx = "^[a-zA-ZäöüÄÖÜß-]+$";

        assertTrue(mitarbeiter.getNachname().matches(buchstabenRegEx));
    }



    @Test
    void testEmail(){
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // ueberprüft, ob E-Mail-Adresse aus einem Local-Part (Buchstaben, Zahlen, Unterstriche, Bindestriche, Punkte), einem @-Zeichen und einer Domain (mindestens zwei Teile, getrennt durch Punkte, und ein TLD mit zwei bis vier Zeichen) besteht.

        assertTrue(mitarbeiter.getEmail().matches(emailRegex));

    }

    @Test
    void testTelefonnummer(){
        String telefonnummerRegex = "^\\d+$"; // nur Zahlen

        assertTrue(mitarbeiter.getTelefonnummer().matches(telefonnummerRegex));

    }

    @Test
    void testPasswort(){
        String passwortRegEx ="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; // Passwort mit mindestens 8 Zeichen und mindestens einem Buchstaben, einer Zahl und einem Sonderzeichen

        assertTrue(mitarbeiter.getPasswort().matches(passwortRegEx));
    }
}
