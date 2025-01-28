package com.cityfeedback.backend.beschwerde;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnhangTest {

    /**
     * Testet den Konstruktor von Anhang mit Parametern.
     * Überprüft, ob die übergebenen Werte korrekt gesetzt werden.
     */
    @Test
    void testAnhangConstructor() {
        Anhang anhang = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertThat(anhang.getDateiName()).isEqualTo("datei.pdf");
        assertThat(anhang.getDatenTyp()).isEqualTo("application/pdf");
        assertThat(anhang.getDateiGroesse()).isEqualTo(12345L);
        assertThat(anhang.getDaten()).isNull();
    }

    /**
     * Testet den Standard-Konstruktor von Anhang.
     * Überprüft, ob alle Felder standardmäßig auf null gesetzt sind.
     */
    @Test
    void testDefaultConstructor() {
        Anhang anhang = new Anhang();

        assertThat(anhang.getDateiName()).isNull();
        assertThat(anhang.getDatenTyp()).isNull();
        assertThat(anhang.getDateiGroesse()).isNull();
        assertThat(anhang.getDaten()).isNull();
    }

    /**
     * Testet die setDateiName-Methode.
     * Überprüft, ob der Dateiname korrekt gesetzt wird.
     */
    @Test
    void testSetDateiName() {
        Anhang anhang = new Anhang();
        anhang.setDateiName("datei.pdf");

        assertThat(anhang.getDateiName()).isEqualTo("datei.pdf");
    }

    /**
     * Testet die setDatenTyp-Methode.
     * Überprüft, ob der Datentyp korrekt gesetzt wird.
     */
    @Test
    void testSetDatenTyp() {
        Anhang anhang = new Anhang();
        anhang.setDatenTyp("application/pdf");

        assertThat(anhang.getDatenTyp()).isEqualTo("application/pdf");
    }

    /**
     * Überprüft, ob die Dateigröße korrekt gesetzt wird.
     */
    @Test
    void testSetDateiGroesse() {
        Anhang anhang = new Anhang();
        anhang.setDateiGroesse(12345L);

        assertThat(anhang.getDateiGroesse()).isEqualTo(12345L);
    }

    /**
     * Überprüft, ob zwei Anhang-Objekte mit denselben Werten als gleich betrachtet werden.
     */
    @Test
    void testEqualsSameValues() {
        Anhang anhang1 = new Anhang("datei.pdf", "application/pdf", 12345L, null);
        Anhang anhang2 = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertEquals(anhang1, anhang2); // Gleiche Werte
    }

    /**
     * Überprüft, ob zwei Anhang-Objekte mit unterschiedlichen Werten als ungleich betrachtet werden.
     */
    @Test
    void testEqualsDifferentValues() {
        Anhang anhang1 = new Anhang("datei1.pdf", "application/pdf", 12345L, null);
        Anhang anhang2 = new Anhang("datei2.pdf", "application/pdf", 12345L, null);

        assertNotEquals(anhang1, anhang2); // Verschiedene Werte
    }

    /**
     * Überprüft, ob ein Anhang-Objekt nicht gleich null ist.
     */
    @Test
    void testEqualsWithNull() {
        Anhang anhang = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertNotEquals(null, anhang); // Vergleich mit null
    }

    /**
     * Überprüft, ob ein Anhang-Objekt nicht gleich einem Objekt eines anderen Typs ist.
     */
    @Test
    void testEqualsWithDifferentType() {
        Anhang anhang = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertNotEquals("string", anhang); // Vergleich mit anderem Typ
    }

    /**
     * Überprüft, ob zwei Anhang-Objekte mit denselben Werten denselben Hash-Code haben.
     */
    @Test
    void testHashCodeSameValues() {
        Anhang anhang1 = new Anhang("datei.pdf", "application/pdf", 12345L, null);
        Anhang anhang2 = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertEquals(anhang1.hashCode(), anhang2.hashCode()); // Gleicher Hash-Code
    }

    /**
     * Überprüft, ob zwei Anhang-Objekte mit unterschiedlichen Werten unterschiedliche Hash-Codes haben.
     */
    @Test
    void testHashCodeDifferentValues() {
        Anhang anhang1 = new Anhang("datei1.pdf", "application/pdf", 12345L, null);
        Anhang anhang2 = new Anhang("datei2.pdf", "application/pdf", 12345L, null);

        assertNotEquals(anhang1.hashCode(), anhang2.hashCode()); // Unterschiedlicher Hash-Code
    }

    /**
     * Überprüft, ob die toString-Ausgabe die erwarteten Werte enthält.
     */
    @Test
    void testToString() {
        Anhang anhang = new Anhang("datei.pdf", "application/pdf", 12345L, null);
        String toStringResult = anhang.toString();

        assertThat(toStringResult)
                .contains("dateiName=datei.pdf", "datenTyp=application/pdf", "dateiGroesse=12345", "daten=null");
    }
}
