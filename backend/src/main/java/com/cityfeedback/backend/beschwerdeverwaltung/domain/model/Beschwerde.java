package com.cityfeedback.backend.beschwerdeverwaltung.domain.model;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Prioritaet;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
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
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Prioritaet prioritaet;

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

    public Beschwerde(String titel, String beschwerdeTyp, String textfeld, Anhang anhang, Buerger buerger){
        this.titel = titel;
        this.beschwerdeTyp = beschwerdeTyp;
        this.textfeld = textfeld;
        this.anhang = anhang != null ? anhang : null;
        this.erstellDatum = new Date();
        this.status = Status.EINGEGANGEN;
        this.prioritaet = randomEnum(Prioritaet.class);
        this.buerger = buerger;
    }

    private <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        return values[(int) (Math.random() * values.length)];
    }
}
