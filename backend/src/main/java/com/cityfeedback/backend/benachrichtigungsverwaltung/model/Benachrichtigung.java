package com.cityfeedback.backend.benachrichtigungsverwaltung.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Benachrichtigung {

    @Id
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    private Long id;

    private String kommentar;

  /*  @ManyToOne
    @JoinColumn(name = "beschwerde_id")
    @JsonBackReference // Verhindert Endlosschleifen, da diese Seite der Beziehung nicht in JSON aufgenommen wird
    private Beschwerde beschwerde;*/

    public Benachrichtigung(String kommentar){
        this.kommentar = kommentar;
    }
}
