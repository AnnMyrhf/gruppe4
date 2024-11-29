package com.cityfeedback.backend.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Pointcut für alle Methoden in der ExampleService-Klasse
    @Pointcut("execution(* com.cityfeedback.backend.services.MitarbeiterService.*(..))")
    public void allMethodsInExampleService() {}

    // Advice, das vor der Ausführung einer Methode ausgeführt wird
    @Before("allMethodsInExampleService()")
    public void logBefore() {
        System.out.println("LoggingAspect: Before method execution");
    }

    // Advice, das nach der Ausführung einer Methode ausgeführt wird
    @After("allMethodsInExampleService()")
    public void logAfter() {
        System.out.println("LoggingAspect: After method execution");
    }
}
