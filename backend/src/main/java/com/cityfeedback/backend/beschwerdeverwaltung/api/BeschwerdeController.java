package com.cityfeedback.backend.beschwerdeverwaltung.api;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class BeschwerdeController {



   /* @GetMapping("/buerger/dashboard/{id}")
    public int getAnzahlBeschwerden(@PathVariable("id") Long id) {
        return beschwerdeService.getAnzahlBeschwerden(id);
    }*/
}