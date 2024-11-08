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
    private String id;
    private Date ErstellDatum;
    private String Status;
    private String BeschwerdeTyp;
    private String Prioritaet;
    private String Textfeld;
    private boolean Anhang;
    private String DatentypAnhang;

    public Beschwerde(Date ErstellDatum,
                      String Status,
                      String BeschwerdeTyp,
                      String Prioritaet,
                      String Textfeld,
                      boolean Anhang,
                      String DatentypAnhang)
    {
        this.id = UUID.randomUUID().toString();
        this.ErstellDatum = ErstellDatum;
        this.Status = Status;
        this.BeschwerdeTyp = BeschwerdeTyp;
        this.Prioritaet = Prioritaet;
        this.Textfeld = Textfeld;
        this.Anhang = Anhang;
        this.DatentypAnhang = DatentypAnhang;
    }

    // Getter methods for each field

 /*
    public String getId() { return id; }
    public Date getErstellDatum() { return ErstellDatum; }
    public String getStatus() { return Status; }
    public String getBeschwerdeTyp() { return BeschwerdeTyp; }
    public String getPrioritaet() { return Prioritaet; }
    public String getTextfeld() { return Textfeld; }
    public boolean hasAnhang() { return Anhang; }
    public String getDatentypAnhang() { return DatentypAnhang; }

  */
}
