package com.cityfeedback.backend.beschwerdeverwaltung.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BeschwerdeService {

    public static final String BUERGER_EXISTIERT_NICHT = "Buerger existiert nicht:";

    @Autowired
    private BuergerRepository buergerRepository;

    @Autowired
    private BeschwerdeRepository beschwerdeRepository;

    @Lazy
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
        try {
            List<Beschwerde> beschwerden = beschwerdeRepository.findByBuerger_Id(buergerId);
            if (beschwerden.isEmpty()) {
                throw new IllegalArgumentException("Keine Beschwerden für Buerger-ID " + buergerId + " gefunden");
            }
            return beschwerden;
        } catch (IllegalArgumentException e) {
            // Rückgabe eines leeren Arrays, wenn keine Beschwerden gefunden wurden
            return new ArrayList<>();
        }
    }


    public List<Beschwerde> getAllBeschwerden() {
        try {
            List<Beschwerde> beschwerden = beschwerdeRepository.findAll();
            if (beschwerden.isEmpty()) {
                throw new IllegalArgumentException("Keine Beschwerden vorhanden ");
            }
            return beschwerden;
        } catch (IllegalArgumentException e) {
            // Rückgabe eines leeren Arrays, wenn keine Beschwerden gefunden wurden
            return new ArrayList<>();
        }
    }

    public Beschwerde getBeschwerde(Long id){
        Optional<Beschwerde> beschwerde = beschwerdeRepository.findById(id);
        if (beschwerde.isEmpty()){
            throw new IllegalArgumentException("Beschwerde nicht gefunden ");
        }
        return beschwerde.orElse(null);
    }

    public ResponseEntity<String> createBeschwerde(String titel, String beschwerdeTyp, String textfeld, MultipartFile file, Long buergerId) {
        try {
            // Bürger suchen
            Optional<Buerger> buergerOptional = buergerService.getBuergerById(buergerId);
            if (buergerOptional.isEmpty()) {
                throw new IllegalArgumentException("Bürger mit ID " + buergerId + " nicht gefunden.");
            }
            Buerger buerger = buergerOptional.get();

            // Verarbeite den Anhang, falls vorhanden
            Anhang anhang = null;
            if (file != null && !file.isEmpty()) {
                anhang = new Anhang();
                anhang.setDateiName(file.getOriginalFilename());
                anhang.setDatenTyp(file.getContentType());
                anhang.setDateiGroesse(file.getSize());
                anhang.setDaten(file.getBytes()); // Datei in Byte-Array konvertieren
            }

            // erstelle die Beschwerde
            Beschwerde beschwerde = new Beschwerde(titel, beschwerdeTyp, textfeld, anhang, buerger);

            // Speichere die Beschwerde
            beschwerdeRepository.save(beschwerde);
            // return Erfolg
            return ResponseEntity.ok("Beschwerde erfolgreich erstellt.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler: " + e.getMessage());
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

    /*public ResponseEntity<String> deleteBeschwerde(Long beschwerdeId) {
        try {
            // Überprüfen, ob die Beschwerde existiert
            Optional<Beschwerde> beschwerdeOptional = beschwerdeRepository.findById(beschwerdeId);
            if (beschwerdeOptional.isEmpty()) {
                throw new IllegalArgumentException("Beschwerde mit ID " + beschwerdeId + " nicht gefunden.");
            }

            // Beschwerde löschen
            beschwerdeRepository.deleteById(beschwerdeId);
            return ResponseEntity.ok("Beschwerde erfolgreich gelöscht.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler: " + e.getMessage());
        }
    }*/

    public void deleteBeschwerde(Long beschwerdeId){
        try {
            // Überprüfen, ob die Beschwerde existiert
            Optional<Beschwerde> beschwerdeOptional = beschwerdeRepository.findById(beschwerdeId);
            if (beschwerdeOptional.isEmpty()) {
                throw new IllegalArgumentException("Beschwerde mit ID " + beschwerdeId + " nicht gefunden.");
            }

            // Beschwerde löschen
            beschwerdeRepository.deleteById(beschwerdeId);
        } catch (Exception e) {
        }
    }


}