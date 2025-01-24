package com.cityfeedback.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class BuergerRegistrierenTest {

    private BuergerRegistrieren buergerRegistrieren;
    private BuergerRegistrieren buergerRegistrieren2;
    private Timestamp timestamp;

    @BeforeEach
    void setUp() {
        timestamp = new Timestamp(System.currentTimeMillis());
        buergerRegistrieren = new BuergerRegistrieren(1L, timestamp, "Max", "Mustermann", "max.mustermann@example.com");
        buergerRegistrieren2 = new BuergerRegistrieren(1L, timestamp, "Max", "Mustermann", "max.mustermann@example.com");
    }

    @Test
    void testEquals_Object() {
        // Test für equals(Object)
        BuergerRegistrieren registrieren1 = new BuergerRegistrieren(1L, timestamp, "Max", "Mustermann", "max.mustermann@example.com");
        BuergerRegistrieren registrieren2 = new BuergerRegistrieren(1L, timestamp, "Max", "Mustermann", "max.mustermann@example.com");

        assertEquals(registrieren1, registrieren2, "Die Objekte sollten gleich sein.");

        buergerRegistrieren2.setVorname("John");
        assertNotEquals(registrieren1, buergerRegistrieren2, "Die Objekte sollten ungleich sein.");

        assertNotEquals(registrieren1, null, "Das Objekt sollte nicht gleich null sein.");
        assertNotEquals(registrieren1, new Object(), "Das Objekt sollte nicht gleich einem anderen Typ sein.");
    }

    @Test
    void testHashCode() {
        // Test für hashCode()
        BuergerRegistrieren registrieren1 = new BuergerRegistrieren(1L, timestamp, "Max", "Mustermann", "max.mustermann@example.com");
        BuergerRegistrieren registrieren2 = new BuergerRegistrieren(1L, timestamp, "Max", "Mustermann", "max.mustermann@example.com");

        assertEquals(registrieren1.hashCode(), registrieren2.hashCode(), "Die Hash-Codes sollten gleich sein.");

        buergerRegistrieren2.setVorname("John");
        assertNotEquals(registrieren1.hashCode(), buergerRegistrieren2.hashCode(), "Die Hash-Codes sollten ungleich sein.");
    }

    @Test
    void testToString() {
        // Test für toString()
        String expectedString = "BuergerRegistrieren(id=1, timestamp=" + timestamp.toString() +
                ", vorname=Max, nachname=Mustermann, email=max.mustermann@example.com)";
        assertEquals(expectedString, buergerRegistrieren.toString(), "Die toString() Methode sollte korrekt arbeiten.");
    }

    @Test
    void testSetId() {
        // Test für setId(Long)
        BuergerRegistrieren registrieren = new BuergerRegistrieren();
        registrieren.setId(100L);
        assertEquals(100L, registrieren.getId(), "Die ID sollte korrekt gesetzt werden.");
    }

}
