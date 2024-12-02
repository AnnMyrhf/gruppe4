package com.cityfeedback.backend;

import com.cityfeedback.backend.beschwerdeverwaltung.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.cityfeedback.backend.security.JwtResponse;
import com.cityfeedback.backend.security.JwtUtils;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Testklasse fuer Buerger
 */


@SpringBootTest
@Transactional
public class BuergerTest {

    // Leere Liste fuer Beschwerden
    private final List<Beschwerde> beschwerden = new ArrayList<>();
    // Test
    // Testobjekte
    Buerger testBuerger1 = new Buerger(123L, "Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "StarkesPW11?", beschwerden);
    Buerger testBuerger2 = new Buerger(124L, "Frau", "Julia", "Mustermann", "987654321", "maxi.musterfau@example.com", "StarkesPW1?", beschwerden);
    Buerger testBuerger3 = new Buerger(125L, "Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!", beschwerden);
    Buerger testBuerger4 = new Buerger(126L, "Herr", "Juan", "Perez", "123456789", "j.perez@example.com", "pinFuerte123!", beschwerden);
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private BuergerService buergerService;
    @Autowired
    private BuergerRepository buergerRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;


    @BeforeEach
    void setUp() {
        // vor jedem Test wird die DB geleert
        buergerRepository.deleteAll();
    }

    /**
     * Ueberprueft, ob ein Buerger erfolgreich registriert wird, einschließlich aller Attribute.
     */
    @Test
    public void registriereBuerger_sollErfolgreichSein() {

        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertTrue(buergerRepository.existsByEmail(testBuerger3.getEmail()));

    }

    /**
     * Ueberprueft, ob bei einer bereits bestehenden E-Mail-Adresse eine spezifische Fehlermeldung zurückgegeben wird.
     */
    @Test
    public void registriereBuerger_sollFehlerWerfenBeiDoppelterEmail() {
        buergerRepository.save(testBuerger1);
        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    /**
     * Ueberprueft, ob das Passwort vor der Speicherung korrekt gehasht wird.
     */
    @Test
    public void registriereBuerger_sollPasswortHashen() {
        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger3);

        //Sollte das gespeicherte Passwort nicht dem Klartext entsprechen
        Buerger gespeicherterBuerger = buergerRepository.findByEmail(testBuerger3.getEmail()).get();
        assertNotEquals(testBuerger3.getPasswort(), gespeicherterBuerger.getPasswort());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Zusaetzliche Überprüfung mit dem PasswordEncoder
        boolean istPasswortKorrekt = passwordEncoder.matches(testBuerger3.getPasswort(), gespeicherterBuerger.getPasswort());
        assertTrue(istPasswortKorrekt);

    }

    /**
     * Ueberprueft, ob ein bereits registrierter Buerger sich erfolgreich mit den korrekten Anmeldedaten anmelden kann
     * und ob dabei ein gueltiges JWT mit den korrekten Buergerinfos zurückgegeben wird.
     */
    /*@Test
    public void anmeldenBuerger_sollErfolgreichSein() {

        buergerRepository.save(testBuerger1);
        LoginDaten loginDaten = new LoginDaten(testBuerger1.getEmail(), testBuerger1.getPasswort());

        ResponseEntity<?> response = buergerService.anmeldenBuerger(loginDaten);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertNotNull(jwtResponse.getToken());
        assertEquals(testBuerger1.getId(), jwtResponse.getId());
        assertEquals(testBuerger1.getEmail(), jwtResponse.getEmail());
    }*/

    @Test
    void anmeldenBuerger_FalscheEmail() {
        buergerRepository.save(testBuerger3);
        LoginDaten loginDaten = new LoginDaten("falsche@email.de", testBuerger2.getPasswort());

        ResponseEntity<?> response = buergerService.anmeldenBuerger(loginDaten);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void anmeldenBuerger_FalschesPasswort() {
        buergerRepository.save(testBuerger3);

        LoginDaten loginDaten = new LoginDaten(testBuerger2.getEmail(), "falschesPW123!");

        ResponseEntity<?> response = buergerService.anmeldenBuerger(loginDaten);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }


    /**
     * Ueberprueft, ob ein existierender Buerger vollständig aus der Datenbank geloescht wird.
     */
    @Test
    public void loescheBuerger_sollErfolgreichSein() {

        Buerger buergerLoeschen = buergerRepository.save(testBuerger4);

        ResponseEntity<?> response = buergerService.loescheBuerger(buergerLoeschen.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(buergerRepository.existsByEmail(buergerLoeschen.getEmail()));

    }

    /**
     * Ueberprueft, ob beim Versuch, einen nicht existierenden Buerger zu loeschen, eine ResolutionException geworfen wird.
     */
    @Test
    public void loescheBuerger_sollExceptionWerfenWennBuergerNichtExistiert() {

        assertThrows(ResolutionException.class, () -> buergerService.loescheBuerger(testBuerger1.getId()));
    }
}

