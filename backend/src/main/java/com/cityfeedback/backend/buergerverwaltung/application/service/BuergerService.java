package com.cityfeedback.backend.buergerverwaltung.application.service;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
import org.springframework.validation.BindingResult;

import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Lazy
    @Autowired
    BeschwerdeService beschwerdeService;

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
            Map<String, String> errors = new HashMap<>();
            errors.put("email", "E-Mail konnten nicht gefunden");
            return ResponseEntity.badRequest().body(errors);
        } catch (BadCredentialsException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("passwort", "Passwort stimmt nicht mit E-Mail Adresse überein");
            return ResponseEntity.badRequest().body(errors); // aus Sicherheitsgründen kein eindeutiger Hinweis
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
    public ResponseEntity<?> registriereBuerger(@Valid Buerger buerger, BindingResult bindingResult) { // uebergebenes Buerger-Objekt soll vor der Verarbeitung validiert werden

        if (bindingResult.hasErrors()) {
            // Validierungsfehler sammeln und zurückgeben
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

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
        try {
            // Bürger in der Datenbank speichern
            //Prüfen ob Bürger noch Beschwerden hat
            List<Beschwerde> beschwerden = beschwerdeService.getBeschwerdenByBuergerId(id);
            if (!beschwerden.isEmpty()){
                beschwerden.forEach(beschwerde -> beschwerdeService.deleteBeschwerde(beschwerde.getId()));
            }
            Buerger buerger = buergerRepository.findById(id).orElseThrow(() -> new ResolutionException(BUERGER_EXISTIERT_NICHT + id));
            buergerRepository.delete(buerger);
        } catch (ResolutionException e) {
            return ResponseEntity.badRequest().body("Bürger konnte nicht gefunden werden");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein interner Fehler ist aufgetreten: " + e.getMessage());
        }
        return ResponseEntity.ok("Bürgeraccount erfolgreich gelöscht");
    }

    public Optional<Buerger> getBuergerById(Long id){
        return buergerRepository.findById(id);
    }

}