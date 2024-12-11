package com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@Embeddable// automatisch Getter, Setter, toString() usw
@AllArgsConstructor
@NoArgsConstructor
public class Anhang {
         String dateiName;
         String datenTyp;
         Long dateiGroesse;
         String dateiEinheit;
}
