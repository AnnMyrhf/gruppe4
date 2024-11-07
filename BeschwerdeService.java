package com.cityfeedback.backend.services;

import java.util.regex.Pattern;
import com.cityfeedback.backend.domain.Beschwerde;

public class BeschwerdeService {

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
}

