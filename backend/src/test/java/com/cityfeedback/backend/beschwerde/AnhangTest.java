package com.cityfeedback.backend.beschwerde;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnhangTest {

    @Test
    void testAnhangConstructor() {
        Anhang anhang = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertThat(anhang.getDateiName()).isEqualTo("datei.pdf");
        assertThat(anhang.getDatenTyp()).isEqualTo("application/pdf");
        assertThat(anhang.getDateiGroesse()).isEqualTo(12345L);
        assertThat(anhang.getDaten()).isNull();
    }

    @Test
    void testDefaultConstructor() {
        Anhang anhang = new Anhang();

        assertThat(anhang.getDateiName()).isNull();
        assertThat(anhang.getDatenTyp()).isNull();
        assertThat(anhang.getDateiGroesse()).isNull();
        assertThat(anhang.getDaten()).isNull();
    }

    @Test
    void testSetDateiName() {
        Anhang anhang = new Anhang();
        anhang.setDateiName("datei.pdf");

        assertThat(anhang.getDateiName()).isEqualTo("datei.pdf");
    }

    @Test
    void testSetDatenTyp() {
        Anhang anhang = new Anhang();
        anhang.setDatenTyp("application/pdf");

        assertThat(anhang.getDatenTyp()).isEqualTo("application/pdf");
    }

    @Test
    void testSetDateiGroesse() {
        Anhang anhang = new Anhang();
        anhang.setDateiGroesse(12345L);

        assertThat(anhang.getDateiGroesse()).isEqualTo(12345L);
    }

    @Test
    void testEqualsSameValues() {
        Anhang anhang1 = new Anhang("datei.pdf", "application/pdf", 12345L, null);
        Anhang anhang2 = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertEquals(anhang1, anhang2); // Gleiche Werte
    }

    @Test
    void testEqualsDifferentValues() {
        Anhang anhang1 = new Anhang("datei1.pdf", "application/pdf", 12345L, null);
        Anhang anhang2 = new Anhang("datei2.pdf", "application/pdf", 12345L, null);

        assertNotEquals(anhang1, anhang2); // Verschiedene Werte
    }

    @Test
    void testEqualsWithNull() {
        Anhang anhang = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertNotEquals(null, anhang); // Vergleich mit null
    }

    @Test
    void testEqualsWithDifferentType() {
        Anhang anhang = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertNotEquals("string", anhang); // Vergleich mit anderem Typ
    }

    @Test
    void testHashCodeSameValues() {
        Anhang anhang1 = new Anhang("datei.pdf", "application/pdf", 12345L, null);
        Anhang anhang2 = new Anhang("datei.pdf", "application/pdf", 12345L, null);

        assertEquals(anhang1.hashCode(), anhang2.hashCode()); // Gleicher Hash-Code
    }

    @Test
    void testHashCodeDifferentValues() {
        Anhang anhang1 = new Anhang("datei1.pdf", "application/pdf", 12345L, null);
        Anhang anhang2 = new Anhang("datei2.pdf", "application/pdf", 12345L, null);

        assertNotEquals(anhang1.hashCode(), anhang2.hashCode()); // Unterschiedlicher Hash-Code
    }

    @Test
    void testToString() {
        Anhang anhang = new Anhang("datei.pdf", "application/pdf", 12345L, null);
        String toStringResult = anhang.toString();

        assertThat(toStringResult)
                .contains("dateiName=datei.pdf", "datenTyp=application/pdf", "dateiGroesse=12345", "daten=null");

    }
}
