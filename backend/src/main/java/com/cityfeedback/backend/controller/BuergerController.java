package com.cityfeedback.backend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.services.BuergerService;


/**
 * REST-Controller für die Buerger-Registrierung
 * Verarbeitet HTTP -Requests fuer Buerger.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class BuergerController {

    BuergerService buergerService;

    @PostMapping("/buerger-registrieren")
    public ResponseEntity<?> registriereBuerger(@Valid @RequestBody Buerger buerger) {
        return buergerService.registriereBuerger(buerger);
    }

    @PostMapping("/buerger-anmelden")
    public ResponseEntity<?> anmeldenBuerger(@RequestBody Buerger buerger) {
        return buergerService.anmeldenBuerger(buerger);
    }

    @DeleteMapping("/buerger-loeschen{id}")
    public ResponseEntity<?> loescheBuerger(@PathVariable("id") Long id) {
        return buergerService.loescheBuerger(id);
    }
}