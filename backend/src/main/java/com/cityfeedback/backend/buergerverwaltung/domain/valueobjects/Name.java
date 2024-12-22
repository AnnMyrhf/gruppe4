package com.cityfeedback.backend.buergerverwaltung.domain.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor// automatisch Getter, Setter, toString() usw
@AllArgsConstructor
public class Name {

    @NotBlank(message = "Vorname darf nicht leer sein!")
    @Size(max = 30, message = "Vorname darf max. 30 Zeichen lang sein!")
    String vorname;

    @NotBlank(message = "Nachname darf nicht leer sein!")
    @Size(max = 30, message = "Nachname darf max. 30 Zeichen lang sein!")
    String nachname;
}
