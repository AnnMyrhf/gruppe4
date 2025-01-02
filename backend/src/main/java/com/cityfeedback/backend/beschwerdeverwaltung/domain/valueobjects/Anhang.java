package com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor// automatisch Getter, Setter, toString() usw
@AllArgsConstructor
public class Anhang {
        String dateiName;
        String datenTyp;
        Long dateiGroesse;

        @Lob
        private byte[] daten;
}
