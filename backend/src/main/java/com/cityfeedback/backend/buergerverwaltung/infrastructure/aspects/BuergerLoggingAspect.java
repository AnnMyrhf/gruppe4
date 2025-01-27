package com.cityfeedback.backend.buergerverwaltung.infrastructure.aspects;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loggt Registrierung, Login und Loeschen eines Buergers
 *
 * @author Maik Bartels
 */
@Aspect
@Component
public class BuergerLoggingAspect {


    private static final Logger logger = LoggerFactory.getLogger(BuergerLoggingAspect.class);

    // register new Mitarbeiter
    // Pointcut für create new Mitarbeiter
    @Pointcut("execution(* com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService.registriereBuerger(..))")
    public void registriereBuerger() {}

    @AfterReturning(
            pointcut = "registriereBuerger()",
            returning = "result"

    )
    public void registrierenBuergerLogging(ResponseEntity<?> result) {
        // System.out.println("Mitarbeiter speichern erfolgreich");
        if (result.getStatusCode().isError()){
            logger.error("Bürger registrieren fehlgeschlagen: " + result.getStatusCode().toString() + " "  + result.getBody().toString());
        } else {
            logger.info("Bürger registrierung erfolgreich");
        }
    }

    // Bürger anmelden
    // Pointcut für Bürger anmelden
    @Pointcut("execution(* com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService.anmeldenBuerger(..))")
    public void anmeldenBuerger() {}

    @AfterReturning(
            pointcut = "anmeldenBuerger()",
            returning = "result"

    )
    public void anmeldenBuergerLogging(ResponseEntity<?> result) {
        if (result.getStatusCode().isError()){
            logger.error("Bürger anmelden fehlgeschlagen: " + result.getStatusCode().toString() + " "  + result.getBody().toString());
        } else {
            logger.info("Bürger anmelden erfolgreich:" + result.getStatusCode().toString());
        }
    }

    //Bürger löschen
    @Pointcut("execution(* com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService.loescheBuerger(..))")
    public void loescheBuerger() {}

    @AfterReturning(
            pointcut = "loescheBuerger()",
            returning = "result"

    )
    public void loescheBuergerLogging(ResponseEntity<?> result) {
        if (result.getStatusCode().isError()){
            logger.error("Bürger löschen fehlgeschlagen: " + result.getStatusCode().toString() + " "  + result.getBody().toString());
        } else {
            logger.info("Bürger löschen erfolgreich");
        }
    }


}
