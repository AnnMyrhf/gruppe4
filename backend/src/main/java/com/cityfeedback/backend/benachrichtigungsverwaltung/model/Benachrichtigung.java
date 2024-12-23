package com.cityfeedback.backend.benachrichtigungsverwaltung.model;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;

import com.cityfeedback.backend.buergerverwaltung.domain.valueobjects.Name;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
