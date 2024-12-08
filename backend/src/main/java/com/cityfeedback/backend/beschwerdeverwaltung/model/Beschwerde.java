package com.cityfeedback.backend.beschwerdeverwaltung.model;

import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Column(length = 2000)
    private String textfeld;
    private boolean anhang;
    private String datentypAnhang;

    @ManyToOne
    @JoinColumn(name = "buerger_id")
    private Buerger buerger;

}