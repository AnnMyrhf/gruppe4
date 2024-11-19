package com.cityfeedback.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
    private Integer id;
    private String anrede;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String email;
    private String passwort;
    private String abteilung;
    private String position;

}
