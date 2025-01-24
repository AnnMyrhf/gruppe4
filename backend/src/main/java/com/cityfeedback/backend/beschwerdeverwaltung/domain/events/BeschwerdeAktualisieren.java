package com.cityfeedback.backend.beschwerdeverwaltung.domain.events;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Prioritaet;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
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
public class BeschwerdeAktualisieren implements DomainEvent{


        @Id // Markiert id als Primaerschluessel
        @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
        private Long id;
        private Timestamp timestamp;
        private String titel;
        private Status status;
        private String kommentar;

        public BeschwerdeAktualisieren(Beschwerde beschwerde) {
            this.id = beschwerde.getId();
            this.timestamp = new Timestamp(System.currentTimeMillis());
            this.titel = beschwerde.getTitel();
            this.status = beschwerde.getStatus();
            this.kommentar = beschwerde.getKommentar();
        }
    }

