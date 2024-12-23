package com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BeschwerdeLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(BeschwerdeLoggingAspect.class);

    // register new Mitarbeiter
    // Pointcut für create new Mitarbeiter
    @Pointcut("execution(* com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService.createBeschwerde(..))")
    public void createBeschwerde() {}

    @AfterReturning(
            pointcut = "createBeschwerde()",
            returning = "result"

    )
    public void createBeschwerdeLogging(ResponseEntity<?> result) {
        if (result.getStatusCode().isError()){
            logger.error("Beschwerde erstellen fehlgeschlagen: " + result.getStatusCode().toString() + " "  + result.getBody().toString());
        } else {
            logger.info(result.getBody().toString() + " " + result.getStatusCode().toString());
        }
    }
}
