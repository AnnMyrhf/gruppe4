package com.cityfeedback.backend;


import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import com.cityfeedback.backend.security.JwtResponse;
import com.cityfeedback.backend.security.valueobjects.LoginDaten;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.module.ResolutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MitarbeiterTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    // Testobjekte
    Mitarbeiter testMitarbeiter1 = new Mitarbeiter(1234L, "Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef");
    Mitarbeiter testMitarbeiter2 = new Mitarbeiter(1234L, "Herr", "Max", "Mustermann", "123456", "Hallo@web.com", "StarkesPW11!", "Verwaltung", "Assistenz");
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

    @Test
    public void testGetter_Anrede() {
        assertEquals("Frau", testMitarbeiter1.getAnrede());
    }

    @Test
    public void testGetter_Vorname() {
        assertEquals("Anna", testMitarbeiter1.getVorname());
    }

    @Test
    public void testGetter_Nachname() {
        assertEquals("Müller", testMitarbeiter1.getNachname());
    }

    @Test
    public void testGetter_Telefonnummer() {
        assertEquals("123456", testMitarbeiter1.getTelefonnummer());
    }

    @Test
    public void testGetter_Email() {
        assertEquals("Hallo@web.com", testMitarbeiter1.getEmail());
    }

    @Test
    public void testGetter_Passwort() {
        assertEquals("Hallo12!", testMitarbeiter1.getPasswort());
    }

    @Test
    public void testGetter_Abteilung() {
        assertEquals("Verwaltung", testMitarbeiter1.getAbteilung());
    }

    @Test
    public void testGetter_Position() {
        assertEquals("Chef", testMitarbeiter1.getPosition());
    }

    @Test
    public void testSetter_Anrede() {
        testMitarbeiter1.setAnrede("Person");
        assertEquals("Person", testMitarbeiter1.getAnrede());
    }

    @Test
    public void testSetter_Vorname() {
        testMitarbeiter1.setVorname("Maxi");
        assertEquals("Maxi", testMitarbeiter1.getVorname());
    }

    @Test
    public void testSetter_Nachname() {
        testMitarbeiter1.setNachname("Schmidt");
        assertEquals("Schmidt", testMitarbeiter1.getNachname());
    }

    @Test
    public void testSetter_Telefonnummer() {
        testMitarbeiter1.setTelefonnummer("987654321");
        assertEquals("987654321", testMitarbeiter1.getTelefonnummer());
    }

    @Test
    public void testSetter_Email() {
        testMitarbeiter1.setEmail("test@test.de");
        assertEquals("test@test.de", testMitarbeiter1.getEmail());
    }

    @Test
    public void testSetter_Passwort() {
        testMitarbeiter1.setPasswort("EchtStark11!");
        assertEquals("EchtStark11!", testMitarbeiter1.getPasswort());
    }

    @Test
    public void testSetter_Abteilung() {
        testMitarbeiter1.setAbteilung("Beschwerdemanagement");
        assertEquals("Beschwerdemanagement", testMitarbeiter1.getAbteilung());
    }

    @Test
    public void testSetter_Position() {
        testMitarbeiter1.setPosition("Teamleitung");
        assertEquals("Teamleitung", testMitarbeiter1.getPosition());
    }
/*
    @Test
    public void testValidation_VornameZuLang() {
        testMitarbeiter1.setVorname("Anna".repeat(31)); // 31 Zeichen
        ResponseEntity<?> response = mitarbeiterService.registriereMitarbeiter(testMitarbeiter1);
       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

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
      //  assertEquals(3, jwtResponse.getId()); // 2, weil in der main-Methode bereits zwei Testbuerger angelegt werden
        assertEquals(testMitarbeiter1.getEmail(), jwtResponse.getEmail());
    }

    @Test
    void anmeldenMitarbeiter_FalscheEmail() {
        mitarbeiterRepository.save(testMitarbeiter1);
        LoginDaten loginDaten = new LoginDaten("falsche@email.de", testMitarbeiter1.getPasswort());

        ResponseEntity<?> response = mitarbeiterService.anmeldenMitarbeiter(loginDaten);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

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

        assertThrows(ResolutionException.class, () -> mitarbeiterService.loescheMitarbeiter(testMitarbeiter1.getId()));
    }
}
