package com.cityfeedback.backend.beschwerdeverwaltung.domain.model;

import com.cityfeedback.backend.benachrichtigungsverwaltung.model.Benachrichtigung;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Column(nullable = false)
    private Status status;

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

    @OneToMany(mappedBy = "beschwerde", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Benachrichtigung> benachrichtigungen = new ArrayList<>();

    public enum Status {
        EINGEGANGEN,
        IN_BEARBEITUNG,
        ERLEDIGT
    }

    // Methode zur Statusänderung mit Benachrichtigung
    public void setStatus(Status neuerStatus) {
        if (this.status != neuerStatus) {
            this.status = neuerStatus;
            Benachrichtigung benachrichtigung = new Benachrichtigung();
            benachrichtigung.setDatum(LocalDate.now());
            benachrichtigung.setZeit(LocalTime.now());
            benachrichtigung.setKommentar("Status geändert zu: " + neuerStatus);
            benachrichtigung.setBeschwerde(this);
            this.benachrichtigungen.add(benachrichtigung);
        }
    }

}
