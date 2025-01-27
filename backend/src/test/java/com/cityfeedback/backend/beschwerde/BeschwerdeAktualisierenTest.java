package com.cityfeedback.backend.beschwerde;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeAktualisieren;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class BeschwerdeAktualisierenTest {

    private Beschwerde beschwerde;
    private BeschwerdeAktualisieren beschwerdeAktualisieren;
    private BeschwerdeAktualisieren beschwerdeAktualisieren2;
    private Timestamp timestamp;

    @BeforeEach
    void setUp() {
        timestamp = new Timestamp(System.currentTimeMillis());
        beschwerdeAktualisieren = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Testkommentar");
        beschwerdeAktualisieren2 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Testkommentar");
    }

    @BeforeEach
    void setUp2() {
        // Vorbereitung: eine Beschwerde mit Testwerten erstellen
        beschwerde = new Beschwerde("Test Titel", "Test Typ", "Test Text", null, null);
        beschwerde.setStatus(Status.IN_BEARBEITUNG);
    }

    /**
     * Überprüft, ob der Standard-Konstruktor ohne Argumente ein korrektes Objekt erstellt.
     */
    @Test
    void testNoArgsConstructor() {
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        assertNotNull(aktualisieren, "Das Objekt sollte instanziiert werden.");
        assertNull(aktualisieren.getId(), "Die ID sollte null sein.");
        assertNull(aktualisieren.getTimestamp(), "Der Timestamp sollte null sein.");
        assertNull(aktualisieren.getTitel(), "Der Titel sollte null sein.");
        assertNull(aktualisieren.getStatus(), "Der Status sollte null sein.");
        assertNull(aktualisieren.getKommentar(), "Der Kommentar sollte null sein");
    }

    /**
     * Überprüft, ob der Konstruktor mit einem `Beschwerde`-Objekt die Felder korrekt initialisiert.
     */
    @Test
    void testConstructorWithBeschwerde() {
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren(beschwerde);

        assertNotNull(aktualisieren, "Das Objekt sollte instanziiert werden.");
        assertEquals(beschwerde.getId(), aktualisieren.getId(), "Die ID sollte übereinstimmen.");
        assertEquals(beschwerde.getTitel(), aktualisieren.getTitel(), "Der Titel sollte übereinstimmen.");
        assertEquals(beschwerde.getStatus(), aktualisieren.getStatus(), "Der Status sollte übereinstimmen.");
        assertEquals(beschwerde.getKommentar(), aktualisieren.getKommentar(), "Der Kommentar sollte übereinstimmen.");
        assertNotNull(aktualisieren.getTimestamp(), "Der Timestamp sollte gesetzt sein.");
    }

    /**
     * Überprüft die equals- und hashCode-Methoden auf korrekte Implementierung.
     */
    @Test
    void testEqualsAndHashCode() {
        BeschwerdeAktualisieren aktualisieren1 = new BeschwerdeAktualisieren(beschwerde);
        BeschwerdeAktualisieren aktualisieren2 = new BeschwerdeAktualisieren(beschwerde);

        assertEquals(aktualisieren1, aktualisieren2, "Die Objekte sollten gleich sein.");
        assertEquals(aktualisieren1.hashCode(), aktualisieren2.hashCode(), "Die Hash-Codes sollten gleich sein.");

        beschwerde.setTitel("Neuer Titel");
        BeschwerdeAktualisieren aktualisieren3 = new BeschwerdeAktualisieren(beschwerde);
        assertNotEquals(aktualisieren1, aktualisieren3, "Die Objekte sollten ungleich sein.");
    }

    /**
     * Überprüft, ob der Getter und Setter für den Titel korrekt funktionieren.
     */
    @Test
    void testSetAndGetTitel() {
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setTitel("Neuer Titel");
        assertEquals("Neuer Titel", aktualisieren.getTitel(), "Der Titel sollte korrekt gesetzt werden.");
    }

    /**
     * Überprüft, ob der Getter und Setter für den Status korrekt funktionieren.
     */
    @Test
    void testSetAndGetStatus() {
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setStatus(Status.IN_BEARBEITUNG);
        assertEquals(Status.IN_BEARBEITUNG, aktualisieren.getStatus(), "Der Status sollte korrekt gesetzt werden.");
    }

    /**
     * Überprüft, ob der Getter und Setter für den Kommentar korrekt funktionieren.
     */
    @Test
    void testSetAndGetKommentar() {
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setKommentar("Neuer Kommentar");
        assertEquals("Neuer Kommentar", aktualisieren.getKommentar(), "Der Kommentar sollte korrekt gesetzt werden.");
    }

    /**
     * Überprüft, ob der Getter und Setter für den Timestamp korrekt funktionieren.
     */
    @Test
    void testSetAndGetTimestamp() {
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
        aktualisieren.setTimestamp(timestamp2);
        assertEquals(timestamp2, aktualisieren.getTimestamp(), "Der Timestamp sollte korrekt gesetzt werden.");
    }

    /**
     * Überprüft die equals-Methode mit verschiedenen Objekten.
     */
    @Test
    void testEquals_Object() {
        BeschwerdeAktualisieren aktualisieren1 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Ein Kommentar");
        BeschwerdeAktualisieren aktualisieren2 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Ein Kommentar");

        assertEquals(aktualisieren1, aktualisieren2, "Die Objekte sollten gleich sein.");

        beschwerdeAktualisieren2.setTitel("Neuer Titel");
        assertNotEquals(aktualisieren1, beschwerdeAktualisieren2, "Die Objekte sollten ungleich sein.");

        assertNotEquals(null, aktualisieren1, "Das Objekt sollte nicht gleich null sein.");
        assertNotEquals(aktualisieren1, new Object(), "Das Objekt sollte nicht gleich einem anderen Typ sein.");
    }

    /**
     * Überprüft den Konstruktor mit Parametern (Long, Timestamp, String, Status, Kommentar).
     */
    @Test
    void testConstructorWithParameters() {
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren(2L, timestamp, "Neuer Titel", Status.IN_BEARBEITUNG, "Ein Kommentar");
        assertEquals(2L, aktualisieren.getId(), "Die ID sollte korrekt gesetzt werden.");
        assertEquals("Neuer Titel", aktualisieren.getTitel(), "Der Titel sollte korrekt gesetzt werden.");
        assertEquals(Status.IN_BEARBEITUNG, aktualisieren.getStatus(), "Der Status sollte korrekt gesetzt werden.");
        assertEquals("Ein Kommentar", aktualisieren.getKommentar(), "Der Kommentar sollte korrekt gesetzt werden.");
        assertEquals(timestamp, aktualisieren.getTimestamp(), "Der Timestamp sollte korrekt gesetzt werden.");
    }

    /**
     * Überprüft die toString-Methode auf korrekte Implementierung.
     */
    @Test
    void testToString() {
        String expectedString = "BeschwerdeAktualisieren(id=1, timestamp=" + timestamp.toString() +
                ", titel=Titel, status=IN_BEARBEITUNG, kommentar=Testkommentar)";
        assertEquals(expectedString, beschwerdeAktualisieren.toString(), "Die toString() Methode sollte korrekt arbeiten.");
    }

    /**
     * Überprüft die hashCode-Methode auf korrekte Implementierung.
     */
    @Test
    void testHashCode() {
        BeschwerdeAktualisieren aktualisieren1 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Ein Kommentar");
        BeschwerdeAktualisieren aktualisieren2 = new BeschwerdeAktualisieren(1L, timestamp, "Titel", Status.IN_BEARBEITUNG, "Ein Kommentar");

        assertEquals(aktualisieren1.hashCode(), aktualisieren2.hashCode(), "Die Hash-Codes sollten gleich sein.");

        beschwerdeAktualisieren2.setTitel("Neuer Titel");
        assertNotEquals(aktualisieren1.hashCode(), beschwerdeAktualisieren2.hashCode(), "Die Hash-Codes sollten ungleich sein.");
    }

    /**
     * Überprüft, ob die setId-Methode die ID korrekt setzt.
     */
    @Test
    void testSetId() {
        BeschwerdeAktualisieren aktualisieren = new BeschwerdeAktualisieren();
        aktualisieren.setId(100L);
        assertEquals(100L, aktualisieren.getId(), "Die ID sollte korrekt gesetzt werden.");
    }
}