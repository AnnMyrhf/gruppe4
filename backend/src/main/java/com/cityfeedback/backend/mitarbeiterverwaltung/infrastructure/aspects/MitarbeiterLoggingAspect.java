package com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.aspects;

import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class MitarbeiterLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(MitarbeiterLoggingAspect.class);

    // Mitarbeiter registrieren
    // Pointcut für Mitarbeiter registrieren
    @Pointcut("execution(* com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService.registriereMitarbeiter(..))")
    public void registriereMitarbeiter() {}

    @AfterReturning(
            pointcut = "registriereMitarbeiter()",
            returning = "result"

    )
    public void registriereMitarbeiterLogging(ResponseEntity<?> result) {
        if (result.getStatusCode().isError()){
            logger.error("Mitarbeiter registrieren fehlgeschlagen: " + result.getStatusCode().toString() + " "  + result.getBody().toString());
        } else {
            logger.info("Mitarbeiter registrieren erfolgreich: " + result.getStatusCode().toString());
        }
    }

    // Mitarbeiter anmelden
    // Pointcut für Mitarbeiter anmelden
    @Pointcut("execution(* com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService.anmeldenMitarbeiter(..))")
    public void anmeldenMitarbeiter() {}

    @AfterReturning(
            pointcut = "anmeldenMitarbeiter()",
            returning = "result"

    )
    public void anmeldenMitarbeiterLogging(ResponseEntity<?> result) {
        if (result.getStatusCode().isError()){
            logger.error("Mitarbeiter anmelden fehlgeschlagen: " + result.getStatusCode().toString() + " "  + result.getBody().toString());
        } else {
            logger.info("Mitarbeiter anmelden erfolgreich: " + result.getStatusCode().toString());
        }
    }

    // Mitarbeiter anmelden
    // Pointcut für Mitarbeiter anmelden
    @Pointcut("execution(* com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService.loescheMitarbeiter(..))")
    public void loescheMitarbeiter() {}

    @AfterReturning(
            pointcut = "loescheMitarbeiter()",
            returning = "result"

    )
    public void loescheMitarbeiterLogging(ResponseEntity<?> result) {
        if (result.getStatusCode().isError()){
            logger.error("Mitarbeiter anmelden fehlgeschlagen: " + result.getStatusCode().toString() + " "  + result.getBody().toString());
        } else {
            logger.info("Mitarbeiter anmelden erfolgreich: " + result.getStatusCode().toString());
        }
    }
}