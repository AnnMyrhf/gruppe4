package com.cityfeedback.backend.buergerverwaltung.domain.events;

import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.buergerverwaltung.domain.valueobjects.Name;
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
    private Name name;
    private String email;

    public BuergerRegistrieren(Buerger buerger) {
        this.id = buerger.getId();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.name = buerger.getName();
        this.email = buerger.getEmail();
    }
}