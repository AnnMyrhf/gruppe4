package com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects;

import jakarta.persistence.Embeddable;
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
         String dateiEinheit;
}
