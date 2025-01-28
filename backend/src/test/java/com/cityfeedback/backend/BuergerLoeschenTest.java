package com.cityfeedback.backend.buergerverwaltung.domain.events;

import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class BuergerLoeschenTest {

    private Buerger testBuerger;
    private Timestamp testTimestamp;

    @BeforeEach
    void setUp() {
        // Beispiel-Buerger für Tests initialisieren
        testTimestamp = new Timestamp(System.currentTimeMillis());
        testBuerger = new Buerger();
        testBuerger.setId(1L);
        testBuerger.setVorname("Max");
        testBuerger.setNachname("Mustermann");
        testBuerger.setEmail("max.mustermann@example.com");
    }

    /**
     * Testet den No-Args-Konstruktor der Klasse BuergerLoeschen.
     * Stellt sicher, dass der Konstruktor ein gültiges Objekt erstellt.
     */
    @Test
    void testNoArgsConstructor() {
        BuergerLoeschen event = new BuergerLoeschen();
        assertNotNull(event, "No-Args-Konstruktor sollte ein Objekt erstellen");
    }

    /**
     * Testet den All-Args-Konstruktor der Klasse BuergerLoeschen.
     * Stellt sicher, dass alle Felder korrekt initialisiert werden.
     */
    @Test
    void testAllArgsConstructor() {
        BuergerLoeschen event = new BuergerLoeschen(
                1L,
                testTimestamp,
                "Max",
                "Mustermann",
                "max.mustermann@example.com"
        );

        assertNotNull(event, "All-Args-Konstruktor sollte ein Objekt erstellen");
        assertEquals(1L, event.getId(), "Die ID sollte korrekt gesetzt sein");
        assertEquals(testTimestamp, event.getTimestamp(), "Der Timestamp sollte korrekt gesetzt sein");
        assertEquals("Max", event.getVorname(), "Der Vorname sollte korrekt gesetzt sein");
        assertEquals("Mustermann", event.getNachname(), "Der Nachname sollte korrekt gesetzt sein");
        assertEquals("max.mustermann@example.com", event.getEmail(), "Die Email sollte korrekt gesetzt sein");
    }

    /**
     * Testet den Konstruktor der Klasse BuergerLoeschen,
     * der ein Buerger-Objekt als Parameter verwendet.
     * Stellt sicher, dass die Felder mit den Werten des Buerger-Objekts initialisiert werden.
     */
    @Test
    void testBuergerConstructor() {
        BuergerLoeschen event = new BuergerLoeschen(testBuerger);

        assertNotNull(event, "Der Konstruktor mit Buerger sollte ein Objekt erstellen");
        assertEquals(testBuerger.getId(), event.getId(), "Die ID sollte mit der Buerger-ID übereinstimmen");
        assertNotNull(event.getTimestamp(), "Der Timestamp sollte gesetzt sein");
        assertEquals(testBuerger.getVorname(), event.getVorname(), "Der Vorname sollte mit dem Buerger-Vornamen übereinstimmen");
        assertEquals(testBuerger.getNachname(), event.getNachname(), "Der Nachname sollte mit dem Buerger-Nachnamen übereinstimmen");
        assertEquals(testBuerger.getEmail(), event.getEmail(), "Die Email sollte mit der Buerger-Email übereinstimmen");
    }

    /**
     * Testet die Getter- und Setter-Methoden der Klasse BuergerLoeschen.
     * Stellt sicher, dass die Werte korrekt gesetzt und abgerufen werden können.
     */
    @Test
    void testSettersAndGetters() {
        BuergerLoeschen event = new BuergerLoeschen();

        event.setId(2L);
        event.setTimestamp(testTimestamp);
        event.setVorname("Lisa");
        event.setNachname("Müller");
        event.setEmail("lisa.mueller@example.com");

        assertEquals(2L, event.getId(), "Die ID sollte korrekt gesetzt und zurückgegeben werden");
        assertEquals(testTimestamp, event.getTimestamp(), "Der Timestamp sollte korrekt gesetzt und zurückgegeben werden");
        assertEquals("Lisa", event.getVorname(), "Der Vorname sollte korrekt gesetzt und zurückgegeben werden");
        assertEquals("Müller", event.getNachname(), "Der Nachname sollte korrekt gesetzt und zurückgegeben werden");
        assertEquals("lisa.mueller@example.com", event.getEmail(), "Die Email sollte korrekt gesetzt und zurückgegeben werden");
    }

    /**
     * Testet, ob der Timestamp im Konstruktor korrekt generiert wird.
     * Stellt sicher, dass der Timestamp nicht null ist und der aktuellen Zeit entspricht.
     */
    @Test
    void testTimestampGeneration() {
        BuergerLoeschen event = new BuergerLoeschen(testBuerger);

        assertNotNull(event.getTimestamp(), "Der Timestamp sollte nicht null sein");
        assertTrue(event.getTimestamp().getTime() <= System.currentTimeMillis(),
                "Der Timestamp sollte kleiner oder gleich der aktuellen Zeit sein");
    }

    /**
     * Testet die equals- und hashCode-Methoden der Klasse BuergerLoeschen.
     * Stellt sicher, dass zwei identische Objekte als gleich betrachtet werden
     * und dass ihre HashCodes übereinstimmen.
     */
    @Test
    void testEqualsAndHashCode() {
        BuergerLoeschen event1 = new BuergerLoeschen(testBuerger);
        BuergerLoeschen event2 = new BuergerLoeschen(testBuerger);

        assertEquals(event1, event2, "Zwei identische Events sollten gleich sein");
        assertEquals(event1.hashCode(), event2.hashCode(), "Die HashCodes von identischen Events sollten gleich sein");
    }

    /**
     * Testet die toString-Methode der Klasse BuergerLoeschen.
     * Stellt sicher, dass alle relevanten Felder im String enthalten sind.
     */
    @Test
    void testToString() {
        BuergerLoeschen event = new BuergerLoeschen(testBuerger);

        String toString = event.toString();
        assertTrue(toString.contains("BuergerLoeschen"), "Die toString-Methode sollte den Klassennamen enthalten");
        assertTrue(toString.contains("Max"), "Die toString-Methode sollte den Vornamen enthalten");
        assertTrue(toString.contains("Mustermann"), "Die toString-Methode sollte den Nachnamen enthalten");
        assertTrue(toString.contains("max.mustermann@example.com"), "Die toString-Methode sollte die Email enthalten");
    }

}
