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
class BuergerTest {
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
    void registriereBuerger_sollErfolgreichSein() {
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
    void registriereBuerger_sollFehlerWerfenBeiDoppelterEmail() {
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
    void registriereBuerger_sollPasswortHashen() {
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
     * Ueberprueft, ob ein existierender Buerger vollständig aus der Datenbank geloescht wird.
     */
    @Test
    void loescheBuerger_sollErfolgreichSein() {

        Buerger buergerLoeschen = buergerRepository.save(testBuerger4);

        ResponseEntity<?> response = buergerService.loescheBuerger(buergerLoeschen.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(buergerRepository.existsByEmail(buergerLoeschen.getEmail()));

    }

    /**
     * Ueberprueft, ob beim Versuch, einen nicht existierenden Buerger zu loeschen, eine ResolutionException geworfen wird.
     */
    @Test
    void loescheBuerger_sollExceptionWerfenWennBuergerNichtExistiert() {
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
    void testGetter_Anrede() {
        assertEquals("Frau", testBuerger1.getAnrede());
    }

    /**
     * Überprüft, ob der Getter für den Vornamen den korrekten Wert zurückgibt.
     */
    @Test
    void testGetter_Vorname() {
        assertEquals("Maxi", testBuerger1.getVorname());
    }

    /**
     * Überprüft, ob der Getter für den Nachnamen den korrekten Wert zurückgibt.
     */
    @Test
    void testGetter_Nachname() {
        assertEquals("Musterfrau", testBuerger1.getNachname());
    }

    /**
     * Überprüft, ob der Getter für die Telefonnummer den korrekten Wert zurückgibt.
     */
    @Test
    void testGetter_Telefonnummer() {
        assertEquals("987654321", testBuerger1.getTelefonnummer());
    }

    /**
     * Überprüft, ob der Getter für die E-Mail-Adresse den korrekten Wert zurückgibt.
     */
    @Test
    void testGetter_Email() {
        assertEquals("maxi.musterfrau@example.com", testBuerger1.getEmail());
    }

    /**
     * Überprüft, ob der Getter für das Passwort den korrekten Wert zurückgibt.
     */
    @Test
    void testGetter_Passwort() {
        assertEquals("StarkesPW11?", testBuerger1.getPasswort());
    }

    /**
     * Überprüft, ob der Setter die Anrede korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    void testSetter_Anrede() {
        testBuerger1.setAnrede("Person");
        assertEquals("Person", testBuerger1.getAnrede());
    }

    /**
     * Überprüft, ob der Setter den Vornamen korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    void testSetter_Vorname() {
        testBuerger1.setVorname("Anna");
        assertEquals("Anna", testBuerger1.getVorname());
    }

    /**
     * Überprüft, ob der Setter den Nachnamen korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    void testSetter_Nachname() {
        testBuerger1.setNachname("Schmidt");
        assertEquals("Schmidt", testBuerger1.getNachname());
    }

    /**
     * Überprüft, ob der Setter die Telefonnummer korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    void testSetter_Telefonnummer() {
        testBuerger1.setTelefonnummer("123456789");
        assertEquals("123456789", testBuerger1.getTelefonnummer());
    }

    /**
     * Überprüft, ob der Setter die E-Mail-Adresse korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    void testSetter_Email() {
        testBuerger1.setEmail("test@example.com");
        assertEquals("test@example.com", testBuerger1.getEmail());
    }

    /**
     * Überprüft, ob der Setter das Passwort korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    void testSetter_Passwort() {
        testBuerger1.setPasswort("NochStaerker11!");
        assertEquals("NochStaerker11!", testBuerger1.getPasswort());
    }

    /**
     * Überprüft, ob der Bürger die korrekte Authority besitzt und ob genau eine Authority vorhanden ist.
     */
    @Test
    void testGetAuthorities() {
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

    /**
     * Überprüft, ob ein gültiges Bürgerobjekt keine Validierungsfehler aufweist.
     */
    @Test
    void testValidBuerger() {
        Buerger validBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(validBuerger);
        assertTrue(violations.isEmpty(), "Es sollten keine Validierungsfehler auftreten.");
    }

    /**
     * Überprüft, dass die Anrede nicht leer sein darf.
     */
    @Test
    void testAnredeNotBlank() {
        Buerger invalidBuerger = new Buerger("", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size());
        assertEquals("Anrede darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    /**
     * Überprüft, dass der Vorname nicht leer sein darf.
     */
    @Test
    void testVornameNotBlank() {
        Buerger invalidBuerger = new Buerger("Frau", "", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size());
        assertEquals("Vorname darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    /**
     * Überprüft, dass der Vorname die maximale Länge von 30 Zeichen nicht überschreitet.
     */
    @Test
    void testVornameMaxLength() {
        Buerger invalidBuerger = new Buerger("Frau", "MaxiMaxiMaxiMaxiMaxiMaxiMaxiMaxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size(), "Es sollte ein Validierungsfehler für einen zu langen Vornamen auftreten.");
        assertEquals("Vorname darf max. 30 Zeichen lang sein!", violations.iterator().next().getMessage());
    }

    /**
     * Überprüft, dass der Nachname nicht leer sein darf.
     */
    @Test
    void testNachnameNotBlank() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size());
        assertEquals("Nachname darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    /**
     * Überprüft, dass der Nachname die maximale Länge von 30 Zeichen nicht überschreitet.
     */
    @Test
    void testNachnameMaxLength() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "MusterfrauenMusterfrauenMusterfrauen", "123456789", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size());
        assertEquals("Nachname darf max. 30 Zeichen lang sein!", violations.iterator().next().getMessage());
    }

    /**
     * Überprüft, dass die Telefonnummer nicht leer ist und nur Zahlen enthält.
     */
    @Test
    void testTelefonnummerNotBlankAndOnlyNumbers() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "", "maxi@example.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(2, violations.size());
    }

    /**
     * Überprüft, dass die E-Mail-Adresse im gültigen Format vorliegt.
     */
    @Test
    void testEmailValid() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi.com", "Passwort1!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size(), "Es sollte ein Validierungsfehler für eine ungültige E-Mail auftreten.");
    }

    /**
     * Überprüft, dass das Passwort nicht leer sein darf.
     */
    @Test
    void testPasswortNotBlank() {
        Buerger invalidBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertEquals(1, violations.size(), "Es sollte ein Validierungsfehler für ein leeres Passwort auftreten.");
        assertEquals("Passwort darf nicht leer sein!", violations.iterator().next().getMessage());
    }

    /**
     * Überprüft die Implementierung der Benutzerkontodetails.
     */
    @Test
    void testUserDetailsImplementation() {
        assertTrue(testBuerger1.isAccountNonExpired(), "Der Account sollte nicht abgelaufen sein.");
        assertTrue(testBuerger1.isAccountNonLocked(), "Der Account sollte nicht gesperrt sein.");
        assertTrue(testBuerger1.isCredentialsNonExpired(), "Die Anmeldeinformationen sollten nicht abgelaufen sein.");
        assertTrue(testBuerger1.isEnabled(), "Der Account sollte aktiviert sein.");
    }

    /**
     * Überprüft, ob ungültige Telefonnummern erkannt werden.
     */
    @Test
    void testTelefonnummerInvalidFormat() {
        Buerger invalidBuerger = new Buerger("Frau", "Anna", "Muster", "abc-def-123", "anna@example.com", "Passwort123!", null);

        Set<ConstraintViolation<Buerger>> violations = validator.validate(invalidBuerger);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Die Telefonnummer darf nur Zahlen")));
    }

    /**
     * Überprüft, ob equals und hashCode für gleiche Bürger korrekt funktionieren.
     */
    @Test
    void testEqualsAndHashCode() {
        Buerger buerger1 = new Buerger("Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!", beschwerden);
        Buerger buerger2 = new Buerger("Herr", "Juan", "Perez", "123456789", "juan.perez@example.com", "pinFuerte123!", beschwerden);

        assertEquals(buerger1, buerger2, "Die beiden Bürger sollten als gleich gelten.");
        assertEquals(buerger1.hashCode(), buerger2.hashCode(), "Die Hash-Codes sollten übereinstimmen.");
    }

    /**
     * Überprüft, ob equals für gleiche Attribute korrekt funktioniert.
     */
    @Test
    void testEquals_SameAttributes() {
        Buerger buerger1 = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", beschwerden);
        Buerger buerger2 = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", beschwerden);

        assertEquals(buerger1, buerger2);
    }

    /**
     * Überprüft, ob equals für unterschiedliche Attribute korrekt funktioniert.
     */
    @Test
    void testEquals_DifferentAttributes() {
        Buerger buerger1 = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", beschwerden);
        Buerger buerger2 = new Buerger("Herr", "Max", "Mustermann", "987654321", "max@example.com", "Passwort2!", beschwerden);

        assertNotEquals(buerger1, buerger2);
    }

    /**
     * Überprüft, ob hashCode für gleiche Attribute korrekt funktioniert.
     */
    @Test
    void testHashCode_SameAttributes() {
        Buerger buerger1 = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", beschwerden);
        Buerger buerger2 = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", beschwerden);

        assertEquals(buerger1.hashCode(), buerger2.hashCode());
    }

    /**
     * Überprüft, ob hashCode für unterschiedliche Attribute korrekt funktioniert.
     */
    @Test
    void testHashCode_DifferentAttributes() {
        Buerger buerger1 = new Buerger("Frau", "Maxi", "Musterfrau", "123456789", "maxi@example.com", "Passwort1!", beschwerden);
        Buerger buerger2 = new Buerger("Herr", "Max", "Mustermann", "987654321", "max@example.com", "Passwort2!", beschwerden);

        assertNotEquals(buerger1.hashCode(), buerger2.hashCode());
    }

}

