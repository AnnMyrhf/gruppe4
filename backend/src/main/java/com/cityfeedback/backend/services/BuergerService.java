package com.cityfeedback.backend.services;

import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.repositories.BuergerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@AllArgsConstructor
public class BuergerService {

    private static final Logger log = LoggerFactory.getLogger(BuergerService.class);
    BuergerRepository buergerRepository;
    //PasswordEncoder encoder;

    /**
     * Registriert einen neuen Buerger.
     * Methode fuehrt eine Validierung der übergebenen Buerger-Daten durch.
     * Im Erfolgsfall wird der Bürger in der Datenbank gespeichert.
     *
     * @param buerger der zu registrierende Buerger mit Daten
     * @return HTTP-Antwort mit Status 200 (OK) bei erfolgreichem Speichern, ansonsten 400 (Bad Request) mit einer Fehlermeldung.
     * @throws DataAccessException Falls ein Datenbankfehler auftritt.
     * @author Ann-Kathrin Meyerhof
     */
    // TODO PW-Hashing
    @Transactional//  Rollback/Fehlerbehandlung, entweder sind alle Aenderungen an der Datenbank erfolgreich oder keine
    public ResponseEntity<?> registriereBuerger(@Valid Buerger buerger) { // uebergebenes Buerger-Objekt soll vor der Verarbeitung validiert werden

        // Ueberprüfen, ob die E-Mail-Adresse bereits existiert
        if (buergerRepository.existsByEmail(buerger.getEmail())) {
            return ResponseEntity.badRequest().body("Fehler: E-Mail-Adresse existiert bereits!");
        }

        // Neuen Buerger erstellen
        Buerger neuerBuerger = new Buerger(buerger.getId(), buerger.getAnrede(), buerger.getVorname(), buerger.getNachname(), buerger.getTelefonnummer(), buerger.getEmail(), buerger.getPasswort());
        //passwordEncoder.encode(buerger.getPasswort()) // Passwort hashen


        try {
            // Bürger in der Datenbank speichern
            buergerRepository.save(neuerBuerger);
        } catch (DataIntegrityViolationException e) {
            // Abfangen von Datenbank-Integritaetsverletzungen (z. B. unique constraints)
            return ResponseEntity.badRequest().body("Fehler bei der Speicherung des Bürgers: " + e.getMessage());
        } catch (Exception e) {
            // Abfangen allgemeiner Ausnahmen
            log.error("Unvorhergesehener Fehler bei der Registrierung:", e);
            return ResponseEntity.internalServerError().body("Ein interner Fehler ist aufgetreten.");
        }

        return ResponseEntity.ok("Registrierung erfolgreich! Bitte loggen Sie sich ein, um fortzufahren." + neuerBuerger);
    }

    /**
     * Loescht einen Buerrger aus der Datenbank.
     * Methode findet den Buerger anhand der übergebenen ID und loescht ihn permanent.
     *
     * @param id Die ID des zu loeschenden Buergers.
     * @return ResponseEntity mit Erfolgsmeldung oder Fehlermeldung.
     * @throws EntityNotFoundException Wenn kein Buerger mit der angegebenen ID gefunden wird.
     * @author Ann-Kathrin Meyerhof
     */
/*    @Transactional
    public ResponseEntity<?> loescheBuerger(Long id) {
        try {
            buergerRepository.deleteById(id);
            // TODO alle zum Buerger gehoerende Beschwerden loeschen oder alle Buerger-Attribute anoynm setzen?
            return ResponseEntity.ok("Buerger-Account erfolgreich geloescht!");
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("Buerger mit der ID " + id + " existiert nicht.");
        }
    }*/
}
