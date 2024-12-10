package com.cityfeedback.backend.beschwerdeverwaltung.api;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.model.Beschwerde;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class BeschwerdeController {

    BeschwerdeService beschwerdeService;

    @GetMapping("/buerger/dashboard/{id}")
    public List<Beschwerde> getBeschwerdenByBuergerId(@PathVariable("id") Long id){
        return beschwerdeService.getBeschwerdenByBuergerId(id);
    }
}