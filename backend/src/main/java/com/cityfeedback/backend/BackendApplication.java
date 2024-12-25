package com.cityfeedback.backend;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Hauptklasse, welche die Anwendung ausfuehrt
 */
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MitarbeiterRepository mitarbeiterRepository, BuergerRepository buergerRepository, BeschwerdeRepository beschwerdeRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Leere Liste fuer Beschwerden
            final List<Beschwerde> beschwerden = new ArrayList<>();
            BindingResult bindingResult;

            // Testobjekte Bürger
            Buerger testBuerger1 = new Buerger("Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "StarkesPW11?", beschwerden);
            testBuerger1.setPasswort(passwordEncoder.encode(testBuerger1.getPasswort()));
            buergerRepository.save(testBuerger1);

            Buerger testBuerger2 = new Buerger("Frau", "Peter", "Neu", "987654321", "testbuerger@test.com", "StarkesPW11?", beschwerden);
            testBuerger2.setPasswort(passwordEncoder.encode(testBuerger2.getPasswort()));
            buergerRepository.save(testBuerger2);


            // Testobjekte Beschwerde
            Anhang anhang = new Anhang("schlagloch.jpg", "image/jpeg", 20480L, null);
            Beschwerde testBeschwerde1 = new Beschwerde("Straßenbau", "Schlaglöcher auf Hauptstraße", "Auf der Hauptstraße befinden sich mehrere tiefe Schlaglöcher, die eine Gefahr für den Straßenverkehr darstellen. Besonders Radfahrer sind gefährdet.", anhang, testBuerger1);
            beschwerdeRepository.save(testBeschwerde1);

            Beschwerde testBeschwerde2 = new Beschwerde("Müllentsorgung", "Überfüllte Mülltonnen", "Die Mülltonnen vor unserem Wohnblock werden seit drei Wochen nicht geleert. Es stinkt und zieht Ungeziefer an.", anhang, testBuerger1);
            beschwerdeRepository.save(testBeschwerde2);

            Beschwerde testBeschwerde3 = new Beschwerde("Lärmbelästigung", "Nächtlicher Baulärm", "Seit zwei Wochen finden nachts Bauarbeiten auf der Baustelle in der Mustergasse statt. Es ist unmöglich zu schlafen.", anhang, testBuerger1);
            beschwerdeRepository.save(testBeschwerde3);

            Beschwerde testBeschwerde4 = new Beschwerde("Straßenbeleuchtung", "Defekte Straßenlaternen", "In unserer Straße funktionieren drei Straßenlaternen nicht mehr. Es ist nachts sehr dunkel und unsicher.", anhang, testBuerger2);
            beschwerdeRepository.save(testBeschwerde4);

            Beschwerde testBeschwerde5 = new Beschwerde("Parkprobleme", "Falschparker auf Gehwegen", "Vor unserem Haus parken regelmäßig Autos auf dem Gehweg, was besonders für Kinder und ältere Menschen problematisch ist.", anhang, testBuerger2);
            beschwerdeRepository.save(testBeschwerde5);

            // Testobjekte Mitarbeiter
            Mitarbeiter testMitarbeiter1 = new Mitarbeiter("Frau", "Anna", "Müller", "123456", "mitarbeiter@test.com", "StarkesPW11?");
            testMitarbeiter1.setPasswort(passwordEncoder.encode(testMitarbeiter1.getPasswort()));
            mitarbeiterRepository.save(testMitarbeiter1);

            Mitarbeiter testMitarbeiter2 = new Mitarbeiter("Frau", "Anna", "Müller", "123456", "mitarbeiter2@test.com", "StarkesPW11?");
            testMitarbeiter2.setPasswort(passwordEncoder.encode(testMitarbeiter2.getPasswort()));
            mitarbeiterRepository.save(testMitarbeiter2);

        };
    }

}
