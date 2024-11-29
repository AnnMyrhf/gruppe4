package com.cityfeedback.backend;

import com.cityfeedback.backend.repositories.BeschwerdeRepository;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import java.util.regex.Pattern;
import com.cityfeedback.backend.services.BeschwerdeService;
import com.cityfeedback.backend.domain.Beschwerde;
import org.springframework.beans.factory.annotation.Autowired;

public class BeschwerdeValidierungTest {
    @Autowired
    private BeschwerdeRepository beschwerdeRepository;
    BeschwerdeService beschwerdeService = new BeschwerdeService(beschwerdeRepository);

    @Test
    public void testGueltigeBeschwerdeDaten() {
        Beschwerde beschwerde = new Beschwerde(new Date(), "OPEN", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf");
        assertTrue(beschwerdeService.isBeschwerdeDatenGueltig(beschwerde));
    }

    @Test
    public void testAnhangOptional() {
        // Erstellen einer Beschwerde ohne Anhang
        assertDoesNotThrow(() -> new Beschwerde(new Date(), "OPEN", "Infrastruktur", "hoch", "Beschwerdetext", false, "pdf"));
    }

    @Test
    public void testFehlendesStatusFeld() {
        Beschwerde beschwerde = new Beschwerde(new Date(), "", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf");
        assertFalse(beschwerdeService.isBeschwerdeDatenGueltig(beschwerde));
    }

    @Test
    public void testUnbekanntesStatusFormat() {
        Beschwerde beschwerde = new Beschwerde(new Date(), "UNBEKANNT", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf");
        assertFalse(beschwerdeService.isBeschwerdeDatenGueltig(beschwerde));
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
        assertFalse(beschwerdeService.isBeschwerdeDatenGueltig(Beschwerde));
    }
}
