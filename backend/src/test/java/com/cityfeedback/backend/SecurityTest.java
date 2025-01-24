package com.cityfeedback.backend;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import com.cityfeedback.backend.mitarbeiterverwaltung.domain.model.Mitarbeiter;
import com.cityfeedback.backend.security.BenutzerDetailsService;
import com.cityfeedback.backend.security.JwtResponse;
import com.cityfeedback.backend.security.JwtUtils;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    Mitarbeiter testMitarbeiter1 = new Mitarbeiter("Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!");
    Mitarbeiter testMitarbeiter2 = new Mitarbeiter("Herr", "Max", "Mustermann", "123456", "Hallo@web.de", "StarkesPW11!");
    Buerger testBuerger1 = new Buerger("Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "StarkesPW11?", beschwerden);
    Buerger testBuerger2 = new Buerger("Frau", "Julia", "Mustermann", "987654321", "maxi.musterfau@example.de", "StarkesPW1?", beschwerden);
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

    // Testobjekte für LoginDaten
    LoginDaten loginDaten1 = new LoginDaten("user@example.com", "password123");
    LoginDaten loginDaten2 = new LoginDaten("user@example.com", "password123");
    LoginDaten loginDaten3 = new LoginDaten("otheruser@example.com", "password456");


    // --- Tests für die LoginDaten Klasse ---

    @Test
    void testGetterSetter() {
        // Test für Getter und Setter
        LoginDaten loginDaten = new LoginDaten();
        loginDaten.setEmail("newuser@example.com");
        loginDaten.setPasswort("newpassword");

        assertEquals("newuser@example.com", loginDaten.getEmail(), "Die E-Mail-Adresse sollte korrekt gesetzt und zurückgegeben werden.");
        assertEquals("newpassword", loginDaten.getPasswort(), "Das Passwort sollte korrekt gesetzt und zurückgegeben werden.");
    }

    @Test
    void testConstructor() {
        // Test für den Konstruktor
        LoginDaten loginDaten = new LoginDaten("test@example.com", "testpassword");
        assertEquals("test@example.com", loginDaten.getEmail(), "Die E-Mail-Adresse sollte im Konstruktor gesetzt werden.");
        assertEquals("testpassword", loginDaten.getPasswort(), "Das Passwort sollte im Konstruktor gesetzt werden.");
    }

    @Test
    void testEquals() {
        // Test für equals(Object)
        assertEquals(loginDaten1, loginDaten2, "Die Objekte sollten gleich sein.");

        // Test für unterschiedliche Objekte
        assertNotEquals(loginDaten1, loginDaten3, "Die Objekte sollten ungleich sein.");

        // Test für Vergleich mit null
        assertNotEquals(loginDaten1, null, "Das Objekt sollte nicht gleich null sein.");

        // Test für Vergleich mit einem Objekt eines anderen Typs
        assertNotEquals(loginDaten1, new Object(), "Das Objekt sollte nicht gleich einem anderen Typ sein.");
    }

    @Test
    void testHashCode() {
        // Test für hashCode()
        assertEquals(loginDaten1.hashCode(), loginDaten2.hashCode(), "Die Hash-Codes sollten gleich sein.");
        assertNotEquals(loginDaten1.hashCode(), loginDaten3.hashCode(), "Die Hash-Codes sollten ungleich sein.");
    }

    @Test
    void testToString() {
        // Test für toString()
        String expectedString = "LoginDaten(email=user@example.com, passwort=password123)";
        assertEquals(expectedString, loginDaten1.toString(), "Die toString() Methode sollte korrekt arbeiten.");
    }

    @Test
    void testConstructorAndGetters() {
        // Testdaten
        String token = "testToken123";
        Long id = 1L;
        String email = "test@example.com";
        Object[] roles = {"ROLE_USER", "ROLE_ADMIN"};

        // Objekt erstellen
        JwtResponse jwtResponse = new JwtResponse(token, id, email, roles);

        // Assertions
        assertEquals(token, jwtResponse.getAccessToken());
        assertEquals("Bearer", jwtResponse.getTokenType());
        assertEquals(id, jwtResponse.getId());
        assertEquals(email, jwtResponse.getEmail());
        assertEquals(roles, jwtResponse.getRole());
    }

    @Test
    void testSetAccessToken() {
        // Objekt erstellen
        JwtResponse jwtResponse = new JwtResponse("initialToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});

        // Token ändern
        jwtResponse.setAccessToken("newToken123");

        // Assertion
        assertEquals("newToken123", jwtResponse.getAccessToken());
    }

    @Test
    void testSetTokenType() {
        // Objekt erstellen
        JwtResponse jwtResponse = new JwtResponse("testToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});

        // Typ ändern
        jwtResponse.setTokenType("CustomType");

        // Assertion
        assertEquals("CustomType", jwtResponse.getTokenType());
    }

    @Test
    void testSetEmail() {
        // Objekt erstellen
        JwtResponse jwtResponse = new JwtResponse("testToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});

        // E-Mail ändern
        jwtResponse.setEmail("newemail@example.com");

        // Assertion
        assertEquals("newemail@example.com", jwtResponse.getEmail());
    }

    @Test
    void testSetRole() {
        // Objekt erstellen
        JwtResponse jwtResponse = new JwtResponse("testToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});

        // Rolle ändern
        Object[] newRoles = {"ROLE_ADMIN"};
        jwtResponse.setRole(newRoles);

        // Assertion
        assertEquals(newRoles, jwtResponse.getRole());
    }

    @Test
    void testSetId() {
        // Objekt erstellen
        JwtResponse jwtResponse = new JwtResponse("testToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});

        // ID ändern
        jwtResponse.setId(42L);

        // Assertion
        assertEquals(42L, jwtResponse.getId());
    }


}
