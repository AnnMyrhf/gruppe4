package com.cityfeedback.backend.beschwerdeverwaltung.domain.events;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.Date;
import java.util.Objects;
/*Getter
public class BeschwerdeErstellen extends DomainEvent {
    private final Beschwerde beschwerde;
    private final Anhang anhang;

    public BeschwerdeErstellen(Long domainId, Anhang anhang, Beschwerde beschwerde) {
        super(domainId);
        this.anhang = Objects.requireNonNull(anhang, "Anhang darf nicht leer sein");
        this.beschwerde = Objects.requireNonNull(beschwerde, "Beschwerde darf nicht null sein");
    }
}*/
/*
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeschwerdeErstellen extends DomainEvent {
    private Date erstellDatum;
    private String status;
    private String prioritaet;
    private String beschwerdeTyp;
    private String titel;
    private String textfeld;
    private Anhang anhang;


}*/
