package com.cityfeedback.backend.beschwerdeverwaltung.domain.model;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeAktualisieren;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeErstellen;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.DomainEvent;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Prioritaet;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import com.cityfeedback.backend.buergerverwaltung.domain.events.BuergerRegistrieren;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.domain.DomainEvents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status.*;

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

    @Column(length = 1000)
    private String kommentar; // Optional: für einen einzelnen Kommentar

    public Beschwerde(String titel, String beschwerdeTyp, String textfeld, Anhang anhang, Buerger buerger) {
        this.titel = titel;
        this.beschwerdeTyp = beschwerdeTyp;
        this.textfeld = textfeld;
        this.anhang = anhang;
        this.erstellDatum = new Date();
        this.prioritaet = randomEnum(Prioritaet.class);
        this.buerger = buerger;
        this.kommentar = "";
        this.status = Status.EINGEGANGEN;
    }

    @DomainEvents
    public List<DomainEvent> getDomainEvents() {
        List<DomainEvent> events = new ArrayList<>();

        if (status == EINGEGANGEN) {
            events.add(new BeschwerdeErstellen(this));
        } else events.add(new BeschwerdeAktualisieren(this));

        return events;
    }

    private <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        return values[(int) (Math.random() * values.length)];
    }
}

