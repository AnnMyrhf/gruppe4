package com.cityfeedback.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodTest() {
        BackendApplication.main(new String[] {});
        // Wenn keine Ausnahmen auftreten, besteht der Test.
    }

}
