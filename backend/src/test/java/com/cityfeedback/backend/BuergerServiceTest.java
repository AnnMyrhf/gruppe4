package com.cityfeedback.backend.buergerverwaltung.application.service;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.security.JwtUtils;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuergerServiceTest {

    @Mock
    private BuergerRepository buergerRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private BeschwerdeService beschwerdeService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private BuergerService buergerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testet die erfolgreiche Anmeldung eines Bürgers.
     * Stellt sicher, dass bei gültigen Login-Daten ein JWT-Token generiert wird.
     */
    @Test
    void testAnmeldenBuergerErfolgreich() {
        LoginDaten loginDaten = new LoginDaten("test@example.com", "password");
        Buerger buerger = mock(Buerger.class);
        Authentication authentication = mock(Authentication.class);

        when(buergerRepository.findByEmail(loginDaten.getEmail())).thenReturn(Optional.of(buerger));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(buerger);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("dummyJwtToken");

        ResponseEntity<?> response = buergerService.anmeldenBuerger(loginDaten);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(jwtUtils, times(1)).generateJwtToken(authentication);
    }

    /**
     * Testet die Anmeldung eines Bürgers, wenn die E-Mail nicht gefunden wird.
     * Stellt sicher, dass eine entsprechende Fehlermeldung zurückgegeben wird.
     */
    @Test
    void testAnmeldenBuergerNichtGefunden() {
        LoginDaten loginDaten = new LoginDaten("unknown@example.com", "password");

        when(buergerRepository.findByEmail(loginDaten.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<?> response = buergerService.anmeldenBuerger(loginDaten);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("E-Mail konnten nicht gefunden"));
    }

    /**
     * Testet die erfolgreiche Registrierung eines Bürgers.
     * Stellt sicher, dass ein neuer Bürger korrekt gespeichert wird.
     */
    @Test
    void testRegistriereBuergerErfolgreich() {
        Buerger buerger = new Buerger();
        buerger.setEmail("test@example.com");
        buerger.setPasswort("password");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(buergerRepository.existsByEmail(buerger.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(buerger.getPasswort())).thenReturn("encodedPassword");

        ResponseEntity<?> response = buergerService.registriereBuerger(buerger, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(buergerRepository, times(1)).save(any(Buerger.class));
    }

    /**
     * Testet die Registrierung eines Bürgers bei Validierungsfehlern.
     * Stellt sicher, dass keine Speicherung erfolgt und ein Fehler zurückgegeben wird.
     */
    @Test
    void testRegistriereBuergerValidationError() {
        Buerger buerger = new Buerger();
        buerger.setEmail("invalid");

        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = buergerService.registriereBuerger(buerger, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(buergerRepository, never()).save(any(Buerger.class));
    }

    /**
     * Testet die Registrierung eines Bürgers, wenn die E-Mail bereits existiert.
     * Stellt sicher, dass keine Speicherung erfolgt und ein Fehler zurückgegeben wird.
     */
    @Test
    void testRegistriereBuergerEmailExists() {
        Buerger buerger = new Buerger();
        buerger.setEmail("existing@example.com");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(buergerRepository.existsByEmail(buerger.getEmail())).thenReturn(true);

        ResponseEntity<?> response = buergerService.registriereBuerger(buerger, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("E-Mail-Adresse existiert bereits"));
    }

    /**
     * Testet die Registrierung eines Bürgers bei einem Datenbankfehler.
     * Stellt sicher, dass ein entsprechender Fehler zurückgegeben wird.
     */
    @Test
    void testRegistriereBuergerDatabaseError() {
        Buerger buerger = new Buerger();
        buerger.setEmail("test@example.com");
        buerger.setPasswort("password");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(buergerRepository.existsByEmail(buerger.getEmail())).thenReturn(false);
        doThrow(new DataIntegrityViolationException("Database error")).when(buergerRepository).save(any(Buerger.class));

        ResponseEntity<?> response = buergerService.registriereBuerger(buerger, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Fehler bei der Speicherung des Bürgers"));
    }

    /**
     * Testet das erfolgreiche Löschen eines Bürgers.
     * Stellt sicher, dass der Bürger aus der Datenbank entfernt wird.
     */
    @Test
    void testLoescheBuergerErfolgreich() {
        Long id = 1L;
        Buerger buerger = new Buerger();
        when(buergerRepository.findById(id)).thenReturn(Optional.of(buerger));
        when(beschwerdeService.getBeschwerdenByBuergerId(id)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = buergerService.loescheBuerger(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(buergerRepository, times(1)).delete(buerger);
    }

    /**
     * Testet das Löschen eines Bürgers, wenn der Bürger nicht gefunden wird.
     * Stellt sicher, dass ein entsprechender Fehler zurückgegeben wird.
     */
    @Test
    void testLoescheBuergerNichtGefunden() {
        Long id = 1L;

        when(buergerRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = buergerService.loescheBuerger(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Bürger konnte nicht gefunden werden"));
    }

    /**
     * Testet das Löschen eines Bürgers mit vorhandenen Beschwerden.
     * Stellt sicher, dass die Beschwerden des Bürgers entfernt werden.
     */
    @Test
    void testLoescheBuergerMitBeschwerden() {
        Long id = 1L;
        Beschwerde beschwerde = new Beschwerde();
        when(beschwerdeService.getBeschwerdenByBuergerId(id)).thenReturn(List.of(beschwerde));
        when(buergerRepository.findById(id)).thenReturn(Optional.of(new Buerger()));

        ResponseEntity<?> response = buergerService.loescheBuerger(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(beschwerdeService, times(1)).deleteBeschwerde(beschwerde.getId());
    }

}
