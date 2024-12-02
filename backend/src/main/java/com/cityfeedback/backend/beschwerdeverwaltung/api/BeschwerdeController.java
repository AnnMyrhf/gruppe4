package com.cityfeedback.backend.beschwerdeverwaltung.api;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.beschwerdeverwaltung.model.Beschwerde;
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
    @GetMapping("/{id}")
    Beschwerde getById(@PathVariable long id){
        Optional<Beschwerde> beschwerde =  beschwerdeRepository.findById(id);
        if (beschwerde.isEmpty()){
            throw new IllegalArgumentException("Beschwerde " + id + " nicht gefunden");
        }
        return beschwerde.get();
    }


}