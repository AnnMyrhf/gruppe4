package com.cityfeedback.backend.beschwerdeverwaltung.application.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.api.BuergerController;
import com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BeschwerdeService {

    public static final String BUERGER_EXISTIERT_NICHT = "Buerger existiert nicht:";

    @Autowired
    private BuergerRepository buergerRepository;

    @Autowired
    private BeschwerdeRepository beschwerdeRepository;
    @Autowired
    private BuergerService buergerService;

    public BeschwerdeService() {
        this.beschwerdeRepository = beschwerdeRepository;
    }

    // Regex-Muster zur Validierung
    private static final Pattern STATUS_PATTERN = Pattern.compile("^(OPEN|IN_PROGRESS|RESOLVED|CLOSED)$");
    private static final Pattern DATENTYP_ANHANG_PATTERN = Pattern.compile("^(application|text|image)/[a-zA-Z0-9]+$");
    private static final Pattern UNERLAUBTE_MUSTER = Pattern.compile("(<script>|DROP TABLE|INSERT INTO|DELETE FROM|UPDATE .+ SET|SELECT .+ FROM)");

    // Liste unerlaubter Zeichen
    private String[] UNERLAUBTE_ZEICHEN = {"<", ">", ";"};

    public List<Beschwerde> getBeschwerdenByBuergerId(Long buergerId) {
        List<Beschwerde> beschwerden = beschwerdeRepository.findByBuerger_Id(buergerId);
        if (beschwerden.isEmpty()) {
            throw new IllegalArgumentException("Keine Beschwerden für Buerger-ID " + buergerId + " gefunden");
        }
        return beschwerden;
    }

    public List<Beschwerde> getAllBeschwerden() {
        List<Beschwerde> beschwerden = beschwerdeRepository.findAll();
        if (beschwerden.isEmpty()) {
            throw new IllegalArgumentException("Keine Beschwerden vorhanden ");
        }
        return beschwerden;
    }

    public Beschwerde getBeschwerde(Long id){
        Optional<Beschwerde> beschwerde = beschwerdeRepository.findById(id);
        if (beschwerde.isEmpty()){
            throw new IllegalArgumentException("Beschwerde nicht gefunden ");
        }
        return beschwerde.orElse(null);
    }

    public ResponseEntity<?> createBeschwerde(@Valid Beschwerde beschwerde, Long buergerId) {
        try {
            // Überprüfen, ob der Bürger existiert
            Optional<Buerger> buergerOptional = buergerService.getBuergerById(buergerId);
            if (buergerOptional.isEmpty()) {
                throw new IllegalArgumentException("Bürger mit ID " + buergerId + " nicht gefunden.");
            }

            Buerger buerger = buergerOptional.get();

            // Setze Bürger in die Beschwerde
            beschwerde.setBuerger(buerger);

            // Speichere die Beschwerde
            beschwerdeRepository.save(beschwerde);

            return ResponseEntity.ok("Beschwerde erfolgreich erstellt.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler: " + e.getMessage());
        }
    }


    public Beschwerde updateKommentar(Long beschwerdeId, String kommentar) {
        Beschwerde beschwerde = beschwerdeRepository.findById(beschwerdeId)
                .orElseThrow(() -> new IllegalArgumentException("Beschwerde nicht gefunden"));

        beschwerde.setKommentar(kommentar);
        return beschwerdeRepository.save(beschwerde);
    }

    public Beschwerde updateStatus(Long beschwerdeId, String status) {
        Beschwerde beschwerde = beschwerdeRepository.findById(beschwerdeId)
                .orElseThrow(() -> new IllegalArgumentException("Beschwerde nicht gefunden"));

        Status newStatus = Status.valueOf(status.toUpperCase()); // Konvertierung in Großbuchstaben
        beschwerde.setStatus(newStatus); // Setzen des Status

        return beschwerdeRepository.save(beschwerde);
    }
}