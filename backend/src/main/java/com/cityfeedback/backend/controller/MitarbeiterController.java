package com.cityfeedback.backend.controller;

import com.cityfeedback.backend.domain.Mitarbeiter;
import com.cityfeedback.backend.repositories.MitarbeiterRepository;
import com.cityfeedback.backend.services.MitarbeiterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
public class MitarbeiterController {

    MitarbeiterService mitarbeiterService;

    @PostMapping("/mitarbeiter-registrieren")
    public ResponseEntity<String> create(@RequestBody Mitarbeiter mitarbeiter) {
        try {
            mitarbeiterService.createNewMitarbeiter(mitarbeiter);
            return new ResponseEntity<>("Mitarbeiter erfolgreich erstellt", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Fehler: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/mitarbeiter-loeschen{id}")
    void delete(@PathVariable Long id){
        mitarbeiterService.deleteMitarbeiter(id);
    }

}
