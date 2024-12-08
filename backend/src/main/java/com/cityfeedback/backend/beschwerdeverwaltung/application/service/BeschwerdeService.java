package com.cityfeedback.backend.beschwerdeverwaltung.application.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.beschwerdeverwaltung.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.api.BuergerController;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeschwerdeService {

    public static final String BUERGER_EXISTIERT_NICHT = "Buerger existiert nicht:";

    @Autowired
    private BuergerRepository buergerRepository;

    @Autowired
    private BeschwerdeRepository beschwerdeRepository;
    @Autowired
    private BuergerController buergerController;

    public Beschwerde createBeschwerde(Beschwerde beschwerde) {
        return beschwerdeRepository.save(beschwerde);
    }

    public BeschwerdeService(){
        this.beschwerdeRepository = beschwerdeRepository;
    }

    // Regex-Muster zur Validierung
    private static final Pattern STATUS_PATTERN = Pattern.compile("^(OPEN|IN_PROGRESS|RESOLVED|CLOSED)$");
    private static final Pattern DATENTYP_ANHANG_PATTERN = Pattern.compile("^(application|text|image)/[a-zA-Z0-9]+$");
    private static final Pattern UNERLAUBTE_MUSTER = Pattern.compile("(<script>|DROP TABLE|INSERT INTO|DELETE FROM|UPDATE .+ SET|SELECT .+ FROM)");

    // Liste unerlaubter Zeichen
    private String[] UNERLAUBTE_ZEICHEN = {"<", ">", ";"};

    public boolean isBeschwerdeDatenGueltig(Beschwerde beschwerde) {
        if (beschwerde.getErstellDatum() == null ||
                beschwerde.getStatus() == null || beschwerde.getStatus().isEmpty() ||
                beschwerde.getBeschwerdeTyp() == null || beschwerde.getBeschwerdeTyp().isEmpty() ||
                beschwerde.getPrioritaet() == null || beschwerde.getPrioritaet().isEmpty() ||
                beschwerde.getTextfeld() == null || beschwerde.getTextfeld().isEmpty() || beschwerde.getTextfeld().length() > 1000 ||
                beschwerde.getDatentypAnhang() == null || beschwerde.getDatentypAnhang().isEmpty()) {
            return false;
        }

        // Validierung des Status
        if (!STATUS_PATTERN.matcher(beschwerde.getStatus()).matches()) {
            return false;
        }

        // Validierung des Datentyps des Anhangs
        if (!DATENTYP_ANHANG_PATTERN.matcher(beschwerde.getDatentypAnhang()).matches()) {
            return false;
        }

        // Prüft auf unerlaubte Zeichen im Text
        if (UNERLAUBTE_MUSTER.matcher(beschwerde.getTextfeld()).find()) {
            return false;
        }

        return true;
    }

   /* public int getAnzahlBeschwerden(Long id) {
        Optional<Buerger> buerger = buergerRepository.findById(id);
        List<Beschwerde> alleBeschwerden = beschwerdeRepository.findByBuerger(buerger);
        return alleBeschwerden.size();
    }*/
}