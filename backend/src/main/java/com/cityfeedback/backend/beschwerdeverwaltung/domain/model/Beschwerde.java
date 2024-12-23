package com.cityfeedback.backend.beschwerdeverwaltung.domain.model;

import com.cityfeedback.backend.benachrichtigungsverwaltung.model.Benachrichtigung;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeAktualisieren;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeErstellen;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Prioritaet;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.domain.DomainEvents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Prioritaet prioritaet;

    @NotBlank(message = "Der Titel darf nicht leer sein!")//  darf nicht null oder leer sein
    @Size(max = 100, message = "Der Titel darf nur 100 Zeichen enthalten.")
    private String titel;

    // Attribute, die der Buerger uebergibt
    @NotBlank(message = "Bitte wählen Sie eine Kategorie aus!")//  darf nicht null oder leer sein
    private String beschwerdeTyp;

    @Column(length = 2000)
    @NotBlank(message = "Das Texfeld darf nicht leer sein!")//  darf nicht null oder leer sein
    private String textfeld;

    @Embedded
    private Anhang anhang;


    @ManyToOne
    @JoinColumn(name = "buerger_id")
    @JsonBackReference // Verhindert Endlosschleifen, da diese Seite der Beziehung nicht in JSON aufgenommen wird
    private Buerger buerger;


    /*@OneToMany(mappedBy = "beschwerde", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    // Verhindert Endlosschleifen und stellt sicher, dass die Beschwerden in JSON zurückgegeben werden
    private List<Benachrichtigung> benachrichtigungen;*/

    public Beschwerde(String titel, String beschwerdeTyp, String textfeld, Anhang anhang, Buerger buerger){
        this.titel = titel;
        this.beschwerdeTyp = beschwerdeTyp;
        this.textfeld = textfeld;
        this.anhang = anhang != null ? anhang : null;
        this.erstellDatum = new Date();
        this.prioritaet = randomEnum(Prioritaet.class);
        this.buerger = buerger;
        //this.benachrichtigungen = new ArrayList<>(); // Initialisiere die Liste der Benachrichtigungen
    }

 /*  @DomainEvents
    public List<Object> domainEvents() {
        if (status == Status.EINGEGANGEN) {
            return List.of(new BeschwerdeErstellen(this));
        } else {
            return List.of(new BeschwerdeAktualisieren(this));
        }
    }*/


    private <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        return values[(int) (Math.random() * values.length)];
    }
}
