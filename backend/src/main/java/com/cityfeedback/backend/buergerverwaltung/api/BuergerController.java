package com.cityfeedback.backend.buergerverwaltung.api;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
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
    public Buerger getBuergerInfo(@PathVariable("id") Long id){
        return buergerService.getBuergerById(id).get();
    }
}