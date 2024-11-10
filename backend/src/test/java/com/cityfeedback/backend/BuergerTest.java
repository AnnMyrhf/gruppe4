package com.cityfeedback.backend;

import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.repositories.BuergerRepository;
import com.cityfeedback.backend.services.BuergerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.lang.module.ResolutionException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse fuer Buerger
 */
@SpringBootTest
public class BuergerTest {

    // Testobjekte
    Buerger testBuerger1 = new Buerger(123L, "Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "StarkesPW1?");
    Buerger testBuerger2 = new Buerger(124L, "Frau", "Julia", "Mustermann", "987654321", "maxi.musterfau@example.com", "StarkesPW1?");
    Buerger testBuerger3 = new Buerger(125L, "Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!");
    Buerger testBuerger4 = new Buerger(126L, "Herr", "Juan", "Perez", "123456789", "j.perez@example.com", "pinFuerte123!");

    @Autowired
    private BuergerService buergerService;
    @Autowired
    private BuergerRepository buergerRepository;

    @BeforeEach
    void setUp() {
        // vor jedem Test wird die DB geleert
        buergerRepository.deleteAll();
    }

    @Test
    public void registriereBuerger_sollErfolgreichSein() { // Ueberprüft, ob ein Buerger erfolgreich gespeichert werden kann.

        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(buergerRepository.existsByEmail(testBuerger3.getEmail()));

    }

    @Test
    public void registriereBuerger_sollFehlerWerfenBeiDoppelterEmail() {  // Prueft, ob bei einer bereits bestehenden E-Mail-Adresse ein Fehler zurückgegeben wird.
        buergerRepository.save(testBuerger1);
        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

 /*  @Test
    public void registriereBuerger_sollPasswortHashen() { // ueberprüft, ob das Passwort vor der Speicherung gehasht wird.
        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger3);

        Buerger gespeicherterBuerger = buergerRepository.findById(testBuerger3.getId()).get();
        assertNotEquals("pinFuerte123!", gespeicherterBuerger.getPasswort());

    }*/

    /*@Test
    public void loescheBuerger_sollErfolgreichSein() { // Ueberprüft, ob ein Buerger erfolgreich geloescht werden kann.

        buergerRepository.save(testBuerger4);

        ResponseEntity<?> response = buergerService.loescheBuerger(testBuerger4);

        assertEquals(HttpStatus.OK, response.getStatusCode());
       // assertFalse(buergerRepository.existsByEmail(testBuerger4.getEmail()));

    }*/

   /* @Test
    public void loescheBuerger_sollExceptionWerfenWennBuergerNichtExistiert() { // Prueft, ob eine ResolutionException geworfen wird, wenn versucht wird, einen nicht existierenden Buerrger zu loeschen.

        assertThrows(ResolutionException.class, () -> buergerService.loescheBuerger(testBuerger1.getId()));
    }*/
}