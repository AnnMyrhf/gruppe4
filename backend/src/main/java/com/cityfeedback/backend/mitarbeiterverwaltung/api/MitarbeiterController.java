package com.cityfeedback.backend.mitarbeiterverwaltung.api;

import com.cityfeedback.backend.mitarbeiterverwaltung.domain.model.Mitarbeiter;
import com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * REST-Controller fuer Mitarbeiter
 * Verarbeitet HTTP -Requests eines Mitarbeiters
 *
 * @author Maik Bartels, Ann-Kathrin Meyerhof
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
public class MitarbeiterController {

    MitarbeiterService mitarbeiterService;

    @PostMapping("/mitarbeiter-registrieren")
    public ResponseEntity<?> registriereMitarbeiter(@Valid @RequestBody Mitarbeiter mitarbeiter, BindingResult bindingResult) {
        return mitarbeiterService.registriereMitarbeiter(mitarbeiter, bindingResult);
    }

    @PostMapping("/mitarbeiter-anmelden")
    public ResponseEntity<?> anmeldenMitarbeiter(@RequestBody LoginDaten loginDaten) {
        return mitarbeiterService.anmeldenMitarbeiter(loginDaten);
    }

    @DeleteMapping("/mitarbeiter-loeschen/{id}")
    public ResponseEntity<?> loescheBuerger(@PathVariable("id") Long id) {
        return mitarbeiterService.loescheMitarbeiter(id);
    }

    @GetMapping("/mitarbeiter-information/{id}")
    public Mitarbeiter getMitarbeiterInfo(@PathVariable("id") Long id){
        return mitarbeiterService.getMitarbeiterById(id).get();
    }
}
