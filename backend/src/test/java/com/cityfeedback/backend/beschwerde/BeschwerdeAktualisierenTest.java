package com.cityfeedback.backend.beschwerdeverwaltung.domain.events;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Prioritaet;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class BeschwerdeAktualisierenTest {

    private Beschwerde beschwerde;
    private BeschwerdeAktualisieren beschwerdeAktualisieren;

    @BeforeEach
    void setUp() {
        // Vorbereitung: eine Beschwerde mit Testwerten erstellen
        beschwerde = new Beschwerde("Test Titel", "Test Typ", "Test Text", null, null);
        beschwerde.setStatus(Status.IN_BEARBEITUNG);
        beschwerde.setPrioritaet(Prioritaet.HOCH);
    }

    @Test
    void testNoArgsConstructor() {
        // Test für den Standard-Konstruktor ohne Argumente
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        assertNotNull(aktualisieren, "Das Objekt sollte instanziiert werden.");
        assertNull(aktualisieren.getId(), "Die ID sollte null sein.");
        assertNull(aktualisieren.getTimestamp(), "Der Timestamp sollte null sein.");
        assertNull(aktualisieren.getTitel(), "Der Titel sollte null sein.");
        assertNull(aktualisieren.getStatus(), "Der Status sollte null sein.");
        assertNull(aktualisieren.getPrioritaet(), "Die Priorität sollte null sein.");
    }

    @Test
    void testConstructorWithBeschwerde() {
        // Test für den Konstruktor, der ein `Beschwerde`-Objekt erwartet
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren(beschwerde);

        assertNotNull(aktualisieren, "Das Objekt sollte instanziiert werden.");
        assertEquals(beschwerde.getId(), aktualisieren.getId(), "Die ID sollte übereinstimmen.");
        assertEquals(beschwerde.getTitel(), aktualisieren.getTitel(), "Der Titel sollte übereinstimmen.");
        assertEquals(beschwerde.getStatus(), aktualisieren.getStatus(), "Der Status sollte übereinstimmen.");
        assertEquals(beschwerde.getPrioritaet(), aktualisieren.getPrioritaet(), "Die Priorität sollte übereinstimmen.");
        assertNotNull(aktualisieren.getTimestamp(), "Der Timestamp sollte gesetzt sein.");
    }

    @Test
    void testEqualsAndHashCode() {
        // Test für die equals- und hashCode-Methoden
        BeschwerdeAktualisieren aktualisieren1 = new BeschwerdeAktualisieren(beschwerde);
        BeschwerdeAktualisieren aktualisieren2 = new BeschwerdeAktualisieren(beschwerde);

        assertEquals(aktualisieren1, aktualisieren2, "Die Objekte sollten gleich sein.");
        assertEquals(aktualisieren1.hashCode(), aktualisieren2.hashCode(), "Die Hash-Codes sollten gleich sein.");

        // Test für Ungleichheit
        beschwerde.setTitel("Neuer Titel");
        BeschwerdeAktualisieren aktualisieren3 = new BeschwerdeAktualisieren(beschwerde);
        assertNotEquals(aktualisieren1, aktualisieren3, "Die Objekte sollten ungleich sein.");
    }

    @Test
    void testSetAndGetTitel() {
        // Test für den Getter und Setter des Titels
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setTitel("Neuer Titel");
        assertEquals("Neuer Titel", aktualisieren.getTitel(), "Der Titel sollte korrekt gesetzt werden.");
    }

    @Test
    void testSetAndGetStatus() {
        // Test für den Getter und Setter des Status
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setStatus(Status.IN_BEARBEITUNG);
        assertEquals(Status.IN_BEARBEITUNG, aktualisieren.getStatus(), "Der Status sollte korrekt gesetzt werden.");
    }

    @Test
    void testSetAndGetPrioritaet() {
        // Test für den Getter und Setter der Priorität
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setPrioritaet(Prioritaet.MITTEL);
        assertEquals(Prioritaet.MITTEL, aktualisieren.getPrioritaet(), "Die Priorität sollte korrekt gesetzt werden.");
    }

    @Test
    void testSetAndGetTimestamp() {
        // Test für den Getter und Setter des Timestamps
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        aktualisieren.setTimestamp(timestamp);
        assertEquals(timestamp, aktualisieren.getTimestamp(), "Der Timestamp sollte korrekt gesetzt werden.");
    }
}
