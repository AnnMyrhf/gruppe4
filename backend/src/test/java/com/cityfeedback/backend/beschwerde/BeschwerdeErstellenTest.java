package com.cityfeedback.backend.beschwerdeverwaltung.domain.events;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class BeschwerdeErstellenTest {

    private BeschwerdeErstellen beschwerdeErstellen;
    private BeschwerdeErstellen beschwerdeErstellen2;
    private Timestamp timestamp;

    @BeforeEach
    void setUp() {
        timestamp = new Timestamp(System.currentTimeMillis());
        beschwerdeErstellen = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");
        beschwerdeErstellen2 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");
    }

    /**
     * Überprüft die equals-Methode mit verschiedenen Objekten.
     */
    @Test
    void testEquals_Object() {
        BeschwerdeErstellen erstellen1 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");
        BeschwerdeErstellen erstellen2 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");

        // Überprüfung der Gleichheit von zwei identischen Objekten
        assertEquals(erstellen1, erstellen2, "Die Objekte sollten gleich sein.");

        // Änderung eines Wertes, um die Ungleichheit zu prüfen
        erstellen2.setTitel("Neuer Titel");
        assertNotEquals(erstellen1, erstellen2, "Die Objekte sollten ungleich sein.");

        // Überprüfung der Ungleichheit mit null
        assertNotEquals(null, erstellen1, "Das Objekt sollte nicht gleich null sein.");

        // Überprüfung der Ungleichheit mit einem Objekt eines anderen Typs
        assertNotEquals(erstellen1, new Object(), "Das Objekt sollte nicht gleich einem anderen Typ sein.");
    }

    /**
     * Überprüft die hashCode-Methode auf korrekte Implementierung.
     */
    @Test
    void testHashCode() {
        BeschwerdeErstellen erstellen1 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");
        BeschwerdeErstellen erstellen2 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");

        // Überprüfung, ob die Hash-Codes für gleiche Objekte identisch sind
        assertEquals(erstellen1.hashCode(), erstellen2.hashCode(), "Die Hash-Codes sollten gleich sein.");

        // Änderung eines Wertes, um die Ungleichheit der Hash-Codes zu prüfen
        erstellen2.setTitel("Neuer Titel");
        assertNotEquals(erstellen1.hashCode(), erstellen2.hashCode(), "Die Hash-Codes sollten ungleich sein.");
    }

    /**
     * Überprüft die toString-Methode auf korrekte Implementierung.
     */
    @Test
    void testToString() {
        BeschwerdeErstellen erstellen = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");

        // Erwartete Ausgabe des toString-Aufrufs
        String expectedString = "BeschwerdeErstellen(id=1, timestamp=" + timestamp.toString() +
                ", titel=Titel, status=EINGEGANGEN, email=test@example.com)";

        // Überprüfung, ob die tatsächliche Ausgabe der erwarteten entspricht
        assertEquals(expectedString, erstellen.toString(), "Die toString() Methode sollte korrekt arbeiten.");
    }

    /**
     * Überprüft, ob die setId-Methode die ID korrekt setzt.
     */
    @Test
    void testSetId() {
        BeschwerdeErstellen erstellen = new BeschwerdeErstellen();

        // Setzen einer neuen ID
        erstellen.setId(100L);

        // Überprüfung, ob die ID korrekt gesetzt wurde
        assertEquals(100L, erstellen.getId(), "Die ID sollte korrekt gesetzt werden.");
    }

    /**
     * Überprüft, ob der Status einer Beschwerde korrekt gesetzt wird.
     */
    @Test
    void testSetStatus() {
        // Arrange: Erstellen einer Instanz der Klasse BeschwerdeErstellen
        BeschwerdeErstellen event = new BeschwerdeErstellen();

        // Neuer Status
        Status newStatus = Status.IN_BEARBEITUNG;

        // Act: Setzen des neuen Status
        event.setStatus(newStatus);

        // Assert: Überprüfen, ob der Status korrekt gesetzt wurde
        assertEquals(newStatus, event.getStatus(), "Der Status sollte korrekt aktualisiert werden.");
    }

    /**
     * Überprüft, ob die E-Mail-Adresse einer Beschwerde korrekt gesetzt wird.
     */
    @Test
    void testSetEmail() {
        // Arrange: Erstellen einer Instanz der Klasse BeschwerdeErstellen
        BeschwerdeErstellen event = new BeschwerdeErstellen();

        // Neue E-Mail-Adresse
        String newEmail = "neue.email@example.com";

        // Act: Setzen der neuen E-Mail-Adresse
        event.setEmail(newEmail);

        // Assert: Überprüfen, ob die E-Mail korrekt gesetzt wurde
        assertEquals(newEmail, event.getEmail(), "Die E-Mail-Adresse sollte korrekt aktualisiert werden.");
    }

    /**
     * Überprüft, ob der Zeitstempel einer Beschwerde korrekt gesetzt wird.
     */
    @Test
    void testSetTimestamp() {
        // Arrange: Erstellen einer Instanz der Klasse BeschwerdeErstellen
        BeschwerdeErstellen event = new BeschwerdeErstellen();

        // Neuer Zeitstempel
        Timestamp newTimestamp = new Timestamp(System.currentTimeMillis());

        // Act: Setzen des neuen Zeitstempels
        event.setTimestamp(newTimestamp);

        // Assert: Überprüfen, ob der Zeitstempel korrekt gesetzt wurde
        assertEquals(newTimestamp, event.getTimestamp(), "Der Zeitstempel sollte korrekt aktualisiert werden.");
    }
}