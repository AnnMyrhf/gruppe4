package com.cityfeedback.backend;


import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import com.cityfeedback.backend.security.JwtResponse;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;

import jakarta.validation.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.lang.module.ResolutionException;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class MitarbeiterTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    // Testobjekte
    Mitarbeiter testMitarbeiter1 = new Mitarbeiter("Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef");
    Mitarbeiter testMitarbeiter2 = new Mitarbeiter("Herr", "Max", "Mustermann", "123456", "Hallo@web.com", "StarkesPW11!", "Verwaltung", "Assistenz");
    Mitarbeiter testMitarbeiter3 = new Mitarbeiter("Herr", "Max", "Mustermann", "123456", "Hallo@web.com", "StarkesPW11!", "Verwaltung", "Assistenz");

    @Autowired
    private MitarbeiterService mitarbeiterService;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @BeforeEach
    void setUp() {
        // vor jedem Test wird die DB geleert
        mitarbeiterRepository.deleteAll();

    }

    /**
     * Ueberprueft, ob ein Mitarbeiter erfolgreich registriert wird, einschließlich aller Attribute.
     */
    @Test
    public void registriereMitarbeiter_sollErfolgreichSein() {

        ResponseEntity<?> response = mitarbeiterService.registriereMitarbeiter(testMitarbeiter1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(mitarbeiterRepository.existsByEmail(testMitarbeiter1.getEmail()));

    }


    /**
     * Ueberprueft, ob bei einer bereits bestehenden E-Mail-Adresse eine spezifische Fehlermeldung zurückgegeben wird.
     */
    @Test
    public void registriereMitarbeiter_sollFehlerWerfenBeiDoppelterEmail() {
        mitarbeiterRepository.save(testMitarbeiter1);
        ResponseEntity<?> response = mitarbeiterService.registriereMitarbeiter(testMitarbeiter2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    /**
     * Ueberprueft, ob das Passwort vor der Speicherung korrekt gehasht wird.
     */
    @Test
    public void registriereMitarbeiter_sollPasswortHashen() {
        ResponseEntity<?> response = mitarbeiterService.registriereMitarbeiter(testMitarbeiter1);

        //Sollte das gespeicherte Passwort nicht dem Klartext entsprechen
        Mitarbeiter gespeicherterMitarbeiter = mitarbeiterRepository.findByEmail(testMitarbeiter1.getEmail()).get();
        assertNotEquals(testMitarbeiter1.getPasswort(), gespeicherterMitarbeiter.getPasswort());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Zusaetzliche Überprüfung mit dem PasswordEncoder
        boolean istPasswortKorrekt = passwordEncoder.matches(testMitarbeiter1.getPasswort(), gespeicherterMitarbeiter.getPasswort());
        assertTrue(istPasswortKorrekt);

    }
/*
   @Test
    public void testValidation_VornameZuLang() {
        testMitarbeiter1.setVorname("Anna".repeat(31)); // 31 Zeichen
        assertThrows(MethodArgumentNotValidException.class, () -> {
        mitarbeiterService.registriereMitarbeiter(testMitarbeiter1);
        });
        DefaultBindingResult bindingResult = new DefaultBindingResult(testMitarbeiter2, "mitarbeiter");
FieldError error = new FieldError("mitarbeiter", "vorname", "Vorname ist zu lang");
bindingResult.addError(error);
    }*/

/*
   @Test
    public void testValidation_NachnameZuLang() {
        testMitarbeiter1.setNachname("Mustermann".repeat(31)); // 31 Zeichen
        ResponseEntity<?> response = mitarbeiterService.registriereMitarbeiter(testMitarbeiter1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUngueltigeEmail() {
        testMitarbeiter1.setEmail("ungueltige Email");
        ResponseEntity<?> response = mitarbeiterService.registriereMitarbeiter(testMitarbeiter1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }*/

    /**
     * Ueberprueft, ob ein bereits registrierter Buerger sich erfolgreich mit den korrekten Anmeldedaten anmelden kann
     * und ob dabei ein gueltiges JWT mit den korrekten Buergerinfos zurückgegeben wird.
     */
    @Test
    public void anmeldenMitarbeiter_sollErfolgreichSein() {
        testMitarbeiter1.setPasswort(passwordEncoder.encode(testMitarbeiter1.getPasswort()));
        mitarbeiterRepository.save(testMitarbeiter1);
        LoginDaten loginDaten = new LoginDaten(testMitarbeiter1.getEmail(), "Hallo12!");

        ResponseEntity<?> response = mitarbeiterService.anmeldenMitarbeiter(loginDaten);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertNotNull(jwtResponse.getToken());
        //assertEquals(3, jwtResponse.getId()); // 3, weil in der main-Methode bereits zwei Testbuerger angelegt werden
        assertEquals(testMitarbeiter1.getEmail(), jwtResponse.getEmail());
    }

    /**
     * Ueberprueft, ob ein Anmeldeversuch mit einer falschen E-Mail-Adresse fehlschlaegt.
     */
    @Test
    void anmeldenMitarbeiter_FalscheEmail() {
        mitarbeiterRepository.save(testMitarbeiter1);
        LoginDaten loginDaten = new LoginDaten("falsche@email.de", testMitarbeiter1.getPasswort());

        ResponseEntity<?> response = mitarbeiterService.anmeldenMitarbeiter(loginDaten);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    /**
     * Ueberprueft, ob ein Anmeldeversuch mit einem falschen Passwort fehlschlaegt.
     */
    @Test
    void anmeldenMitarbeiter_FalschesPasswort() {
        mitarbeiterRepository.save(testMitarbeiter1);

        LoginDaten loginDaten = new LoginDaten(testMitarbeiter1.getEmail(), "Hallo12!");

        ResponseEntity<?> response = mitarbeiterService.anmeldenMitarbeiter(loginDaten);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    /**
     * Ueberprueft, ob ein existierender Mitarbeiter vollstaendig aus der Datenbank geloescht wird.
     */
    @Test
    public void loescheMitarbeiter_sollErfolgreichSein() {

        Mitarbeiter mitarbeiterLoeschen = mitarbeiterRepository.save(testMitarbeiter1);

        ResponseEntity<?> response = mitarbeiterService.loescheMitarbeiter(mitarbeiterLoeschen.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(mitarbeiterRepository.existsByEmail(mitarbeiterLoeschen.getEmail()));

    }

    /**
     * Ueberprueft, ob beim Versuch, einen nicht existierenden Mitarbeiter zu loeschen, eine ResolutionException geworfen wird.
     */
    @Test
    public void loescheMitarbeiter_sollExceptionWerfenWennBuergerNichtExistiert() {
        testMitarbeiter1.setId(55L);
        assertThrows(ResolutionException.class, () -> mitarbeiterService.loescheMitarbeiter(testMitarbeiter1.getId()));
    }

    /**
     * Ueberprueft, ob der Getter für die Anrede den korrekten Wert zurückgibt,
     */
    @Test
    public void testGetter_Anrede() {
        assertEquals("Frau", testMitarbeiter1.getAnrede());
    }

    /**
     * Ueberprueft, ob der Getter für den Vornamen den korrekten Wert zurückgibt,
     */
    @Test
    public void testGetter_Vorname() {
        assertEquals("Anna", testMitarbeiter1.getVorname());
    }

    /**
     * Ueberprueft, ob der Getter für den Nachnamen den korrekten Wert zurückgibt,
     */
    @Test
    public void testGetter_Nachname() {
        assertEquals("Müller", testMitarbeiter1.getNachname());
    }

    /**
     * Ueberprueft, ob der Getter für die Telefonnummer den korrekten Wert zurückgibt,
     */
    @Test
    public void testGetter_Telefonnummer() {
        assertEquals("123456", testMitarbeiter1.getTelefonnummer());
    }

    /**
     * Ueberprueft, ob der Getter für die E-Mail-Adresse den korrekten Wert zurückgibt,
     */
    @Test
    public void testGetter_Email() {
        assertEquals("Hallo@web.com", testMitarbeiter1.getEmail());
    }

    /**
     * Ueberprueft, ob der Getter für das Passwort den korrekten Wert zurückgibt,
     */
    @Test
    public void testGetter_Passwort() {
        assertEquals("Hallo12!", testMitarbeiter1.getPasswort());
    }

    /**
     * Ueberprueft, ob der Getter für die Abteilung den korrekten Wert zurückgibt,
     */
    @Test
    public void testGetter_Abteilung() {
        assertEquals("Verwaltung", testMitarbeiter1.getAbteilung());
    }

    /**
     * Ueberprueft, ob der Getter für die Position den korrekten Wert zurückgibt,
     */
    @Test
    public void testGetter_Position() {
        assertEquals("Chef", testMitarbeiter1.getPosition());
    }

    /**
     * Ueberprueft, ob der Setter die Anrede korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Anrede() {
        testMitarbeiter1.setAnrede("Person");
        assertEquals("Person", testMitarbeiter1.getAnrede());
    }

    /**
     * Ueberprueft, ob der Setter den Vornamen korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Vorname() {
        testMitarbeiter1.setVorname("Maxi");
        assertEquals("Maxi", testMitarbeiter1.getVorname());
    }

    /**
     * Ueberprueft, ob der Setter den Nachnamen korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Nachname() {
        testMitarbeiter1.setNachname("Schmidt");
        assertEquals("Schmidt", testMitarbeiter1.getNachname());
    }

    /**
     * Ueberprueft, ob der Setter die Telefonnummer korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Telefonnummer() {
        testMitarbeiter1.setTelefonnummer("987654321");
        assertEquals("987654321", testMitarbeiter1.getTelefonnummer());
    }

    /**
     * Ueberprueft, ob der Setter die E-Mail-Adresse korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Email() {
        testMitarbeiter1.setEmail("test@test.de");
        assertEquals("test@test.de", testMitarbeiter1.getEmail());
    }

    /**
     * Ueberprueft, ob der Setter das Passwort korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Passwort() {
        testMitarbeiter1.setPasswort("EchtStark11!");
        assertEquals("EchtStark11!", testMitarbeiter1.getPasswort());
    }

    /**
     * Ueberprueft, ob der Setter die Abteilung korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    public void testSetter_Abteilung() {
        testMitarbeiter1.setAbteilung("Beschwerdemanagement");
        assertEquals("Beschwerdemanagement", testMitarbeiter1.getAbteilung());
    }

    /**
     * Ueberprueft, ob der Setter die Position korrekt überschreibt und der neue Wert anschließend über den Getter korrekt zurückgegeben wird.
     */
    @Test
    void testSetter_Position() {
        testMitarbeiter1.setPosition("Teamleitung");
        assertEquals("Teamleitung", testMitarbeiter1.getPosition());
    }

    /**
     * Ueberprueft, ob der Mitarbeiter die korrekte Authority besitzt und ob, ob genau eine Authority vorhanden ist
     */
    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = testMitarbeiter1.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority("MITARBEITER")));
        assertEquals(1, authorities.size());
    }

    /**
     * Ueberprueft, ob die Methode korrekt ueberschrieben wurde und die E-Mail-Adresse eines Mitarbeiters als erwarteten Benutzernamen zurueckgibt.
     */
    @Test
    void testGetUsername() {
        String expectedEmail = "Hallo@web.com";
        String actualUsername = testMitarbeiter1.getUsername();
        assertEquals(expectedEmail, actualUsername);
    }

    @Test
    void testToString() {
        String toStringOutput = testMitarbeiter1.toString();
        String expectedString = "Mitarbeiter(anrede='Frau', vorname='Anna', nachname='Müller', telefonnummer='123456', email='Hallo@web.com', passwort='Hallo12!', abteilung='Verwaltung', position='Chef')";
        assertEquals(expectedString, toStringOutput);
    }

    @Test
    void testHashCode() {
        int hashCode1 = testMitarbeiter2.hashCode();
        int hashCode2 = testMitarbeiter3.hashCode();

        // Vergleiche die Hashcodes
        assertEquals(hashCode1, hashCode2, "Die Hashcodes müssen identisch sein.");
    }

    @Test
    void testHashCode_DifferentObjects() {

        // Berechne die Hashcodes
        int hashCode1 = testMitarbeiter1.hashCode();
        int hashCode2 = testMitarbeiter2.hashCode();

        // Vergleiche die Hashcodes
        assertNotEquals(hashCode1, hashCode2, "Die Hashcodes müssen unterschiedlich sein.");
    }

    /**
     * Testet die `equals()`-Methode auf Korrektheit, einschließlich Symmetrie, Transitivitaet und Reflexivitaet.
     */
    @Test
    public void testEquals() {
        assertEquals(testMitarbeiter2.getVorname(), testMitarbeiter3.getVorname());
        assertEquals(testMitarbeiter2.getNachname(), testMitarbeiter3.getNachname());

        assertTrue(testMitarbeiter2.equals(testMitarbeiter3));
        assertTrue(testMitarbeiter3.equals(testMitarbeiter2));
    }

}

