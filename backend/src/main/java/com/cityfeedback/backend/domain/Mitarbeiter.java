package com.cityfeedback.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Repraesentiert einen Buerger in der CityFeedback-Anwendung
 */
@Entity // JPA-Entitaet fuer DB
public class Mitarbeiter {

    @Id // Markiert id als Primaerschluessel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
    private String id;
    private String anrede;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String email;
    private String passwort;
    private String abteilung;
    private String position;

    // Manuell definierter Konstruktor
    public Mitarbeiter(String id, String anrede, String vorname, String nachname, String telefonnummer,
                       String email, String passwort, String abteilung, String position) {
        this.id = id;
        this.anrede = anrede;
        this.vorname = vorname;
        this.nachname = nachname;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.passwort = passwort;
        this.abteilung = abteilung;
        this.position = position;
    }

    // Getter-Methoden für alle Felder
    public String getId() {
        return id;
    }

    public String getAnrede() {
        return anrede;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswort() {
        return passwort;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public String getPosition() {
        return position;
    }

    // Optional: Setter-Methoden für Felder, die veränderbar sein sollen
    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
