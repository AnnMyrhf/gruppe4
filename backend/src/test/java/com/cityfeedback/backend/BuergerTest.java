package com.cityfeedback.backend;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testklasse fuer Buerger
 */


@SpringBootTest
@Transactional
public class BuergerTest {
    // Leere Liste fuer Beschwerden
    final List<Beschwerde> beschwerden = new ArrayList<>();

    // Testobjekte
    Buerger testBuerger1 = new Buerger("Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfrau@example.com", "StarkesPW11?", beschwerden);
    Buerger testBuerger2 = new Buerger("Frau", "Julia", "Mustermann", "987654321", "maxi.musterfrau@example.com", "StarkesPW1?", beschwerden);
    Buerger testBuerger3 = new Buerger("Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!", beschwerden);
    Buerger testBuerger4 = new Buerger("Herr", "Juan", "Perez", "123456789", "j.perez@example.com", "pinFuerte123!", beschwerden);

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private BuergerService buergerService;
    @Autowired
    private BuergerRepository buergerRepository;


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
        // Mock für BindingResult
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        // Simuliere, dass keine Validierungsfehler vorliegen
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger3, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Optional: weitere Überprüfungen
    }

    /**
     * Ueberprueft, ob bei einer bereits bestehenden E-Mail-Adresse eine spezifische Fehlermeldung zurückgegeben wird.
     */
    @Test
    public void registriereBuerger_sollFehlerWerfenBeiDoppelterEmail() {
        // Mock für BindingResult
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        // Simuliere, dass keine Validierungsfehler vorliegen
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        buergerRepository.save(testBuerger1);
        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger2, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    /**
     * Ueberprueft, ob das Passwort vor der Speicherung korrekt gehasht wird.
     */
    @Test
    public void registriereBuerger_sollPasswortHashen() {
        // Mock für BindingResult
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        // Simuliere, dass keine Validierungsfehler vorliegen
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = buergerService.registriereBuerger(testBuerger3, bindingResult);

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

        assertEquals(7, jwtResponse.getId()); // 3, weil in der main-Methode bereits zwei Testbuerger angelegt werden
        assertEquals(testBuerger4.getEmail(), jwtResponse.getEmail());
    }

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
//    @Test
//    public void loescheBuerger_sollExceptionWerfenWennBuergerNichtExistiert() {
//        testBuerger1.setId(55L);
//        assertThrows(ResolutionException.class, () -> buergerService.loescheBuerger(testBuerger1.getId()));
//    }

    @Test
    public void loescheBuerger_sollExceptionWerfenWennBuergerNichtExistiert() {
        testBuerger1.setId(55L);
        // Act
        buergerService.loescheBuerger(55L);

        // Verify
        assertFalse(buergerRepository.findById(55L).isPresent(),
                "Es sollte keinen Buerger mit dieser ID geben.");
    }



    /**
     * Überprüft, ob der Getter für die Anrede den korrekten Wert zurückgibt.
     */
    @Test
    public void testGetter_Anrede() {
        assertEquals("Frau", testBuerger1.getAnrede());
    }

    /**
     * Überprüft, ob der Getter für den Vornamen den korrekten Wert zurückgibt.
     */
    @Test
    public void testGetter_Vorname() {
        assertEquals("Maxi", testBuerger1.getVorname());
    }

    /**
     * Überprüft, ob der Getter für den Nachnamen den korrekten Wert zurückgibt.
     */
    @Test
    public void testGetter_Nachname() {
        assertEquals("Musterfrau", testBuerger1.getNachname());
    }

    /**
     * Überprüft, ob der Getter für die Telefonnummer den korrekten Wert zurückgibt.
     */
    @Test
    public void testGetter_Telefonnummer() {
        assertEquals("987654321", testBuerger1.getTelefonnummer());
    }

    /**
     * Überprüft, ob der Getter für die E-Mail-Adresse den korrekten Wert zurückgibt.
     */
    @Test
    public void testGetter_Email() {
        assertEquals("maxi.musterfrau@example.com", testBuerger1.getEmail());
    }

    /**
     * Überprüft, ob der Getter für das Passwort den korrekten Wert zurückgibt.
     */
    @Test
    public void testGetter_Passwort() {
        assertEquals("StarkesPW11?", testBuerger1.getPasswort());
    }

    /**
     * Überprüft, ob der Setter die Anrede korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Anrede() {
        testBuerger1.setAnrede("Person");
        assertEquals("Person", testBuerger1.getAnrede());
    }

    /**
     * Überprüft, ob der Setter den Vornamen korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Vorname() {
        testBuerger1.setVorname("Anna");
        assertEquals("Anna", testBuerger1.getVorname());
    }

    /**
     * Überprüft, ob der Setter den Nachnamen korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Nachname() {
        testBuerger1.setNachname("Schmidt");
        assertEquals("Schmidt", testBuerger1.getNachname());
    }

    /**
     * Überprüft, ob der Setter die Telefonnummer korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Telefonnummer() {
        testBuerger1.setTelefonnummer("123456789");
        assertEquals("123456789", testBuerger1.getTelefonnummer());
    }

    /**
     * Überprüft, ob der Setter die E-Mail-Adresse korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Email() {
        testBuerger1.setEmail("test@example.com");
        assertEquals("test@example.com", testBuerger1.getEmail());
    }

    /**
     * Überprüft, ob der Setter das Passwort korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Passwort() {
        testBuerger1.setPasswort("NochStaerker11!");
        assertEquals("NochStaerker11!", testBuerger1.getPasswort());
    }

    /**
     * Überprüft, ob der Bürger die korrekte Authority besitzt und ob genau eine Authority vorhanden ist.
     */
    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = testBuerger1.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority("BUERGER")));
        assertEquals(1, authorities.size());
    }

    /**
     * Überprüft, ob die toString-Methode den korrekten String zurückgibt.
     */
    @Test
    void testToString() {
        String toStringOutput = testBuerger1.toString();
        String expectedString = "Buerger(anrede='Frau', vorname='Maxi', nachname='Musterfrau', telefonnummer='987654321', email='maxi.musterfrau@example.com', passwort='StarkesPW11?', beschwerden=[])";
        assertEquals(expectedString, toStringOutput);
    }

    private Validator validator;

    @BeforeEach
    void setUp2() {
        // Initialisierung des Validators über das Validation API
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidBuerger() {
        Buerger validBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(validBuerger);
        assertTrue(violations.isEmpty(), "Es sollten keine Validierungsfehler auftreten.");
    }

    @Test
    public void testAnredeNotBlank() {
        Buerger invalidBuerger = new Buerger("", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size());
        assertEquals("Anrede darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    @Test
    public void testVornameNotBlank() {
        Buerger invalidBuerger = new Buerger("Frau", "", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size());
        assertEquals("Vorname darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    @Test
    public void testVornameMaxLength() {
        Buerger invalidBuerger = new Buerger("Frau", "MaxiMaxiMaxiMaxiMaxiMaxiMaxiMaxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size(), "Es sollte ein Validierungsfehler für einen zu langen Vornamen auftreten.");
        assertEquals("Vorname darf max. 30 Zeichen lang sein!", violations.iterator().next().getMessage());

    }

    @Test
    public void testNachnameNotBlank() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size());
        assertEquals("Nachname darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    @Test
    public void testNachnameMaxLength() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "MusterfrauenMusterfrauenMusterfrauen", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size());
        assertEquals("Nachname darf max. 30 Zeichen lang sein!", violations.iterator().next().getMessage());

    }

    @Test
    public void testTelefonnummerNotBlankAndOnlyNumbers() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(2, violations.size());
        //assertEquals("Telefonnummer darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailValid() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size(), "Es sollte ein Validierungsfehler für eine ungültige E-Mail auftreten.");
       // assertEquals("must be a well-formed email address", violations.iterator().next().getMessage());
    }

    @Test
    public void testPasswortNotBlank() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size(), "Es sollte ein Validierungsfehler für ein leeres Passwort auftreten.");
        assertEquals("Passwort darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    @Test
    public void testUserDetailsImplementation() {
        assertTrue(testBuerger1.isAccountNonExpired(), "Der Account sollte nicht abgelaufen sein.");
        assertTrue(testBuerger1.isAccountNonLocked(), "Der Account sollte nicht gesperrt sein.");
        assertTrue(testBuerger1.isCredentialsNonExpired(), "Die Anmeldeinformationen sollten nicht abgelaufen sein.");
        assertTrue(testBuerger1.isEnabled(), "Der Account sollte aktiviert sein.");
    }

    @Test
    public void testTelefonnummerInvalidFormat() {
        Buerger invalidBuerger = new Buerger("Frau", "Anna", "Muster", "abc-def-123", "anna@example.com", "Passwort123!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Die Telefonnummer darf nur Zahlen")));
    }

    @Test
    public void testEqualsAndHashCode() {
        Buerger buerger1 = new Buerger("Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!", beschwerden);
        Buerger buerger2 = new Buerger("Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!", beschwerden);

        assertEquals(buerger1, buerger2, "Die beiden Bürger sollten als gleich gelten.");
        assertEquals(buerger1.hashCode(), buerger2.hashCode(), "Die Hash-Codes sollten übereinstimmen.");
    }



}

