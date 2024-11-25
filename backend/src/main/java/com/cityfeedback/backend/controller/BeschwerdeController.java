package com.cityfeedback.backend.controller;

import com.cityfeedback.backend.domain.Beschwerde;
import com.cityfeedback.backend.domain.Mitarbeiter;
import com.cityfeedback.backend.repositories.BeschwerdeRepository;
import com.cityfeedback.backend.repositories.MitarbeiterRepository;
import com.cityfeedback.backend.services.BeschwerdeService;
import com.cityfeedback.backend.services.MitarbeiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/beschwerde")
@RestController
public class BeschwerdeController {
    private final BeschwerdeService beschwerdeService;

    public BeschwerdeController(BeschwerdeService beschwerdeService){
        this.beschwerdeService = beschwerdeService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAll")
    List<Beschwerde> getAll(){
        return beschwerdeService.getAll();
    }


}
