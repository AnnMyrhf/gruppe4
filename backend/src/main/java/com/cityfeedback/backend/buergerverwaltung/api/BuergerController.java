package com.cityfeedback.backend.buergerverwaltung.api;

import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService;

/**
 * REST-Controller für die Buerger
 * Verarbeitet HTTP -Requests fuer Buerger.
 *
 * @author Ann-Kathrin Meyerhof, Maik Bartels
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class BuergerController {

    BuergerService buergerService;

    @PostMapping("/buerger-registrieren")
    public ResponseEntity<?> registriereBuerger(@Valid @RequestBody Buerger buerger, BindingResult bindingResult) {
        return buergerService.registriereBuerger(buerger, bindingResult);
    }

    @PostMapping("/buerger-anmelden")
    public ResponseEntity<?> anmeldenBuerger(@RequestBody LoginDaten loginDaten) {
        return buergerService.anmeldenBuerger(loginDaten);
    }

    @DeleteMapping("/buerger-loeschen/{id}")
    public ResponseEntity<?> loescheBuerger(@PathVariable("id") Long id) {
        return buergerService.loescheBuerger(id);
    }

    @GetMapping("/buerger-information/{id}")
    public Buerger getBuergerInfo(@PathVariable("id") Long id) {
        return buergerService.getBuergerById(id).get();
    }
}