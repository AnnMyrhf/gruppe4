package com.cityfeedback.backend.mitarbeiterverwaltung.api;

import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
