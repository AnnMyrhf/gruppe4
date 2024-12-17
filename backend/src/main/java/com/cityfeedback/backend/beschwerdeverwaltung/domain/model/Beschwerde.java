package com.cityfeedback.backend.beschwerdeverwaltung.domain.model;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beschwerde {

    @Id
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Systemseitig generierte Attribute einer Beschwerde
    private Long id;
    private Date erstellDatum;
    private String status;
    private String prioritaet;

    // Attribute, die der Buerger uebergibt
    @NotBlank(message = "Bitte wählen Sie eine Kategorie aus!")//  darf nicht null oder leer sein
    private String beschwerdeTyp;

    @NotBlank(message = "Der Titel darf nicht leer sein!")//  darf nicht null oder leer sein
    @Size(max = 100, message = "Der Titel darf nur 100 Zeichen enthalten.")
    private String titel;

    @Column(length = 2000)
    @NotBlank(message = "Das Texfeld darf nicht leer sein!")//  darf nicht null oder leer sein
    private String textfeld;

    @Embedded
    private Anhang anhang;


    @ManyToOne
    @JoinColumn(name = "buerger_id")
    @JsonBackReference // Verhindert Endlosschleifen, da diese Seite der Beziehung nicht in JSON aufgenommen wird
    private Buerger buerger;

    //private List<DomainEvent> domainEvents = new ArrayList<>();

/*public void erstelleBeschwerde(Date erstellDatum, String status, String prioritaet, String beschwerdeTyp, String titel, String textfeld, Anhang anhang) {
        BeschwerdeErstellen event = new BeschwerdeErstellen(erstellDatum, status, prioritaet, beschwerdeTyp, titel, textfeld, anhang);
        domainEvents.add(event);
    }*/
}
