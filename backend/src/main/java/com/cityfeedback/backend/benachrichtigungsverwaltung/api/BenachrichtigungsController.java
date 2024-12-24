package com.cityfeedback.backend.benachrichtigungsverwaltung.api;

import com.cityfeedback.backend.benachrichtigungsverwaltung.application.service.BenachrichtigungsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class BenachrichtigungsController {

    @Autowired
    private BenachrichtigungsService benachrichtigungsService;

    @GetMapping("/buerger/willkommensmail")
    public String sendeEmailNachBuergerRegistrierung(
            @RequestParam String empfaenger,
            @RequestParam String betreff,
            @RequestParam String text) {
        benachrichtigungsService.sendeEmail(empfaenger, betreff, text);
        return "Willkommensmail erfolgreich an Buerger versendet!";
    }

    @GetMapping("/beschwerde/erstellen/bestaetigung")
    public String sendeEmailNachBeschwerdeErstellen(
            @RequestParam String empfaenger,
            @RequestParam String betreff,
            @RequestParam String text) {
        benachrichtigungsService.sendeEmail(empfaenger, betreff, text);
        return "Bestaetigungsmail fuer Beschwerde erfolgreich an Buerger versendet!";
    }
}