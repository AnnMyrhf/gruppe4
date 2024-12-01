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
    @JoinColumn(name="buerger_id")
    private Buerger buerger;

    /* public Beschwerde(Long id,
                      // Long buerger_id,
                      Date ErstellDatum,
                      String Status,
                      String BeschwerdeTyp,
                      String Prioritaet,
                      String Textfeld,
                      boolean Anhang,
                      String DatentypAnhang)
    {
        this.id = id;
        // this.id = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
        // this.buerger_id = buerger_id;
        this.erstellDatum = ErstellDatum;
        this.status = Status;
        this.beschwerdeTyp = BeschwerdeTyp;
        this.prioritaet = Prioritaet;
        this.textfeld = Textfeld;
        this.anhang = Anhang;
        this.datentypAnhang = DatentypAnhang;
    } */

}