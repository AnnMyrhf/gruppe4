package com.cityfeedback.backend;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import com.cityfeedback.backend.security.BenutzerDetailsService;
import com.cityfeedback.backend.security.JwtResponse;
import com.cityfeedback.backend.security.JwtUtils;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SecurityTest {

    @Autowired
    private BenutzerDetailsService benutzerDetailsService;
    @Autowired
    private BuergerRepository buergerRepository;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @Value("${app.jwtSecret}") // JWT ist 24 Std. gueltig
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Leere Liste fuer Beschwerden
    final List<Beschwerde> beschwerden = new ArrayList<>();

    // Testobjekte
    Mitarbeiter testMitarbeiter1 = new Mitarbeiter(2L, "Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef");
    Mitarbeiter testMitarbeiter2 = new Mitarbeiter(4L, "Herr", "Max", "Mustermann", "123456", "Hallo@web.de", "StarkesPW11!", "Verwaltung", "Assistenz");
    Buerger testBuerger1 = new Buerger(3L, "Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "StarkesPW11?", beschwerden);
    Buerger testBuerger2 = new Buerger(4L, "Frau", "Julia", "Mustermann", "987654321", "maxi.musterfau@example.de", "StarkesPW1?", beschwerden);
    JwtResponse jwtResponseBuerger = new JwtResponse("eyJhbGciOiJIUzI1NiIsInR5...", 1L,  "test@example.com",new String[]{"BUERGER"} );
    JwtResponse jwtResponseMitarbeiter = new JwtResponse("eyJhbGciOiJIUzI1NiIsInR6...", 1L,  "test@test.com",new String[]{"MITARBEITER"} );


    @BeforeEach
    void setUp() {
        // vor jedem Test wird die DB geleert
        buergerRepository.deleteAll();
        mitarbeiterRepository.deleteAll();
    }
    @Test
    void testLoadUserByUsername_Mitarbeiter_SollErfolgreichSein() {
        mitarbeiterRepository.save(testMitarbeiter1);
        mitarbeiterRepository.findByEmail(testMitarbeiter1.getEmail());

        UserDetails userDetails = benutzerDetailsService.loadUserByUsername("Hallo@web.com");
        assertEquals(testMitarbeiter1, userDetails);
    }

    @Test
    void testLoadUserByUsername_Buerger_SollErfolgreichSein() {
        buergerRepository.save(testBuerger1);
        buergerRepository.findByEmail(testBuerger1.getEmail());

        UserDetails userDetails = benutzerDetailsService.loadUserByUsername("maxi.musterfau@example.com");
        assertEquals(testBuerger1, userDetails);
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        mitarbeiterRepository.save(testMitarbeiter1);
        buergerRepository.save(testBuerger1);
        mitarbeiterRepository.findByEmail("nicht.gefunden@example.com");
        buergerRepository.findByEmail("nicht.gefunden@example.com");

        assertThrows(UsernameNotFoundException.class, () -> benutzerDetailsService.loadUserByUsername("nicht.gefunden@example.com"));
    }

    @Test
    void testConstructorAndGetters_Buerger() {
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5...", jwtResponseBuerger.getAccessToken());
        assertEquals("Bearer", jwtResponseBuerger.getTokenType());
        assertEquals(1, jwtResponseBuerger.getId());
        assertEquals("test@example.com", jwtResponseBuerger.getEmail());
        assertArrayEquals(new String[]{"BUERGER"}, (Object[]) jwtResponseBuerger.getRole());
    }

    @Test
    void testConstructorAndGetters_Mitarbeiter() {
        assertEquals("eyJhbGciOiJIUzI1NiIsInR6...", jwtResponseMitarbeiter.getAccessToken());
        assertEquals("Bearer", jwtResponseMitarbeiter.getTokenType());
        assertEquals(1, jwtResponseMitarbeiter.getId());
        assertEquals("test@test.com", jwtResponseMitarbeiter.getEmail());
        assertArrayEquals(new String[]{"MITARBEITER"}, (Object[]) jwtResponseMitarbeiter.getRole());
    }

    @Test
    void testGetters_Buerger() {
        String actualToken = jwtResponseBuerger.getAccessToken();
        String actualType = jwtResponseBuerger.getTokenType();
        Long actualId = jwtResponseBuerger.getId();
        String actualEmail = jwtResponseBuerger.getEmail();
        Object[] actualRole = (Object[]) jwtResponseBuerger.getRole();

        assertEquals("eyJhbGciOiJIUzI1NiIsInR5...", actualToken);
        assertEquals("Bearer", actualType);
        assertEquals(1, actualId);
        assertEquals("test@example.com", actualEmail);
        assertArrayEquals(new String[]{"BUERGER"}, actualRole);
    }

    @Test
    void testSetters_Mitarbeiter() {
        String newToken = "newToken";
        String newType = "Custom";
        jwtResponseMitarbeiter.setAccessToken(newToken);
        jwtResponseMitarbeiter.setTokenType(newType);

        assertEquals(newToken, jwtResponseMitarbeiter.getAccessToken());
        assertEquals(newType, jwtResponseMitarbeiter.getTokenType());
    }

        @Test
     void testToString_Buerger() {
        String toString = jwtResponseBuerger.toString();

        assertTrue(toString.contains("eyJhbGciOiJIUzI1NiIsInR5..."));
        //assertTrue(toString.contains("1"));
        assertTrue(toString.contains("test@example.com"));
       // assertTrue(toString.contains("BUERGER"));
    }

    @Test
    void testGenerateJwtTokenMitEmptyEmail() {
        String email = "";
        String token = jwtUtils.generateJwtTokenMitEmail(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());

    }

    @Test
    void testGenerateJwtTokenMitNullEmail() {
        String email = null;
        String token = jwtUtils.generateJwtTokenMitEmail(email);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

}
