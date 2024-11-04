package com.cityfeedback.backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.cityfeedback.backend.domain.Mitarbeiter;


public class MitarbeiterTest {

    // Testobjekt
    Mitarbeiter mitarbeiter = new Mitarbeiter("abcde", "Frau", "Anna", "Müller", "123456", "Hallo@web.com", "37", "Chef");
    @Test
    void notNullTest() {
        assertNotNull(mitarbeiter.getId());
    }
}