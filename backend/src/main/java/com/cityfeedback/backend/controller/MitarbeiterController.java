package com.cityfeedback.backend.controller;

import com.cityfeedback.backend.domain.Mitarbeiter;
import com.cityfeedback.backend.repositories.MitarbeiterRepository;
import com.cityfeedback.backend.services.MitarbeiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping("/mitarbeiter")
@RestController
public class MitarbeiterController {

    private final MitarbeiterRepository mitarbeiterRepository;
    private final MitarbeiterService mitarbeiterService;

    public MitarbeiterController(MitarbeiterRepository mitarbeiterRepository, MitarbeiterService mitarbeiterService){
        this.mitarbeiterRepository = mitarbeiterRepository;
        this.mitarbeiterService = mitarbeiterService;
    }

    @GetMapping("/all")
    List<Mitarbeiter> findAll(){
        return mitarbeiterRepository.findAll();
    }

    @GetMapping("/{id}")
    Mitarbeiter findById(@PathVariable Integer id){
        Optional<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findById(id);
        if (mitarbeiter.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return mitarbeiter.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Mitarbeiter mitarbeiter){
        mitarbeiterRepository.create(mitarbeiter);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        mitarbeiterRepository.delete(id);
    }

}
