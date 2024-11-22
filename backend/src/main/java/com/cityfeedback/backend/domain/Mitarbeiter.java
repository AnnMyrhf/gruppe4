package com.cityfeedback.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Repraesentiert einen Buerger in der CityFeedback-Anwendung
 */
@Entity // JPA-Entitaet fuer DB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mitarbeiter {

    @Id // Markiert id als Primaerschluessel
    @SequenceGenerator(
            name = "mitarbeiter_id",
            sequenceName = "mitarbeiter_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mitarbeiter_id"
    )
    private Long id;

    private String anrede;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String email;
    private String passwort;
    private String abteilung;
    private String position;

    public Mitarbeiter(String anrede, String vorname, String nachname, String telefonnummer, String email, String passwort, String abteilung, String position) {
        this.position = position;
        this.abteilung = abteilung;
        this.passwort = passwort;
        this.email = email;
        this.telefonnummer = telefonnummer;
        this.nachname = nachname;
        this.vorname = vorname;
        this.anrede = anrede;
    }
}
