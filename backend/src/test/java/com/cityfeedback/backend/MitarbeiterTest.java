/*
package com.cityfeedback.backend;

import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService;
import org.springframework.beans.factory.annotation.Autowired;

public class MitarbeiterTest {
    // Repository
    @Autowired
    MitarbeiterRepository mitarbeiterRepository;

    // Testobjekt
    Mitarbeiter mitarbeiter = new Mitarbeiter(1234L,"Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef");

    @Test
    void testAttributeValues() {
        // Überprüfen, ob die Attribute korrekt initialisiert sind
        assertEquals(1234L, mitarbeiter.getId());
        assertEquals("Frau", mitarbeiter.getAnrede());
        assertEquals("Anna", mitarbeiter.getVorname());
        assertEquals("Müller", mitarbeiter.getNachname());
        assertEquals("123456", mitarbeiter.getTelefonnummer());
        assertEquals("Hallo@web.com", mitarbeiter.getEmail());
        assertEquals("Hallo12!", mitarbeiter.getPasswort());
        assertEquals("Verwaltung", mitarbeiter.getAbteilung());
        assertEquals("Chef", mitarbeiter.getPosition());
    }

    @Test
    void testInvalidVornameThrowsException() {
        Mitarbeiter invalidMitarbeiter = new Mitarbeiter(1234L, "Frau", "123Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef");
        MitarbeiterService service = new MitarbeiterService(mitarbeiterRepository);

        // Test, ob eine IllegalArgumentException bei einem ungültigen Vornamen geworfen wird
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createNewMitarbeiter(invalidMitarbeiter);
        });

        // Überprüfen, ob die Nachricht der Exception korrekt ist
        assertEquals("Vorname ist ungültig", thrown.getMessage());
    }

    @Test
    void testInvalidNachnameThrowsException() {
        Mitarbeiter invalidMitarbeiter = new Mitarbeiter(1234L, "Frau", "Anna", "Müller123", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef");
        MitarbeiterService service = new MitarbeiterService(mitarbeiterRepository);

        // Test, ob eine IllegalArgumentException bei einem ungültigen Vornamen geworfen wird
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createNewMitarbeiter(invalidMitarbeiter);
        });

        // Überprüfen, ob die Nachricht der Exception korrekt ist
        assertEquals("Nachname ist ungültig", thrown.getMessage());
    }

    @Test
    void testInvalidPasswortThrowsException() {
        Mitarbeiter invalidMitarbeiter = new Mitarbeiter(1234L, "Frau", "Anna", "Müller", "123456", "Hallo@web.com", "passwort", "Verwaltung", "Chef");
        MitarbeiterService service = new MitarbeiterService(mitarbeiterRepository);

        // Test, ob eine IllegalArgumentException bei einem ungültigen Vornamen geworfen wird
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createNewMitarbeiter(invalidMitarbeiter);
        });

        // Überprüfen, ob die Nachricht der Exception korrekt ist
        assertEquals("Passwort ist ungültig", thrown.getMessage());
    }

    @Test
    void testInvalidEMailThrowsException() {
        Mitarbeiter invalidMitarbeiter = new Mitarbeiter(1234L, "Frau", "Anna", "Müller", "123456", "Halloweb.com", "Hallo123!", "Verwaltung", "Chef");
        MitarbeiterService service = new MitarbeiterService(mitarbeiterRepository);

        // Test, ob eine IllegalArgumentException bei einem ungültigen Vornamen geworfen wird
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createNewMitarbeiter(invalidMitarbeiter);
        });

        // Überprüfen, ob die Nachricht der Exception korrekt ist
        assertEquals("E-Mail ist ungültig", thrown.getMessage());
    }

    @Test
    void testInvalidTelefonnummerThrowsException() {
        Mitarbeiter invalidMitarbeiter = new Mitarbeiter(1234L, "Frau", "Anna", "Müller", "123456A", "Hallo@web.com", "Hallo123!", "Verwaltung", "Chef");
        MitarbeiterService service = new MitarbeiterService(mitarbeiterRepository);

        // Test, ob eine IllegalArgumentException bei einem ungültigen Vornamen geworfen wird
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createNewMitarbeiter(invalidMitarbeiter);
        });

        // Überprüfen, ob die Nachricht der Exception korrekt ist
        assertEquals("Telefonnummer ist ungültig", thrown.getMessage());
    }

}
*/
