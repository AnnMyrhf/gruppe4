package com.cityfeedback.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        // Diese Methode bleibt absichtlich leer, weil sie nur sicherstellen soll,
        // dass der Spring Application Context erfolgreich geladen wird.
        // Sie dient als Basisprüfung für die Konfigurationsintegrität der Anwendung.
    }

    /**
     * Überprüft, ob die Hauptanwendung ordnungsgemäß gestartet wird und der Bean
     * 'beschwerdeController' im Anwendungskontext vorhanden ist.
     * Dieser Test stellt sicher, dass die Anwendung korrekt konfiguriert ist und der
     * Controller wie erwartet verfügbar ist.
     */
    @Test
    void mainMethodTest() {
        ApplicationContext context = SpringApplication.run(BackendApplication.class, new String[]{});
        assertNotNull(context.getBean("beschwerdeController"), "Der Bean 'beschwerdeController' sollte im Kontext vorhanden sein.");
    }

}
