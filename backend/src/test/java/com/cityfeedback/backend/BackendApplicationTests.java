package com.cityfeedback.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        // Diese Methode bleibt absichtlich leer, weil sie nur sicherstellen soll,
        // dass der Spring Application Context erfolgreich geladen wird.
        // Sie dient als Basisprüfung für die Konfigurationsintegrität der Anwendung.
    }

    @Test
    void mainMethodTest() {
        BackendApplication.main(new String[] {});
        // Wenn keine Ausnahmen auftreten, besteht der Test.
    }

}
