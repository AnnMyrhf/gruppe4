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

    @Test
    void testEquals_Object() {
        // Test für equals(Object)
        BeschwerdeErstellen erstellen1 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");
        BeschwerdeErstellen erstellen2 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");

        assertEquals(erstellen1, erstellen2, "Die Objekte sollten gleich sein.");

        beschwerdeErstellen2.setTitel("Neuer Titel");
        assertNotEquals(erstellen1, beschwerdeErstellen2, "Die Objekte sollten ungleich sein.");

        assertNotEquals(null, erstellen1,"Das Objekt sollte nicht gleich null sein.");
        assertNotEquals(erstellen1, new Object(), "Das Objekt sollte nicht gleich einem anderen Typ sein.");
    }

    @Test
    void testHashCode() {
        // Test für hashCode()
        BeschwerdeErstellen erstellen1 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");
        BeschwerdeErstellen erstellen2 = new BeschwerdeErstellen(1L, timestamp, "Titel", Status.EINGEGANGEN, "test@example.com");

        assertEquals(erstellen1.hashCode(), erstellen2.hashCode(), "Die Hash-Codes sollten gleich sein.");

        beschwerdeErstellen2.setTitel("Neuer Titel");
        assertNotEquals(erstellen1.hashCode(), beschwerdeErstellen2.hashCode(), "Die Hash-Codes sollten ungleich sein.");
    }

    @Test
    void testToString() {
        // Test für toString()
        String expectedString = "BeschwerdeErstellen(id=1, timestamp=" + timestamp.toString() +
                ", titel=Titel, status=EINGEGANGEN, email=test@example.com)";
        assertEquals(expectedString, beschwerdeErstellen.toString(), "Die toString() Methode sollte korrekt arbeiten.");
    }

    @Test
    void testSetId() {
        // Test für setId(Long)
        BeschwerdeErstellen erstellen = new BeschwerdeErstellen();
        erstellen.setId(100L);
        assertEquals(100L, erstellen.getId(), "Die ID sollte korrekt gesetzt werden.");
    }
}
