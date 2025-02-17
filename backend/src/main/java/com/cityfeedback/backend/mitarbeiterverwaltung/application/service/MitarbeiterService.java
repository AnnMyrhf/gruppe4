package com.cityfeedback.backend.mitarbeiterverwaltung.application.service;

import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import com.cityfeedback.backend.mitarbeiterverwaltung.domain.model.Mitarbeiter;
import com.cityfeedback.backend.security.JwtResponse;
import com.cityfeedback.backend.security.JwtUtils;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementiert die Geschaeftslogik fuer die Registrierung, Login und Loeschen eines Mitarbeiters
 *
 * @author Ann-Kathrin Meyerhof, Maik Bartels
 */
@Service
@AllArgsConstructor
public class MitarbeiterService {

    public static final String MITARBEITER_EXISTIERT_NICHT = "Ein Mitarbeiter mit dieser E-Mail-Adresse existiert nicht:";

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> anmeldenMitarbeiter(LoginDaten loginDaten) {
        try {
            // Suche nach dem Buerger in der Datenbank
            mitarbeiterRepository.findByEmail(loginDaten.getEmail()).orElseThrow(() -> new UsernameNotFoundException(MITARBEITER_EXISTIERT_NICHT + loginDaten.getEmail()));

            // Authentifizierung
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDaten.getEmail(), loginDaten.getPasswort()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Holt den authentifizierten Mitarbeiter aus dem Authentication-Objekt
            Mitarbeiter authenticatedUser = (Mitarbeiter) authentication.getPrincipal();

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

    @Transactional//  Rollback/Fehlerbehandlung, entweder sind alle Aenderungen an der Datenbank erfolgreich oder keine
    public ResponseEntity<?> registriereMitarbeiter(@Valid Mitarbeiter mitarbeiter, BindingResult bindingResult) { // uebergebenes Buerger-Objekt soll vor der Verarbeitung validiert werden

        if (bindingResult.hasErrors()) {
            // Validierungsfehler sammeln und zurückgeben
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        // Ueberprüfen, ob die E-Mail-Adresse bereits existiert
        if (mitarbeiterRepository.existsByEmail(mitarbeiter.getEmail())) {
            return ResponseEntity.badRequest().body("Fehler: E-Mail-Adresse existiert bereits!");
        }

        // Neuen Mitarbeiter erstellen
        Mitarbeiter neuerMitarbeiter = new Mitarbeiter(mitarbeiter.getAnrede(), mitarbeiter.getVorname(), mitarbeiter.getNachname(), mitarbeiter.getTelefonnummer(), mitarbeiter.getEmail(), mitarbeiter.getPasswort());
        neuerMitarbeiter.setPasswort(passwordEncoder.encode(neuerMitarbeiter.getPasswort()));

        try {
            // Bürger in der Datenbank speichern
            mitarbeiterRepository.save(neuerMitarbeiter);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException cve) {
                return ResponseEntity.badRequest().body("Ein Datenbankfehler ist aufgetreten: " + cve.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein interner Fehler ist aufgetreten.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein interner Fehler ist aufgetreten.");
        }
        return ResponseEntity.ok("Registrierung erfolgreich! Bitte loggen Sie sich ein, um fortzufahren." + neuerMitarbeiter);

    }

    @Transactional
    public ResponseEntity<?> loescheMitarbeiter(Long id) {
        try {
            // Bürger in der Datenbank speichern
            Mitarbeiter mitarbeiter = mitarbeiterRepository.findById(id).orElseThrow(() -> new ResolutionException(MITARBEITER_EXISTIERT_NICHT + id));
            mitarbeiterRepository.delete(mitarbeiter);
        } catch (ResolutionException e) {
            return ResponseEntity.badRequest().body("Mitarbeiter konnte nicht gefunden werden");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein interner Fehler ist aufgetreten: " + e.getMessage());
        }
        return ResponseEntity.ok("Mitarbeiteraccount erfolgreich gelöscht");

    }

    public Optional<Mitarbeiter> getMitarbeiterById(Long id) {
        return mitarbeiterRepository.findById(id);
    }


}
