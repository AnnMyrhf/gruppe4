package com.cityfeedback.backend.buergerverwaltung.api;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.model.Beschwerde;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService;

import java.util.List;


/**
 * REST-Controller für die Buerger-Registrierung
 * Verarbeitet HTTP -Requests fuer Buerger.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class BuergerController {

    BuergerService buergerService;

    BeschwerdeService beschwerdeService;

    @PostMapping("/buerger-registrieren")
    public ResponseEntity<?> registriereBuerger(@Valid @RequestBody Buerger buerger) {
        return buergerService.registriereBuerger(buerger);
    }

    @PostMapping("/buerger-anmelden")
    public ResponseEntity<?> anmeldenBuerger(@RequestBody LoginDaten loginDaten) {
        return buergerService.anmeldenBuerger(loginDaten);
    }

    @GetMapping("/buerger/dashboard/{buerger}")
    public int getAnzahlBeschwerden(@PathVariable("buerger") Buerger buerger){
        return beschwerdeService.getAnzahlBeschwerden(buerger);
    }

    @DeleteMapping("/buerger-loeschen/{id}")
    public ResponseEntity<?> loescheBuerger(@PathVariable("id") Long id) {
        return buergerService.loescheBuerger(id);
    }
}