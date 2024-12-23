package com.cityfeedback.backend.buergerverwaltung.domain.events;

import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
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
public class BuergerRegistrieren {

    @Id // Markiert id als Primaerschluessel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
    private Long id;
    private Timestamp timestamp;
    private String vorname;
    private String nachname;
    private String email;

    public BuergerRegistrieren(Buerger buerger) {
        this.id = buerger.getId();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.vorname = buerger.getVorname();
        this.nachname = buerger.getNachname();
        this.email = buerger.getEmail();
    }
}