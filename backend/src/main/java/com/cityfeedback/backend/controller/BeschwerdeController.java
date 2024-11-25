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

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/beschwerde")
@RestController
public class BeschwerdeController {
    private final BeschwerdeService beschwerdeService;
    private final BeschwerdeRepository beschwerdeRepository;

    public BeschwerdeController(BeschwerdeService beschwerdeService, BeschwerdeRepository beschwerdeRepository){
        this.beschwerdeService = beschwerdeService;
        this.beschwerdeRepository = beschwerdeRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAll")
    List<Beschwerde> getAll(){
        return beschwerdeService.getAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    Beschwerde getById(@PathVariable long id){
        Optional<Beschwerde> beschwerde =  beschwerdeRepository.findById(id);
        if (beschwerde.isEmpty()){
            throw new IllegalArgumentException("Beschwerde " + id + " nicht gefunden");
        }
        return beschwerde.get();
    }
}
