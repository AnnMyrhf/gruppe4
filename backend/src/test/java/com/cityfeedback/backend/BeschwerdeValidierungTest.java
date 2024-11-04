package com.cityfeedback.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import com.cityfeedback.backend.services.BeschwerdeService;
import com.cityfeedback.backend.domain.Beschwerde;

public class BeschwerdeValidierungTest {
    private BeschwerdeService BeschwerdeService;

    @BeforeEach
    public void setup() {
        BeschwerdeService = new BeschwerdeService();
    }

    @Test
    public void testGueltigeBeschwerdeDaten() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "OPEN", "Lärm", "Hoch", "Laute Musik in der Nachbarschaft", true, "application/pdf");
        assertTrue(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }

    @Test
    public void testFehlendesStatusFeld() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "", "Lärm", "Hoch", "Laute Musik in der Nachbarschaft", true, "application/pdf");
        assertFalse(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }

    @Test
    public void testUnbekanntesStatusFormat() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "UNBEKANNT", "Lärm", "Hoch", "Laute Musik in der Nachbarschaft", true, "application/pdf");
        assertFalse(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }

    @Test
    public void testInvalidesDatentypAnhangFormat() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "OPEN", "Lärm", "Hoch", "Laute Musik in der Nachbarschaft", true, "invalid/type");
        assertFalse(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }

    @Test
    public void testLeeresTextfeld() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "OPEN", "Lärm", "Hoch", "", true, "application/pdf");
        assertFalse(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }
}
