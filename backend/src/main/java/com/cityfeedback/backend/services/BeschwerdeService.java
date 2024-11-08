package com.cityfeedback.backend.services;

import java.util.regex.Pattern;
import com.cityfeedback.backend.domain.Beschwerde;

public class BeschwerdeService {

    // Regex-Muster zur Validierung
    private static final Pattern STATUS_PATTERN = Pattern.compile("^(OPEN|IN_PROGRESS|RESOLVED|CLOSED)$");
    private static final Pattern DATENTYP_ANHANG_PATTERN = Pattern.compile("^(application|text|image)/[a-zA-Z0-9]+$");

    public boolean isBeschwerdeDatenGueltig(Beschwerde Beschwerde) {
        if (Beschwerde.getErstellDatum() == null ||
                Beschwerde.getStatus() == null || Beschwerde.getStatus().isEmpty() ||
                Beschwerde.getBeschwerdeTyp() == null || Beschwerde.getBeschwerdeTyp().isEmpty() ||
                Beschwerde.getPrioritaet() == null || Beschwerde.getPrioritaet().isEmpty() ||
                Beschwerde.getTextfeld() == null || Beschwerde.getTextfeld().isEmpty() ||
                Beschwerde.getDatentypAnhang() == null || Beschwerde.getDatentypAnhang().isEmpty()) {
            return false;
        }

        // Validierung des Status
        if (!STATUS_PATTERN.matcher(Beschwerde.getStatus()).matches()) {
            return false;
        }

        // Validierung des Datentyps des Anhangs
        return DATENTYP_ANHANG_PATTERN.matcher(Beschwerde.getDatentypAnhang()).matches();
    }
}

