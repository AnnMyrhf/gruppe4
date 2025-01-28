package com.cityfeedback.backend.mitarbeiterverwaltung.domain.events;

import com.cityfeedback.backend.mitarbeiterverwaltung.domain.model.Mitarbeiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MitarbeiterRegistrierenTest {

    private Mitarbeiter testMitarbeiter;
    private Timestamp testTimestamp;

    @BeforeEach
    void setUp() {
        // Initialisiere einen Beispiel-Mitarbeiter für die Tests
        testTimestamp = new Timestamp(System.currentTimeMillis());
        testMitarbeiter = new Mitarbeiter();
        testMitarbeiter.setId(1L);
        testMitarbeiter.setVorname("Anna");
        testMitarbeiter.setNachname("Schmidt");
        testMitarbeiter.setEmail("anna.schmidt@example.com");
    }

    /**
     * Testet den No-Args-Konstruktor und überprüft, ob ein Objekt erstellt wird
     */
    @Test
    void testNoArgsConstructor() {
        MitarbeiterRegistrieren event = new MitarbeiterRegistrieren();
        assertNotNull(event, "No-Args-Konstruktor sollte ein Objekt erstellen");
    }

    /**
     * Testet den All-Args-Konstruktor und überprüft, ob alle Felder korrekt gesetzt werden
     */
    @Test
    void testAllArgsConstructor() {
        MitarbeiterRegistrieren event = new MitarbeiterRegistrieren(
                1L,
                testTimestamp,
                "Anna",
                "Schmidt",
                "anna.schmidt@example.com"
        );

        assertNotNull(event, "All-Args-Konstruktor sollte ein Objekt erstellen");
        assertEquals(1L, event.getId(), "Die ID sollte korrekt gesetzt sein");
        assertEquals(testTimestamp, event.getTimestamp(), "Der Timestamp sollte korrekt gesetzt sein");
        assertEquals("Anna", event.getVorname(), "Der Vorname sollte korrekt gesetzt sein");
        assertEquals("Schmidt", event.getNachname(), "Der Nachname sollte korrekt gesetzt sein");
        assertEquals("anna.schmidt@example.com", event.getEmail(), "Die Email sollte korrekt gesetzt sein");
    }

    /**
     * Testet den Konstruktor, der ein Mitarbeiter-Objekt verwendet und überprüft, ob die Felder korrekt gesetzt werden
     */
    @Test
    void testMitarbeiterConstructor() {
        MitarbeiterRegistrieren event = new MitarbeiterRegistrieren(testMitarbeiter);

        assertNotNull(event, "Der Konstruktor mit Mitarbeiter sollte ein Objekt erstellen");
        assertEquals(testMitarbeiter.getId(), event.getId(), "Die ID sollte mit der Mitarbeiter-ID übereinstimmen");
        assertNotNull(event.getTimestamp(), "Der Timestamp sollte gesetzt sein");
        assertEquals(testMitarbeiter.getVorname(), event.getVorname(), "Der Vorname sollte mit dem Mitarbeiter-Vornamen übereinstimmen");
        assertEquals(testMitarbeiter.getNachname(), event.getNachname(), "Der Nachname sollte mit dem Mitarbeiter-Nachnamen übereinstimmen");
        assertEquals(testMitarbeiter.getEmail(), event.getEmail(), "Die Email sollte mit der Mitarbeiter-Email übereinstimmen");
    }

    /**
     * Testet die Getter- und Setter-Methoden und überprüft, ob die Werte korrekt gesetzt und zurückgegeben werden
     */
    @Test
    void testSettersAndGetters() {
        MitarbeiterRegistrieren event = new MitarbeiterRegistrieren();

        event.setId(2L);
        event.setTimestamp(testTimestamp);
        event.setVorname("Tom");
        event.setNachname("Müller");
        event.setEmail("tom.mueller@example.com");

        assertEquals(2L, event.getId(), "Die ID sollte korrekt gesetzt und zurückgegeben werden");
        assertEquals(testTimestamp, event.getTimestamp(), "Der Timestamp sollte korrekt gesetzt und zurückgegeben werden");
        assertEquals("Tom", event.getVorname(), "Der Vorname sollte korrekt gesetzt und zurückgegeben werden");
        assertEquals("Müller", event.getNachname(), "Der Nachname sollte korrekt gesetzt und zurückgegeben werden");
        assertEquals("tom.mueller@example.com", event.getEmail(), "Die Email sollte korrekt gesetzt und zurückgegeben werden");
    }

    /**
     * Testet die Generierung des Timestamps im Konstruktor und überprüft, ob der Timestamp korrekt gesetzt ist
     */
    @Test
    void testTimestampGeneration() {
        MitarbeiterRegistrieren event = new MitarbeiterRegistrieren(testMitarbeiter);

        assertNotNull(event.getTimestamp(), "Der Timestamp sollte nicht null sein");
        assertTrue(event.getTimestamp().getTime() <= System.currentTimeMillis(),
                "Der Timestamp sollte kleiner oder gleich der aktuellen Zeit sein");
    }

    /**
     * Testet die equals- und hashCode-Methoden und überprüft, ob zwei gleiche Objekte korrekt verglichen werden
     */
    @Test
    void testEqualsAndHashCode() {
        MitarbeiterRegistrieren event1 = new MitarbeiterRegistrieren(testMitarbeiter);
        MitarbeiterRegistrieren event2 = new MitarbeiterRegistrieren(testMitarbeiter);

        assertEquals(event1, event2, "Zwei identische Events sollten gleich sein");
        assertEquals(event1.hashCode(), event2.hashCode(), "Die HashCodes von identischen Events sollten gleich sein");
    }

    /**
     * Testet die toString-Methode und überprüft, ob sie die richtigen Informationen enthält
     */
    @Test
    void testToString() {
        MitarbeiterRegistrieren event = new MitarbeiterRegistrieren(testMitarbeiter);

        String toString = event.toString();
        assertTrue(toString.contains("MitarbeiterRegistrieren"), "Die toString-Methode sollte den Klassennamen enthalten");
        assertTrue(toString.contains("Anna"), "Die toString-Methode sollte den Vornamen enthalten");
        assertTrue(toString.contains("Schmidt"), "Die toString-Methode sollte den Nachnamen enthalten");
        assertTrue(toString.contains("anna.schmidt@example.com"), "Die toString-Methode sollte die Email enthalten");
    }

}
