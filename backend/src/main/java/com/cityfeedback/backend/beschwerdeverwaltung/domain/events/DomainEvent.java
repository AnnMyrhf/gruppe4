package com.cityfeedback.backend.beschwerdeverwaltung.domain.events;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@AllArgsConstructor
public abstract class DomainEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant timestamp;

    private Long aggregateId;

    protected DomainEvent() {
        this.timestamp = Instant.now();

    }
}
/*public abstract class DomainEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long domainId;

    private final Instant timeStamp;

    protected DomainEvent(Long domainId) {
        this.domainId = domainId;
        this.timeStamp = Instant.now();
    }
}*/
