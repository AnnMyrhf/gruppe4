package com.cityfeedback.backend.services;

import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.repositories.BuergerRepository;
import jakarta.validation.Valid;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service

@AllArgsConstructor
public class BuergerService {

    @Autowired
    BuergerRepository buergerRepository;

    /**
     * Methode
     * @param buerger
     */
    @Transactional//  Rollback/Fehlerbehandlung, entweder sind alle Aenderungen an der Datenbank erfolgreich oder keine
    public ResponseEntity<?>regstriereBuerger(Buerger buerger) { // uebergebenes Buerger-Objekt soll vor der Verarbeitung validiert werden
        buergerRepository.save(buerger);
           // Doppelte E-MailAdressen abfangen
     /*      if (Boolean.TRUE.equals(buergerRepository.existsByEmail(buerger.getEmail()))) {
                return ResponseEntity.badRequest().body("Fehler: E-Mail-Adresse existiert bereits!");
            }

            // Erstellt neuen Account fuer den Buerger
            Buerger neuerBuerger = new Buerger(buerger.getId(), buerger.getAnrede(), buerger.getVorname(), buerger.getNachname(), buerger.getTelefonnummer(), buerger.getEmail(), buerger.getPasswort());

            buergerRepository.save(neuerBuerger);*/

            return ResponseEntity.ok("Registrierung erfolgreich! Bitte loggen Sie sich ein, um fortzufahren.");
        }
    }