package com.cityfeedback.backend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.services.BuergerService;


/**
 * Rest-Controller zum Handling der Http-Requests fuer Buerger (Authentifizierung)
**/
@RestController
@AllArgsConstructor
public class BuergerController {

    BuergerService buergerService;

    @PostMapping("/buerger")
    public ResponseEntity<?> regstriereBuerger(@Valid @RequestBody Buerger buerger) {
        return buergerService.regstriereBuerger(buerger);

    }
}

