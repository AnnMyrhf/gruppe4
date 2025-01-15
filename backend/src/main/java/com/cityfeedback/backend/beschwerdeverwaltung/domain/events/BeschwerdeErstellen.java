package com.cityfeedback.backend.beschwerdeverwaltung.domain.events;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.domain.DomainEvents;

import java.sql.Timestamp;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeschwerdeErstellen implements DomainEvent {

    @Id // Markiert id als Primaerschluessel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
    private Long id;
    private Timestamp timestamp;
    private String titel;
    private Status status;
    private String email;

    public BeschwerdeErstellen(Beschwerde beschwerde) {
        this.id = beschwerde.getId();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.titel = beschwerde.getTitel();
        this.status = Status.EINGEGANGEN;
        this.email = beschwerde.getBuerger().getEmail();
    }
}

