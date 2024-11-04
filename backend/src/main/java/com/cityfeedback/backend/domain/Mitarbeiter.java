package com.cityfeedback.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Repraesentiert einen Buerger in der CityFeedback-Anwendung
 */
@Entity // JPA-Entitaet fuer DB
@Data // automatisch Getter, Setter, toString() usw
@NoArgsConstructor // leerer Konstruktor fuer JPA benoetigt
@AllArgsConstructor

public class Mitarbeiter {

    @Id // Markiert id als Primaerschluessel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
    private String id;
    private String anrede; // haben wir nicht im Domänenmodell, aber wäre sinnvoll?
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String email;
    private String passwort;
    private String abteilung;
    private String position;
}
