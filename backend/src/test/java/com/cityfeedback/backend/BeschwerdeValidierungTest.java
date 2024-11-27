/*
package com.cityfeedback.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import java.util.regex.Pattern;
import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.model.Beschwerde;

public class BeschwerdeValidierungTest {
    private BeschwerdeService BeschwerdeService;

    @BeforeEach
    public void setup() {
        BeschwerdeService = new BeschwerdeService();
    }

    @Test
    public void testGueltigeBeschwerdeDaten() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "OPEN", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf");
        assertTrue(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }

    @Test
    public void testAnhangOptional() {
        // Erstellen einer Beschwerde ohne Anhang
        assertDoesNotThrow(() -> new Beschwerde(new Date(), "OPEN", "Infrastruktur", "hoch", "Beschwerdetext", false, "pdf"));
    }

    @Test
    public void testFehlendesStatusFeld() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf");
        assertFalse(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }

    @Test
    public void testUnbekanntesStatusFormat() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "UNBEKANNT", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf");
        assertFalse(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }

    @Test
    void testAnhangFormatValid() {
        String validAnhangPdf = "dokument.pdf";
        String validAnhangJpg = "bild.jpg";
        String invalidAnhang = "datei.doc";

        Pattern pattern = Pattern.compile("^.*\\.(pdf|jpg|png)$");

        assertTrue(pattern.matcher(validAnhangPdf).matches());
        assertTrue(pattern.matcher(validAnhangJpg).matches());
        assertFalse(pattern.matcher(invalidAnhang).matches());
    }

    @Test
    public void testLeeresTextfeld() {
        Beschwerde Beschwerde = new Beschwerde(new Date(), "OPEN", "Infrastruktur", "Hoch", "", true, "application/pdf");
        assertFalse(BeschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }
}

*/
