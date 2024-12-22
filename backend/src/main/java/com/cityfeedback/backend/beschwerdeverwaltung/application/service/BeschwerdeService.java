package com.cityfeedback.backend.beschwerdeverwaltung.application.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
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
/*
    public boolean isBeschwerdeDatenGueltig(Beschwerde beschwerde) {
        if (beschwerde.getErstellDatum() == null ||
                beschwerde.getStatus() == null || beschwerde.getStatus().isEmpty() ||
                beschwerde.getBeschwerdeTyp() == null || beschwerde.getBeschwerdeTyp().isEmpty() ||
                beschwerde.getPrioritaet() == null || beschwerde.getPrioritaet().isEmpty() ||
                beschwerde.getTextfeld() == null || beschwerde.getTextfeld().isEmpty() || beschwerde.getTextfeld().length() > 1000 ||
                beschwerde.getAnhang()nhang() == null || beschwerde.getAnhang().isEmpty()) {
            return false;
        }

        // Validierung des Status
        if (!STATUS_PATTERN.matcher(beschwerde.getStatus()).matches()) {
            return false;
        }

        // Validierung des Datentyps des Anhangs
        if (!DATENTYP_ANHANG_PATTERN.matcher(beschwerde.getAnhang()).matches()) {
            return false;
        }

        // Prüft auf unerlaubte Zeichen im Text
        if (UNERLAUBTE_MUSTER.matcher(beschwerde.getTextfeld()).find()) {
            return false;
        }

        return true;
    }*/

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

    public ResponseEntity<?> createBeschwerde(@Valid Beschwerde beschwerde, Long id) { // uebergebenes Buerger-Objekt soll vor der Verarbeitung validiert werden
        Optional<Buerger> ersteller = buergerService.getBuergerById(id);
        Buerger buerger = null;
        if (ersteller.isPresent()){
            buerger = ersteller.get();
        }
        // Neue Beschwerde erstellen
        Beschwerde newBeschwerde = new Beschwerde(beschwerde.getTitel(), beschwerde.getBeschwerdeTyp(), beschwerde.getTextfeld(), beschwerde.getAnhang(),  buerger);

        try {
            // Bürger in der Datenbank speichern

            beschwerdeRepository.save(newBeschwerde);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
                return ResponseEntity.badRequest().body("Ein Datenbankfehler ist aufgetreten: " + cve.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein interner Fehler ist aufgetreten.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein interner Fehler ist aufgetreten.");
        }
        return ResponseEntity.ok("Beschwerde erfolgreich erstellt!");
    }
}