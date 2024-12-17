package com.cityfeedback.backend;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BeschwerdeTest {

    @Test
    void testLombokGetterSetter() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Testtitel");
        beschwerde.setBeschwerdeTyp("Infrastruktur");
        beschwerde.setTextfeld("Das ist ein Beispieltext für das Textfeld.");
        beschwerde.setPrioritaet("Hoch");

        assertThat(beschwerde.getTitel()).isEqualTo("Testtitel");
        assertThat(beschwerde.getBeschwerdeTyp()).isEqualTo("Infrastruktur");
        assertThat(beschwerde.getTextfeld()).isEqualTo("Das ist ein Beispieltext für das Textfeld.");
        assertThat(beschwerde.getPrioritaet()).isEqualTo("Hoch");
    }

    @Test
    void testLombokToString() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Testtitel");
        beschwerde.setBeschwerdeTyp("Infrastruktur");
        beschwerde.setTextfeld("Das ist ein Beispieltext für das Textfeld.");

        String toStringResult = beschwerde.toString();

        assertThat(toStringResult).contains("titel=Testtitel");
        assertThat(toStringResult).contains("beschwerdeTyp=Infrastruktur");
        assertThat(toStringResult).contains("textfeld=Das ist ein Beispieltext für das Textfeld.");
    }
}

    class BeschwerdeValidationTest {

        private Validator validator;

        @BeforeEach
        void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @Test
        void testValidBeschwerde() {
            Beschwerde beschwerde = new Beschwerde();
            beschwerde.setTitel("Gültiger Titel");
            beschwerde.setBeschwerdeTyp("Technik");
            beschwerde.setTextfeld("Dies ist ein gültiger Text für die Beschwerde.");

            Set<ConstraintViolation<Beschwerde>> violations = validator.validate(beschwerde);

            assertThat(violations).isEmpty(); // Keine Validierungsfehler
        }

        @Test
        void testInvalidBeschwerde_NullOrEmptyBeschwerdeTyp() {
            Beschwerde beschwerde = new Beschwerde();
            beschwerde.setTitel("Gültiger Titel");
            beschwerde.setTextfeld("Gültiger Text");

            // Fehlender BeschwerdeTyp
            Set<ConstraintViolation<Beschwerde>> violations = validator.validate(beschwerde);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("Bitte wählen Sie eine Kategorie aus!");
        }

        @Test
        void testInvalidBeschwerde_TitelTooLong() {
            Beschwerde beschwerde = new Beschwerde();
            beschwerde.setTitel("A".repeat(101)); // 101 Zeichen, über der Grenze
            beschwerde.setBeschwerdeTyp("Technik");
            beschwerde.setTextfeld("Gültiger Text");

            Set<ConstraintViolation<Beschwerde>> violations = validator.validate(beschwerde);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("Der Titel darf nur 100 Zeichen enthalten.");
        }

        @Test
        void testInvalidBeschwerde_EmptyTextfeld() {
            Beschwerde beschwerde = new Beschwerde();
            beschwerde.setTitel("Gültiger Titel");
            beschwerde.setBeschwerdeTyp("Technik");
            beschwerde.setTextfeld(""); // Leerer Text

            Set<ConstraintViolation<Beschwerde>> violations = validator.validate(beschwerde);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("Das Texfeld darf nicht leer sein!");
        }
}



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
