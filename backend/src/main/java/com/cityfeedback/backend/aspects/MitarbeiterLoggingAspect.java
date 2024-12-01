package com.cityfeedback.backend.aspects;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class MitarbeiterLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(MitarbeiterLoggingAspect.class);

    // Create new Mitarbeiter
    // Pointcut für create new Mitarbeiter
    @Pointcut("execution(* com.cityfeedback.backend.services.MitarbeiterService.createNewMitarbeiter(..))")
    public void createNewMitarbeiter() {}

    @AfterReturning("createNewMitarbeiter()")
    public void createNewMitarbeiterSuccess() {
        // System.out.println("Mitarbeiter speichern erfolgreich");
        logger.info("Mitarbeiter speichern erfolgreich");
    }

    @AfterThrowing(
            pointcut = "createNewMitarbeiter()",
            throwing = "e"
    )
    public void createNewMitarbeiterFailure(Exception e) {
        logger.info("Speichern fehlgeschlagen: " + e.getMessage());
    }

    //Delete Mitarbeiter
    @Pointcut("execution(* com.cityfeedback.backend.services.MitarbeiterService.deleteMitarbeiter(..))")
    public void deleteMitarbeiter() {}

    @AfterReturning("deleteMitarbeiter()")
    public void deleteMitarbeiterSuccess() {
        // System.out.println("Mitarbeiter speichern erfolgreich");
        logger.info("Mitarbeiter löschen erfolgreich");
    }

    // Test
    @Pointcut("execution(* com.cityfeedback.backend.services.MitarbeiterService.Test(..))")
    public void TestMitarbeiter() {}

    @AfterReturning(
            pointcut = "TestMitarbeiter()",
            returning = "result"
    )
    public void TestSuccess(Object result) {
        logger.info("Value return funktioniert so: " + result);
    }
}
