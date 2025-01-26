package com.cityfeedback.backend.mitarbeiterverwaltung.domain.events;

import com.cityfeedback.backend.mitarbeiterverwaltung.domain.model.Mitarbeiter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MitarbeiterRegistrieren implements DomainEvent {

    @Id // Markiert id als Primaerschluessel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
    private Long id;
    private Timestamp timestamp;
    private String vorname;
    private String nachname;
    private String email;

    public MitarbeiterRegistrieren(Mitarbeiter mitarbeiter) {
        this.id = mitarbeiter.getId();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.vorname = mitarbeiter.getVorname();
        this.nachname = mitarbeiter.getNachname();
        this.email = mitarbeiter.getEmail();
    }
}

