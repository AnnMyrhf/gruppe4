package com.cityfeedback.backend.beschwerde;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Prioritaet;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeschwerdeTest {

    @Test
    void testLombokGetterSetter() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Testtitel");
        beschwerde.setBeschwerdeTyp("Infrastruktur");
        beschwerde.setTextfeld("Das ist ein Beispieltext für das Textfeld.");

        assertThat(beschwerde.getTitel()).isEqualTo("Testtitel");
        assertThat(beschwerde.getBeschwerdeTyp()).isEqualTo("Infrastruktur");
        assertThat(beschwerde.getTextfeld()).isEqualTo("Das ist ein Beispieltext für das Textfeld.");
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
    void testEquals_SameObject() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Titel");
        beschwerde.setTextfeld("Textfeld");

        assertEquals(beschwerde, beschwerde); // Das gleiche Objekt
    }

    @Test
    void testEquals_DifferentObjectsSameValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel");
        beschwerde1.setTextfeld("Textfeld");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel");
        beschwerde2.setTextfeld("Textfeld");

        assertEquals(beschwerde1, beschwerde2); // Verschiedene Objekte, gleiche Werte
    }

    @Test
    void testEquals_DifferentValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel 1");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel 2");

        assertNotEquals(beschwerde1, beschwerde2); // Verschiedene Werte
    }

    @Test
    void testEquals_Null() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Titel");

        assertNotEquals(beschwerde, null); // Vergleich mit null
    }

    @Test
    void testEquals_DifferentType() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Titel");

        assertNotEquals(beschwerde, "String"); // Vergleich mit anderem Typ
    }

    @Test
    void testHashCode_SameValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel");
        beschwerde1.setTextfeld("Textfeld");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel");
        beschwerde2.setTextfeld("Textfeld");

        assertEquals(beschwerde1.hashCode(), beschwerde2.hashCode()); // Gleiche Werte, gleicher Hash-Code
    }

    @Test
    void testHashCode_DifferentValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel 1");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel 2");

        assertNotEquals(beschwerde1.hashCode(), beschwerde2.hashCode()); // Unterschiedliche Werte, unterschiedlicher Hash-Code
    }

    @Test
    void testConstructorWithParameters() {
        Date date = new Date();
        Beschwerde beschwerde = new Beschwerde(1L, date, Status.EINGEGANGEN, Prioritaet.HOCH, "Titel", "Typ", "Text", null, null);

        assertThat(beschwerde.getId()).isEqualTo(1L);
        assertThat(beschwerde.getErstellDatum()).isEqualTo(date);
        assertThat(beschwerde.getStatus()).isEqualTo(Status.EINGEGANGEN);
        assertThat(beschwerde.getPrioritaet()).isEqualTo(Prioritaet.HOCH);
        assertThat(beschwerde.getTitel()).isEqualTo("Titel");
        assertThat(beschwerde.getTextfeld()).isEqualTo("Text");
        assertThat(beschwerde.getBeschwerdeTyp()).isEqualTo("Typ");
        assertThat(beschwerde.getAnhang()).isNull();
        assertThat(beschwerde.getBuerger()).isNull();
    }

    @Test
    void testConstructorWithInvalidParameters() {
        // Test mit null-Werten und prüfen, ob Fehler auftreten
        Date date = new Date();
        Beschwerde beschwerde = new Beschwerde(null, null, null, null, null, null, null, null, null);

        assertThat(beschwerde.getId()).isNull();
        assertThat(beschwerde.getErstellDatum()).isNull();
        assertThat(beschwerde.getStatus()).isNull();
        assertThat(beschwerde.getPrioritaet()).isNull();
        assertThat(beschwerde.getTitel()).isNull();
        assertThat(beschwerde.getTextfeld()).isNull();
        assertThat(beschwerde.getBeschwerdeTyp()).isNull();
        assertThat(beschwerde.getAnhang()).isNull();
        assertThat(beschwerde.getBuerger()).isNull();
    }

    @Test
    void testSetErstellDatum() {
        Beschwerde beschwerde = new Beschwerde();
        Date date = new Date();
        beschwerde.setErstellDatum(date);

        assertThat(beschwerde.getErstellDatum()).isEqualTo(date);
    }

    @Test
    void testSetStatus() {
        Beschwerde beschwerde = new Beschwerde();
        Status status = Status.EINGEGANGEN;
        beschwerde.setStatus(status);

        assertThat(beschwerde.getStatus()).isEqualTo(status);
    }

    @Test
    void testSetPrioritaet() {
        Beschwerde beschwerde = new Beschwerde();
        Prioritaet prioritaet = Prioritaet.HOCH;
        beschwerde.setPrioritaet(prioritaet);

        assertThat(beschwerde.getPrioritaet()).isEqualTo(prioritaet);
    }

    @Test
    void testSetAnhang() {
        Beschwerde beschwerde = new Beschwerde();
        Anhang anhang = new Anhang();
        beschwerde.setAnhang(anhang);

        assertThat(beschwerde.getAnhang()).isEqualTo(anhang);
    }

    @Test
    void testSetBuerger() {
        Beschwerde beschwerde = new Beschwerde();
        Buerger buerger = new Buerger();
        beschwerde.setBuerger(buerger);

        assertThat(beschwerde.getBuerger()).isEqualTo(buerger);
    }

    @Test
    void testSetId() {
        Beschwerde beschwerde = new Beschwerde();
        Long id = 123L;
        beschwerde.setId(id);

        assertThat(beschwerde.getId()).isEqualTo(id);
    }

    @Test
    void testSetTitel() {
        Beschwerde beschwerde = new Beschwerde();
        String titel = "Testtitel";
        beschwerde.setTitel(titel);

        assertThat(beschwerde.getTitel()).isEqualTo(titel);
    }

    @Test
    void testSetBeschwerdeTyp() {
        Beschwerde beschwerde = new Beschwerde();
        String typ = "Technik";
        beschwerde.setBeschwerdeTyp(typ);

        assertThat(beschwerde.getBeschwerdeTyp()).isEqualTo(typ);
    }

    @Test
    void testSetTextfeld() {
        Beschwerde beschwerde = new Beschwerde();
        String textfeld = "Dies ist ein Beispieltext.";
        beschwerde.setTextfeld(textfeld);

        assertThat(beschwerde.getTextfeld()).isEqualTo(textfeld);
    }

}

