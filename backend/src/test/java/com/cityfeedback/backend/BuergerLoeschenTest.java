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

    @Test
    void testNoArgsConstructor() {
        // Teste den No-Args-Konstruktor
        BuergerLoeschen event = new BuergerLoeschen();
        assertNotNull(event, "No-Args-Konstruktor sollte ein Objekt erstellen");
    }

    @Test
    void testAllArgsConstructor() {
        // Teste den All-Args-Konstruktor
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

    @Test
    void testBuergerConstructor() {
        // Teste den Konstruktor, der ein Buerger-Objekt verwendet
        BuergerLoeschen event = new BuergerLoeschen(testBuerger);

        assertNotNull(event, "Der Konstruktor mit Buerger sollte ein Objekt erstellen");
        assertEquals(testBuerger.getId(), event.getId(), "Die ID sollte mit der Buerger-ID übereinstimmen");
        assertNotNull(event.getTimestamp(), "Der Timestamp sollte gesetzt sein");
        assertEquals(testBuerger.getVorname(), event.getVorname(), "Der Vorname sollte mit dem Buerger-Vornamen übereinstimmen");
        assertEquals(testBuerger.getNachname(), event.getNachname(), "Der Nachname sollte mit dem Buerger-Nachnamen übereinstimmen");
        assertEquals(testBuerger.getEmail(), event.getEmail(), "Die Email sollte mit der Buerger-Email übereinstimmen");
    }

    @Test
    void testSettersAndGetters() {
        // Teste die Getter und Setter
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

    @Test
    void testTimestampGeneration() {
        // Teste, ob der Timestamp im Konstruktor korrekt generiert wird
        BuergerLoeschen event = new BuergerLoeschen(testBuerger);

        assertNotNull(event.getTimestamp(), "Der Timestamp sollte nicht null sein");
        assertTrue(event.getTimestamp().getTime() <= System.currentTimeMillis(),
                "Der Timestamp sollte kleiner oder gleich der aktuellen Zeit sein");
    }

    @Test
    void testEqualsAndHashCode() {
        // Teste die equals- und hashCode-Methoden
        BuergerLoeschen event1 = new BuergerLoeschen(testBuerger);
        BuergerLoeschen event2 = new BuergerLoeschen(testBuerger);

        assertEquals(event1, event2, "Zwei identische Events sollten gleich sein");
        assertEquals(event1.hashCode(), event2.hashCode(), "Die HashCodes von identischen Events sollten gleich sein");
    }

    @Test
    void testToString() {
        // Teste die toString-Methode
        BuergerLoeschen event = new BuergerLoeschen(testBuerger);

        String toString = event.toString();
        assertTrue(toString.contains("BuergerLoeschen"), "Die toString-Methode sollte den Klassennamen enthalten");
        assertTrue(toString.contains("Max"), "Die toString-Methode sollte den Vornamen enthalten");
        assertTrue(toString.contains("Mustermann"), "Die toString-Methode sollte den Nachnamen enthalten");
        assertTrue(toString.contains("max.mustermann@example.com"), "Die toString-Methode sollte die Email enthalten");
    }
}
