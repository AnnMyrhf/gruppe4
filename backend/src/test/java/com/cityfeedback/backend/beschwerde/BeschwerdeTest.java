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

    /**
     * Testet die Getter- und Setter-Methoden der Klasse Beschwerde.
     */
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

    /**
     * Überprüft, ob die toString-Methode korrekt die Felder der Beschwerde enthält.
     */
    @Test
    void testLombokToString() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Testtitel");
        beschwerde.setBeschwerdeTyp("Infrastruktur");
        beschwerde.setTextfeld("Das ist ein Beispieltext für das Textfeld.");

        String toStringResult = beschwerde.toString();

        assertThat(toStringResult)
                .contains("titel=Testtitel", "beschwerdeTyp=Infrastruktur", "textfeld=Das ist ein Beispieltext für das Textfeld.");

    }

        private Validator validator;

    /**
     * Initialisiert den Validator für die Validierung der Beschwerde-Objekte.
     */
        @BeforeEach
        void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

    /**
     * Überprüft, ob eine gültige Beschwerde keine Validierungsfehler auslöst.
     */
        @Test
        void testValidBeschwerde() {
            Beschwerde beschwerde = new Beschwerde();
            beschwerde.setTitel("Gültiger Titel");
            beschwerde.setBeschwerdeTyp("Technik");
            beschwerde.setTextfeld("Dies ist ein gültiger Text für die Beschwerde.");

            Set<ConstraintViolation<Beschwerde>> violations = validator.validate(beschwerde);

            assertThat(violations).isEmpty(); // Keine Validierungsfehler
        }

    /**
     * Testet eine ungültige Beschwerde mit fehlendem oder leerem BeschwerdeTyp.
     */
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

    /**
     * Überprüft, ob die Validierung fehlschlägt, wenn der Titel zu lang ist.
     */
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

    /**
     * Testet die Validierung, wenn das Textfeld leer ist.
     */
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

    /**
     * Testet die Validierung des Anhängformats (nur .pdf, .jpg, .png erlaubt).
     */
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

    /**
     * Überprüft, ob zwei gleiche Objekte als gleich erkannt werden.
     */
    @Test
    void testEquals_SameObject() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Titel");
        beschwerde.setTextfeld("Textfeld");

        assertEquals(beschwerde, beschwerde); // Das gleiche Objekt
    }

    /**
     * Testet die Gleichheit von zwei Objekten mit denselben Werten.
     */
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

    /**
     * Überprüft, ob zwei Objekte mit unterschiedlichen Werten als ungleich erkannt werden.
     */
    @Test
    void testEquals_DifferentValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel 1");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel 2");

        assertNotEquals(beschwerde1, beschwerde2); // Verschiedene Werte
    }

    /**
     * Überprüft, ob ein Objekt ungleich null ist.
     */
    @Test
    void testEquals_Null() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Titel");

        assertNotEquals(null, beschwerde); // Vergleich mit null
    }

    /**
     * Testet die Ungleichheit von Objekten unterschiedlichen Typs.
     */
    @Test
    void testEquals_DifferentType() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Titel");

        assertNotEquals("String", beschwerde); // Vergleich mit anderem Typ
    }

    /**
     * Überprüft, ob zwei Objekte mit gleichen Werten denselben Hash-Code haben.
     */
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

    /**
     * Testet, ob Objekte mit unterschiedlichen Werten unterschiedliche Hash-Codes haben.
     */
    @Test
    void testHashCode_DifferentValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel 1");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel 2");

        assertNotEquals(beschwerde1.hashCode(), beschwerde2.hashCode()); // Unterschiedliche Werte, unterschiedlicher Hash-Code
    }

    /**
     * Testet den Konstruktor mit Parametern und überprüft die Initialisierung der Felder.
     */

    @Test
    void testConstructorWithParameters() {
        Beschwerde beschwerde = new Beschwerde("Titel", "Typ", "Text", null, null);

        assertThat(beschwerde.getErstellDatum()).isNotNull();
        assertThat(beschwerde.getStatus()).isNotNull();
        assertThat(beschwerde.getPrioritaet()).isNotNull();
        assertThat(beschwerde.getTitel()).isEqualTo("Titel");
        assertThat(beschwerde.getTextfeld()).isEqualTo("Text");
        assertThat(beschwerde.getBeschwerdeTyp()).isEqualTo("Typ");
        assertThat(beschwerde.getAnhang()).isNull();
        assertThat(beschwerde.getBuerger()).isNull();
    }

    /**
     * Testet den Konstruktor mit ungültigen (null) Parametern.
     */
    @Test
    void testConstructorWithInvalidParameters() {
        // Test mit null-Werten und prüfen, ob Fehler auftreten
        Beschwerde beschwerde = new Beschwerde(null, null, null, null, null, null, null, null, null, null);

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

    /**
     * Überprüft das Setzen und Auslesen des Erstellungsdatums.
     */
    @Test
    void testSetErstellDatum() {
        Beschwerde beschwerde = new Beschwerde();
        Date date = new Date();
        beschwerde.setErstellDatum(date);

        assertThat(beschwerde.getErstellDatum()).isEqualTo(date);
    }

    /**
     * Testet das Setzen und Abrufen des Status einer Beschwerde.
     */
    @Test
    void testSetStatus() {
        Beschwerde beschwerde = new Beschwerde();
        Status status = Status.EINGEGANGEN;
        beschwerde.setStatus(status);

        assertThat(beschwerde.getStatus()).isEqualTo(status);
    }

    /**
     * Überprüft das Setzen und Abrufen der Priorität einer Beschwerde.
     */
    @Test
    void testSetPrioritaet() {
        Beschwerde beschwerde = new Beschwerde();
        Prioritaet prioritaet = Prioritaet.HOCH;
        beschwerde.setPrioritaet(prioritaet);

        assertThat(beschwerde.getPrioritaet()).isEqualTo(prioritaet);
    }

    /**
     * Testet das Setzen und Abrufen eines Anhangs.
     */
    @Test
    void testSetAnhang() {
        Beschwerde beschwerde = new Beschwerde();
        Anhang anhang = new Anhang();
        beschwerde.setAnhang(anhang);

        assertThat(beschwerde.getAnhang()).isEqualTo(anhang);
    }

    /**
     * Überprüft das Setzen und Abrufen eines Bürgers in der Beschwerde.
     */
    @Test
    void testSetBuerger() {
        Beschwerde beschwerde = new Beschwerde();
        Buerger buerger = new Buerger();
        beschwerde.setBuerger(buerger);

        assertThat(beschwerde.getBuerger()).isEqualTo(buerger);
    }

    /**
     * Testet das Setzen und Abrufen der ID der Beschwerde.
     */
    @Test
    void testSetId() {
        Beschwerde beschwerde = new Beschwerde();
        Long id = 123L;
        beschwerde.setId(id);

        assertThat(beschwerde.getId()).isEqualTo(id);
    }

    /**
     * Testet das Setzen und Abrufen des Titels der Beschwerde.
     */
    @Test
    void testSetTitel() {
        Beschwerde beschwerde = new Beschwerde();
        String titel = "Testtitel";
        beschwerde.setTitel(titel);

        assertThat(beschwerde.getTitel()).isEqualTo(titel);
    }

    /**
     * Überprüft das Setzen und Abrufen des Beschwerde-Typs.
     */
    @Test
    void testSetBeschwerdeTyp() {
        Beschwerde beschwerde = new Beschwerde();
        String typ = "Technik";
        beschwerde.setBeschwerdeTyp(typ);

        assertThat(beschwerde.getBeschwerdeTyp()).isEqualTo(typ);
    }

    /**
     * Testet das Setzen und Abrufen des Textfelds einer Beschwerde.
     */
    @Test
    void testSetTextfeld() {
        Beschwerde beschwerde = new Beschwerde();
        String textfeld = "Dies ist ein Beispieltext.";
        beschwerde.setTextfeld(textfeld);

        assertThat(beschwerde.getTextfeld()).isEqualTo(textfeld);
    }

    /**
     * Überprüft das Setzen und Abrufen eines Kommentars in der Beschwerde.
     */
    @Test
    void testSetKommentar() {
        // Arrange: Erstellen eines Testobjekts der Beschwerde
        Beschwerde beschwerde = new Beschwerde("Testtitel", "Allgemein", "Testtext", null, null);

        // Kommentar, das gesetzt werden soll
        String neuerKommentar = "Dies ist ein Testkommentar";

        // Act: Setze den Kommentar
        beschwerde.setKommentar(neuerKommentar);

        // Assert: Überprüfe, ob der Kommentar korrekt gesetzt wurde
        assertEquals(neuerKommentar, beschwerde.getKommentar(), "Der Kommentar sollte korrekt gesetzt werden.");
    }

    /**
     * Testet das Setzen eines Kommentars auf null.
     */
    @Test
    void testSetKommentarNull() {
        // Arrange: Erstelle ein Testobjekt der Beschwerde
        Beschwerde beschwerde = new Beschwerde("Testtitel", "Allgemein", "Testtext", null, null);

        // Act: Setze den Kommentar auf null
        beschwerde.setKommentar(null);

        // Assert: Überprüfe, ob der Kommentar korrekt auf null gesetzt wurde
        assertEquals(null, beschwerde.getKommentar(), "Der Kommentar sollte null sein.");
    }

    /**
     * Testet das Setzen eines leeren Strings als Kommentar.
     */

    @Test
    void testSetKommentarLeererString() {
        // Arrange: Erstelle ein Testobjekt der Beschwerde
        Beschwerde beschwerde = new Beschwerde("Testtitel", "Allgemein", "Testtext", null, null);

        // Act: Setze den Kommentar auf einen leeren String
        beschwerde.setKommentar("");

        // Assert: Überprüfe, ob der Kommentar korrekt auf einen leeren String gesetzt wurde
        assertEquals("", beschwerde.getKommentar(), "Der Kommentar sollte ein leerer String sein.");
    }

    /**
     * Testet die Konsistenz des Hash-Codes bei mehrfachen Aufrufen.
     */
    @Test
    void testHashCode_SameObject() {
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Titel");
        beschwerde.setTextfeld("Textfeld");

        // Teste, dass dasselbe Objekt denselben Hash-Code bei mehreren Aufrufen von hashCode() liefert
        int hashCode1 = beschwerde.hashCode();
        int hashCode2 = beschwerde.hashCode();

        assertEquals(hashCode1, hashCode2, "Der Hash-Code des gleichen Objekts sollte immer gleich sein.");
    }

    /**
     * Überprüft den Hash-Code für Objekte mit null-Werten.
     */
    @Test
    void testHashCode_NullValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel(null); // Titel auf null setzen
        beschwerde1.setTextfeld("Textfeld");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel(null); // Titel auf null setzen
        beschwerde2.setTextfeld("Textfeld");

        assertEquals(beschwerde1.hashCode(), beschwerde2.hashCode(), "Zwei Objekte mit null Titel sollten den gleichen Hash-Code haben.");
    }

    /**
     * Testet den Hash-Code für Objekte mit leeren Feldern.
     */
    @Test
    void testHashCode_EmptyValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel(""); // Leeren Titel setzen
        beschwerde1.setTextfeld("");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel(""); // Leeren Titel setzen
        beschwerde2.setTextfeld("");

        assertEquals(beschwerde1.hashCode(), beschwerde2.hashCode(), "Zwei Objekte mit leeren Feldern sollten den gleichen Hash-Code haben.");
    }

    /**
     * Überprüft, ob Objekte mit unterschiedlichen Feldern verschiedene Hash-Codes haben.
     */
    @Test
    void testHashCode_DifferentFields() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel");
        beschwerde1.setTextfeld("Textfeld");
        beschwerde1.setKommentar("Kommentar 1");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel");
        beschwerde2.setTextfeld("Textfeld");
        beschwerde2.setKommentar("Kommentar 2");

        // Wenn sich nur der Kommentar unterscheidet, sollten auch die Hash-Codes unterschiedlich sein
        assertNotEquals(beschwerde1.hashCode(), beschwerde2.hashCode(), "Die Hash-Codes sollten sich bei unterschiedlichem Kommentar unterscheiden.");
    }

    /**
     * Überprüft, ob der hashCode für ein neues Beschwerde-Objekt generiert werden kann.
     */
    @Test
    void testHashCode_NewObject() {
        Beschwerde beschwerde = new Beschwerde();

        int hashCode = beschwerde.hashCode();

        // Überprüft, ob der hashCode generiert wurde und im gültigen Bereich liegt
        assertNotEquals( "Der hashCode sollte nicht 0 sein.", hashCode != 0);
    }

    /**
     * Testet unterschiedliche Hash-Codes für Objekte mit komplett verschiedenen Feldern.
     */
    @Test
    void testHashCode_AllFieldsDifferent() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel 1");
        beschwerde1.setTextfeld("Textfeld 1");
        beschwerde1.setKommentar("Kommentar 1");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel 2");
        beschwerde2.setTextfeld("Textfeld 2");
        beschwerde2.setKommentar("Kommentar 2");

        assertNotEquals(beschwerde1.hashCode(), beschwerde2.hashCode(), "Die Hash-Codes sollten sich bei unterschiedlichen Werten unterscheiden.");
    }

    /**
     * Überprüft, ob Objekte mit identischen Werten in unterschiedlichen Instanzen denselben Hash-Code haben.
     */
    @Test
    void testHashCode_SameValuesMultipleInstances() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Titel");
        beschwerde1.setTextfeld("Textfeld");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Titel");
        beschwerde2.setTextfeld("Textfeld");

        // Beide Objekte sollten denselben Hash-Code haben, wenn ihre Werte identisch sind
        assertEquals(beschwerde1.hashCode(), beschwerde2.hashCode(), "Obwohl es zwei Instanzen sind, sollten ihre Hash-Codes gleich sein.");
    }

    /**
     * Testet unterschiedliche Hash-Codes für Objekte mit unterschiedlichen IDs.
     */
    @Test
    void testHashCode_DifferentIds() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setId(1L);
        beschwerde1.setTitel("Titel 1");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setId(2L);
        beschwerde2.setTitel("Titel 1");

        assertNotEquals(beschwerde1.hashCode(), beschwerde2.hashCode(), "Objekte mit unterschiedlichen IDs sollten unterschiedliche Hash-Codes haben.");
    }

    /**
     * Testet die Hash-Codes für Objekte mit zufälligen Werten.
     */
    @Test
    void testHashCode_RandomValues() {
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Zufallstitel");
        beschwerde1.setTextfeld("Zufallstext");
        beschwerde1.setKommentar("Zufallskommentar");

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Anderer Titel");
        beschwerde2.setTextfeld("Anderer Text");
        beschwerde2.setKommentar("Anderer Kommentar");

        assertNotEquals(beschwerde1.hashCode(), beschwerde2.hashCode(), "Objekte mit zufälligen Werten sollten unterschiedliche Hash-Codes haben.");
    }

}

