package com.cityfeedback.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beschwerde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date erstellDatum;
    private String status;
    private String beschwerdeTyp;
    private String prioritaet;
    private String textfeld;
    private boolean anhang;
    private String datentypAnhang;

    public Beschwerde(Date ErstellDatum,
                      String Status,
                      String BeschwerdeTyp,
                      String Prioritaet,
                      String Textfeld,
                      boolean Anhang,
                      String DatentypAnhang)
    {
        this.erstellDatum = ErstellDatum;
        this.status = Status;
        this.beschwerdeTyp = BeschwerdeTyp;
        this.prioritaet = Prioritaet;
        this.textfeld = Textfeld;
        this.anhang = Anhang;
        this.datentypAnhang = DatentypAnhang;
    }
}
