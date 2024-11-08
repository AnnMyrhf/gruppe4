package com.cityfeedback.backend;

import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.repositories.BuergerRepository;
import com.cityfeedback.backend.services.BuergerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse fuer Buerger
 */
@SpringBootTest
public class BuergerTest {

    @Autowired
    private BuergerService buergerService;

    @Autowired
    private BuergerRepository buergerRepository;

    // Testobjekte
    Buerger testBuerger1 = new Buerger("", "Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!");
    Buerger testBuerger2 = new Buerger("2", "Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "1StarkesPW?");

    @BeforeEach
    void setUp() {
        // vor jedem Test

    }

   @Test
   public void testErfolgreicheRegistrierung() {
        ResponseEntity<?> response = buergerService.regstriereBuerger(testBuerger1);

       assertEquals(HttpStatus.OK, response.getStatusCode());
       //assertTrue(buergerRepository.existsByEmail(testBuerger1.getEmail()));
    }
}