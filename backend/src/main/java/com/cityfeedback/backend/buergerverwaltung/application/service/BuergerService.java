package com.cityfeedback.backend.buergerverwaltung.application.service;

import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.security.JwtResponse;
import com.cityfeedback.backend.security.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BuergerService {

    public static final String BUERGER_EXISTIERT_NICHT = "Ein Buerger mit dieser E-Mail-Adresse existiert nicht:";
    private static final Logger log = LoggerFactory.getLogger(BuergerService.class);

    @Autowired
    BuergerRepository buergerRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;

    @Transactional//  Rollback/Fehlerbehandlung, entweder sind alle Aenderungen an der Datenbank erfolgreich oder keine
    public ResponseEntity<?> anmeldenBuerger(LoginDaten loginDaten) {
        try {
            // Suche nach dem Buerrger in der Datenbank
            buergerRepository.findByEmail(loginDaten.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException(BUERGER_EXISTIERT_NICHT + loginDaten.getEmail()));

            // Authentifizierung
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDaten.getEmail(), loginDaten.getPasswort())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Holt den authentifizierten Buerger aus dem Authentication-Objekt
            Buerger authenticatedUser = (Buerger) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(jwt, authenticatedUser.getId(), authenticatedUser.getEmail(), authenticatedUser.getAuthorities().toArray()));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("Buerger-Daten konnten nicht gefunden");
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Ungültige Anmeldedaten: E-Mail oder Passwort falsch!"); // aus Sicherheitsgründen kein eindeutiger Hinweis
        }
    }

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
    @Transactional//  Rollback/Fehlerbehandlung, entweder sind alle Aenderungen an der Datenbank erfolgreich oder keine
    public ResponseEntity<?> registriereBuerger(@Valid Buerger buerger) { // uebergebenes Buerger-Objekt soll vor der Verarbeitung validiert werden

        // Ueberprüfen, ob die E-Mail-Adresse bereits existiert
        if (buergerRepository.existsByEmail(buerger.getEmail())) {
            return ResponseEntity.badRequest().body("Fehler: E-Mail-Adresse existiert bereits!");
        }

        // Neuen Buerger erstellen
        Buerger neuerBuerger = new Buerger(buerger.getId(), buerger.getAnrede(), buerger.getVorname(), buerger.getNachname(), buerger.getTelefonnummer(), buerger.getEmail(), buerger.getPasswort(), buerger.getBeschwerden());
        neuerBuerger.setPasswort(passwordEncoder.encode(neuerBuerger.getPasswort()));

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
    @Transactional
    public ResponseEntity<?> loescheBuerger(Long id) {

        Buerger buerger = buergerRepository.findById(id).orElseThrow(() -> new ResolutionException(BUERGER_EXISTIERT_NICHT + id));
        buergerRepository.delete(buerger);

        return ResponseEntity.ok("Account erfolgreich geloescht erfolgreich!.");

    }

    public Optional<Buerger> getBuergerById(Long id){
        return buergerRepository.findById(id);
    }



}