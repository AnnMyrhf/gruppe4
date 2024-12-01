package com.cityfeedback.backend.controller;

import com.cityfeedback.backend.domain.Beschwerde;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.services.BuergerService;

import java.util.List;


/**
 * REST-Controller für die Buerger-Registrierung
 * Verarbeitet HTTP -Requests fuer Buerger.
 */
@RestController
@AllArgsConstructor
public class BuergerController {

    BuergerService buergerService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/registriere-buerger")
    public ResponseEntity<?> registriereBuerger(@Valid @RequestBody Buerger buerger) {
        return buergerService.registriereBuerger(buerger);
    }

    @DeleteMapping("/loesche-buerger/{id}")
    public ResponseEntity<?> loescheBuerger(@PathVariable("id") Long id) {
        return buergerService.loescheBuerger(id);
    }

    @GetMapping("/beschwerden/{buerger}")
    public List<Beschwerde> beschwerden(@PathVariable Buerger buerger) {
        return buergerService.getComplaintsFromBuerger(buerger);
    }
}