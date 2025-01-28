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
class SecurityTest {

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

    /**
     * Testet die Funktionalität der Methode loadUserByUsername mit einem Mitarbeiter und überprüft, ob das erwartete Benutzer-Objekt zurückgegeben wird
     */
    @Test
    void testLoadUserByUsername_Mitarbeiter_SollErfolgreichSein() {
        mitarbeiterRepository.save(testMitarbeiter1);
        mitarbeiterRepository.findByEmail(testMitarbeiter1.getEmail());

        UserDetails userDetails = benutzerDetailsService.loadUserByUsername("Hallo@web.com");
        assertEquals(testMitarbeiter1, userDetails);
    }

    /**
     * Testet die Funktionalität der Methode loadUserByUsername mit einem Bürger und überprüft, ob das erwartete Benutzer-Objekt zurückgegeben wird
     */
    @Test
    void testLoadUserByUsername_Buerger_SollErfolgreichSein() {
        buergerRepository.save(testBuerger1);
        buergerRepository.findByEmail(testBuerger1.getEmail());

        UserDetails userDetails = benutzerDetailsService.loadUserByUsername("maxi.musterfau@example.com");
        assertEquals(testBuerger1, userDetails);
    }

    /**
     * Testet den Fall, wenn der Benutzer nicht gefunden wird, und stellt sicher, dass eine UsernameNotFoundException geworfen wird
     */
    @Test
    void testLoadUserByUsernameNotFound() {
        mitarbeiterRepository.save(testMitarbeiter1);
        buergerRepository.save(testBuerger1);
        mitarbeiterRepository.findByEmail("nicht.gefunden@example.com");
        buergerRepository.findByEmail("nicht.gefunden@example.com");

        assertThrows(UsernameNotFoundException.class, () -> benutzerDetailsService.loadUserByUsername("nicht.gefunden@example.com"));
    }

    /**
     * Testet den Konstruktor und die Getter-Methoden der JwtResponse-Klasse für Bürger
     */
    @Test
    void testConstructorAndGetters_Buerger() {
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5...", jwtResponseBuerger.getAccessToken());
        assertEquals("Bearer", jwtResponseBuerger.getTokenType());
        assertEquals(1, jwtResponseBuerger.getId());
        assertEquals("test@example.com", jwtResponseBuerger.getEmail());
        assertArrayEquals(new String[]{"BUERGER"}, (Object[]) jwtResponseBuerger.getRole());
    }

    /**
     * Testet den Konstruktor und die Getter-Methoden der JwtResponse-Klasse für Mitarbeiter
     */
    @Test
    void testConstructorAndGetters_Mitarbeiter() {
        assertEquals("eyJhbGciOiJIUzI1NiIsInR6...", jwtResponseMitarbeiter.getAccessToken());
        assertEquals("Bearer", jwtResponseMitarbeiter.getTokenType());
        assertEquals(1, jwtResponseMitarbeiter.getId());
        assertEquals("test@test.com", jwtResponseMitarbeiter.getEmail());
        assertArrayEquals(new String[]{"MITARBEITER"}, (Object[]) jwtResponseMitarbeiter.getRole());
    }

    /**
     * Testet die Getter-Methoden der JwtResponse-Klasse für Bürger und überprüft, ob die Werte korrekt zurückgegeben werden
     */
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

    /**
     * Testet die Setter-Methoden der JwtResponse-Klasse für Mitarbeiter und überprüft, ob die Werte korrekt gesetzt werden
     */
    @Test
    void testSetters_Mitarbeiter() {
        String newToken = "newToken";
        String newType = "Custom";
        jwtResponseMitarbeiter.setAccessToken(newToken);
        jwtResponseMitarbeiter.setTokenType(newType);

        assertEquals(newToken, jwtResponseMitarbeiter.getAccessToken());
        assertEquals(newType, jwtResponseMitarbeiter.getTokenType());
    }

    /**
     * Testet die toString-Methode der JwtResponse-Klasse für Bürger und überprüft, ob die wichtigsten Werte im String enthalten sind
     */
    @Test
    void testToString_Buerger() {
        String toString = jwtResponseBuerger.toString();

        assertTrue(toString.contains("eyJhbGciOiJIUzI1NiIsInR5..."));
        assertTrue(toString.contains("test@example.com"));
    }

    /**
     * Testet die Funktionalität der Methode generateJwtTokenMitEmail mit einer leeren E-Mail und stellt sicher, dass ein Token generiert wird
     */
    @Test
    void testGenerateJwtTokenMitEmptyEmail() {
        String email = "";
        String token = jwtUtils.generateJwtTokenMitEmail(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    /**
     * Testet die Funktionalität der Methode generateJwtTokenMitEmail mit einer null-E-Mail und stellt sicher, dass ein Token generiert wird
     */
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


    /**
     * Testet die Getter- und Setter-Methoden der LoginDaten-Klasse
     */
    @Test
    void testGetterSetter() {
        LoginDaten loginDaten = new LoginDaten();
        loginDaten.setEmail("newuser@example.com");
        loginDaten.setPasswort("newpassword");

        assertEquals("newuser@example.com", loginDaten.getEmail(), "Die E-Mail-Adresse sollte korrekt gesetzt und zurückgegeben werden.");
        assertEquals("newpassword", loginDaten.getPasswort(), "Das Passwort sollte korrekt gesetzt und zurückgegeben werden.");
    }

    /**
     * Testet den Konstruktor der LoginDaten-Klasse und stellt sicher, dass die Werte korrekt gesetzt werden
     */
    @Test
    void testConstructor() {
        LoginDaten loginDaten = new LoginDaten("test@example.com", "testpassword");
        assertEquals("test@example.com", loginDaten.getEmail(), "Die E-Mail-Adresse sollte im Konstruktor gesetzt werden.");
        assertEquals("testpassword", loginDaten.getPasswort(), "Das Passwort sollte im Konstruktor gesetzt werden.");
    }

    /**
     * Testet die equals-Methode der LoginDaten-Klasse und überprüft verschiedene Vergleichsfälle
     */
    @Test
    void testEquals() {
        assertEquals(loginDaten1, loginDaten2, "Die Objekte sollten gleich sein.");
        assertNotEquals(loginDaten1, loginDaten3, "Die Objekte sollten ungleich sein.");
        assertNotEquals(null, loginDaten1, "Das Objekt sollte nicht gleich null sein.");
        assertNotEquals(loginDaten1, new Object(), "Das Objekt sollte nicht gleich einem anderen Typ sein.");
    }

    /**
     * Testet die hashCode-Methode der LoginDaten-Klasse und vergleicht die Hash-Werte für gleichwertige und unterschiedliche Objekte
     */
    @Test
    void testHashCode() {
        assertEquals(loginDaten1.hashCode(), loginDaten2.hashCode(), "Die Hash-Codes sollten gleich sein.");
        assertNotEquals(loginDaten1.hashCode(), loginDaten3.hashCode(), "Die Hash-Codes sollten ungleich sein.");
    }

    /**
     * Testet die toString-Methode der LoginDaten-Klasse und verifiziert, dass sie die richtigen Werte zurückgibt
     */
    @Test
    void testToString() {
        String expectedString = "LoginDaten(email=user@example.com, passwort=password123)";
        assertEquals(expectedString, loginDaten1.toString(), "Die toString() Methode sollte korrekt arbeiten.");
    }

    /**
     * Testet den Konstruktor und die Getter-Methoden der JwtResponse-Klasse
     */
    @Test
    void testConstructorAndGetters() {
        String token = "testToken123";
        Long id = 1L;
        String email = "test@example.com";
        Object[] roles = {"ROLE_USER", "ROLE_ADMIN"};

        JwtResponse jwtResponse = new JwtResponse(token, id, email, roles);

        assertEquals(token, jwtResponse.getAccessToken());
        assertEquals("Bearer", jwtResponse.getTokenType());
        assertEquals(id, jwtResponse.getId());
        assertEquals(email, jwtResponse.getEmail());
        assertEquals(roles, jwtResponse.getRole());
    }

    /**
     * Testet die setAccessToken-Methode der JwtResponse-Klasse und stellt sicher, dass das Token korrekt gesetzt wird
     */
    @Test
    void testSetAccessToken() {
        JwtResponse jwtResponse = new JwtResponse("initialToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});
        jwtResponse.setAccessToken("newToken123");
        assertEquals("newToken123", jwtResponse.getAccessToken());
    }

    /**
     * Testet die setTokenType-Methode der JwtResponse-Klasse und stellt sicher, dass der Typ korrekt gesetzt wird
     */
    @Test
    void testSetTokenType() {
        JwtResponse jwtResponse = new JwtResponse("testToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});
        jwtResponse.setTokenType("CustomType");
        assertEquals("CustomType", jwtResponse.getTokenType());
    }

    /**
     * Testet die setEmail-Methode der JwtResponse-Klasse und stellt sicher, dass die E-Mail korrekt gesetzt wird
     */
    @Test
    void testSetEmail() {
        JwtResponse jwtResponse = new JwtResponse("testToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});
        jwtResponse.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", jwtResponse.getEmail());
    }

    /**
     * Testet die setRole-Methode der JwtResponse-Klasse und stellt sicher, dass die Rolle korrekt gesetzt wird
     */
    @Test
    void testSetRole() {
        JwtResponse jwtResponse = new JwtResponse("testToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});
        Object[] newRoles = {"ROLE_ADMIN"};
        jwtResponse.setRole(newRoles);
        assertEquals(newRoles, jwtResponse.getRole());
    }

    /**
     * Testet die setId-Methode der JwtResponse-Klasse und stellt sicher, dass die ID korrekt gesetzt wird
     */
    @Test
    void testSetId() {
        JwtResponse jwtResponse = new JwtResponse("testToken", 1L, "test@example.com", new Object[]{"ROLE_USER"});
        jwtResponse.setId(42L);
        assertEquals(42L, jwtResponse.getId());
    }

    /**
     * Testet die Setter-Methoden der JwtResponse-Klasse und überprüft, ob alle Werte korrekt gesetzt werden
     */
    @Test
    void testSetters() {
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newToken = "newToken";
        String newType = "NewType";
        Long newId = 456L;
        String newEmail = "new@example.com";
        Object[] newRoles = {"ROLE_NEW"};

        jwtResponse.setAccessToken(newToken);
        jwtResponse.setTokenType(newType);
        jwtResponse.setId(newId);
        jwtResponse.setEmail(newEmail);
        jwtResponse.setRole(newRoles);

        assertEquals(newToken, jwtResponse.getAccessToken());
        assertEquals(newType, jwtResponse.getTokenType());
        assertEquals(newId, jwtResponse.getId());
        assertEquals(newEmail, jwtResponse.getEmail());
        assertArrayEquals(newRoles, (Object[]) jwtResponse.getRole());
    }

    /**
     * Testet den Default-Wert des Token-Typs in der JwtResponse-Klasse
     */
    @Test
    void testDefaultType() {
        String accessToken = "defaultToken";
        Long id = 789L;
        String email = "default@example.com";
        Object[] roles = {"ROLE_DEFAULT"};

        JwtResponse jwtResponse = new JwtResponse(accessToken, id, email, roles);

        assertEquals("Bearer", jwtResponse.getTokenType());
    }

}
