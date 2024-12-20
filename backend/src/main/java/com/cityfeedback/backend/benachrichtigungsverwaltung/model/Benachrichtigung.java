package com.cityfeedback.backend.benachrichtigungsverwaltung.model;

import java.time.*;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Benachrichtigung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate datum;

    @Column(nullable = false)
    private LocalTime zeit;

    @Column(nullable = false)
    private String kommentar;

    @ManyToOne
    @JoinColumn(name = "beschwerde_id", nullable = false)
    private Beschwerde beschwerde;
}