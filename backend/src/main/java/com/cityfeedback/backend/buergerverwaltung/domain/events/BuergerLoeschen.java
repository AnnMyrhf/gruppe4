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

/**
 * Repraesentiert ein Domain-Event, das bei Loeschen eines Buergers ausgeloest wird.
 *
 * @author Ann-Kathrin Meyerhof
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuergerLoeschen implements DomainEvent {

    @Id // Markiert id als Primaerschluessel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
    private Long id;
    private Timestamp timestamp;
    private String vorname;
    private String nachname;
    private String email;

    public BuergerLoeschen(Buerger buerger) {
        this.id = buerger.getId();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.vorname = buerger.getVorname();
        this.nachname = buerger.getNachname();
        this.email = buerger.getEmail();
    }
}
