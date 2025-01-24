package com.cityfeedback.backend.beschwerdeverwaltung.domain.events;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class BeschwerdeAktualisierenTest {

    private Beschwerde beschwerde;
    private BeschwerdeAktualisieren beschwerdeAktualisieren;
    private BeschwerdeAktualisieren beschwerdeAktualisieren2;
    private Timestamp timestamp;

    @BeforeEach
    void setUp() {
        timestamp = new Timestamp(System.currentTimeMillis());
        beschwerdeAktualisieren = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Test Kommentar");
        beschwerdeAktualisieren2 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Test Kommentar");
    }

    @BeforeEach
    void setUp2() {
        // Vorbereitung: eine Beschwerde mit Testwerten erstellen
        beschwerde = new Beschwerde("Test Titel", "Test Typ", "Test Text", null, null);
        beschwerde.setStatus(Status.IN_BEARBEITUNG);
        beschwerde.setKommentar("Test Kommentar");
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
        assertNull(aktualisieren.getKommentar(), "Das Kommentar sollte null sein.");
    }

    @Test
    void testConstructorWithBeschwerde() {
        // Test für den Konstruktor, der ein `Beschwerde`-Objekt erwartet
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren(beschwerde);

        assertNotNull(aktualisieren, "Das Objekt sollte instanziiert werden.");
        assertEquals(beschwerde.getId(), aktualisieren.getId(), "Die ID sollte übereinstimmen.");
        assertEquals(beschwerde.getTitel(), aktualisieren.getTitel(), "Der Titel sollte übereinstimmen.");
        assertEquals(beschwerde.getStatus(), aktualisieren.getStatus(), "Der Status sollte übereinstimmen.");
        assertEquals(beschwerde.getKommentar(), aktualisieren.getKommentar(), "Das Kommentar sollte übereinstimmen.");
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
    void testSetAndGetKommentar() {
        // Test für den Getter und Setter des Kommentars
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setKommentar("Test Kommentar");
        assertEquals("Test Kommentar", aktualisieren.getKommentar(), "Das Kommentar sollte korrekt gesetzt werden.");
    }

    @Test
    void testSetAndGetTimestamp() {
        // Test für den Getter und Setter des Timestamps
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        aktualisieren.setTimestamp(timestamp);
        assertEquals(timestamp, aktualisieren.getTimestamp(), "Der Timestamp sollte korrekt gesetzt werden.");
    }

    @Test
    void testEquals_Object() {
        // Test für equals(Object)
        BeschwerdeAktualisieren aktualisieren1 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Test Kommentar");
        BeschwerdeAktualisieren aktualisieren2 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Test Kommentar");

        assertEquals(aktualisieren1, aktualisieren2, "Die Objekte sollten gleich sein.");

        beschwerdeAktualisieren2.setTitel("Neuer Titel");
        assertNotEquals(aktualisieren1, beschwerdeAktualisieren2, "Die Objekte sollten ungleich sein.");

        assertNotEquals(aktualisieren1, null, "Das Objekt sollte nicht gleich null sein.");
        assertNotEquals(aktualisieren1, new Object(), "Das Objekt sollte nicht gleich einem anderen Typ sein.");
    }

    @Test
    void testConstructorWithParameters() {
        // Test für den Konstruktor mit Parametern (Long, Timestamp, String, Status, Kommentar)
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren(2L, timestamp, "Neuer Titel", Status.IN_BEARBEITUNG, "Test Kommentar");
        assertEquals(2L, aktualisieren.getId(), "Die ID sollte korrekt gesetzt werden.");
        assertEquals("Neuer Titel", aktualisieren.getTitel(), "Der Titel sollte korrekt gesetzt werden.");
        assertEquals(Status.IN_BEARBEITUNG, aktualisieren.getStatus(), "Der Status sollte korrekt gesetzt werden.");
        assertEquals("Test Kommentar", aktualisieren.getKommentar(), "Das Kommentar sollte korrekt gesetzt werden.");
        assertEquals(timestamp, aktualisieren.getTimestamp(), "Der Timestamp sollte korrekt gesetzt werden.");
    }

    @Test
    void testToString() {
        // Test für toString()
        String expectedString = "BeschwerdeAktualisieren(id=1, timestamp=" + timestamp.toString() +
                ", titel=Titel, status=IN_BEARBEITUNG, kommentar=Test Kommentar)";
        assertEquals(expectedString, beschwerdeAktualisieren.toString(), "Die toString() Methode sollte korrekt arbeiten.");
    }

    @Test
    void testHashCode() {
        // Test für hashCode()
        BeschwerdeAktualisieren aktualisieren1 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Test Kommentar");
        BeschwerdeAktualisieren aktualisieren2 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Test Kommentar");

        assertEquals(aktualisieren1.hashCode(), aktualisieren2.hashCode(), "Die Hash-Codes sollten gleich sein.");

        beschwerdeAktualisieren2.setTitel("Neuer Titel");
        assertNotEquals(aktualisieren1.hashCode(), beschwerdeAktualisieren2.hashCode(), "Die Hash-Codes sollten ungleich sein.");
    }

    @Test
    void testSetId() {
        // Test für setId(Long)
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setId(100L);
        assertEquals(100L, aktualisieren.getId(), "Die ID sollte korrekt gesetzt werden.");
    }
}
