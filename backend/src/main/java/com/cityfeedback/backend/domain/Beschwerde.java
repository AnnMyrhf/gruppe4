package com.cityfeedback.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Beschwerde {
    @Id
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date erstellDatum;
    private String status;
    private String beschwerdeTyp;
    private String prioritaet;
    private String textfeld;
    private boolean anhang;
    private String datentypAnhang;

    @ManyToOne
    @JoinColumn(name = "buerger_id")
    private Buerger buerger;

}