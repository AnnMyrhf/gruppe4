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

    @Test
    void testAnmeldenBuergerNichtGefunden() {
        LoginDaten loginDaten = new LoginDaten("unknown@example.com", "password");

        when(buergerRepository.findByEmail(loginDaten.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<?> response = buergerService.anmeldenBuerger(loginDaten);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("E-Mail konnten nicht gefunden"));
    }

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

    @Test
    void testRegistriereBuergerValidationError() {
        Buerger buerger = new Buerger();
        buerger.setEmail("invalid");

        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = buergerService.registriereBuerger(buerger, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(buergerRepository, never()).save(any(Buerger.class));
    }

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

    @Test
    void testLoescheBuergerNichtGefunden() {
        Long id = 1L;

        when(buergerRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = buergerService.loescheBuerger(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Bürger konnte nicht gefunden werden"));
    }

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
