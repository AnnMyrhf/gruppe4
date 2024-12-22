package com.cityfeedback.backend;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.domain.valueobjects.Name;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService;
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
    CommandLineRunner commandLineRunner(MitarbeiterRepository mitarbeiterRepository, BuergerRepository buergerRepository, BeschwerdeRepository beschwerdeRepository, MitarbeiterService mitarbeiterService, PasswordEncoder passwordEncoder){
        return args -> {
            // Leere Liste fuer Beschwerden
            final List<Beschwerde> beschwerden = new ArrayList<>();
            BindingResult bindingResult;

            // Testobjekte
            Buerger testBuerger1 = new Buerger(1L, "Frau", new Name( "Maxi", "Musterfrau"), "987654321", "maxi.musterfau@example.com", "StarkesPW11?", beschwerden);
            testBuerger1.setPasswort(passwordEncoder.encode(testBuerger1.getPasswort()));
            buergerRepository.save(testBuerger1);

            Buerger testBuerger2 = new Buerger(2L, "Frau", new Name("Peter", "Neu"), "987654321", "PN@example.com", "StarkesPW11?", beschwerden);
            testBuerger2.setPasswort(passwordEncoder.encode(testBuerger2.getPasswort()));
            buergerRepository.save(testBuerger2);

            mitarbeiterRepository.save(new Mitarbeiter(1L,"Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef"));

            // Testobjekte
            Beschwerde testBeschwerde1 = new Beschwerde("Straßenbau", "Schlaglöcher auf Hauptstraße", "Auf der Hauptstraße befinden sich mehrere tiefe Schlaglöcher, die eine Gefahr für den Straßenverkehr darstellen. Besonders Radfahrer sind gefährdet.", new Anhang("schlagloch.jpg", "image/jpeg", 20480L, "Bytes"), testBuerger1);
            beschwerdeRepository.save(testBeschwerde1);
            testBeschwerde1.setStatus(Status.IN_BEARBEITUNG);
            beschwerdeRepository.save(testBeschwerde1);

            Beschwerde testBeschwerde2 = new Beschwerde("Müllentsorgung", "Überfüllte Mülltonnen", "Die Mülltonnen vor unserem Wohnblock werden seit drei Wochen nicht geleert. Es stinkt und zieht Ungeziefer an.", new Anhang("muell.jpg", "image/jpeg", 15360L, "Bytes"), testBuerger1);
            beschwerdeRepository.save(testBeschwerde2);

            Beschwerde testBeschwerde3 = new Beschwerde("Lärmbelästigung", "Nächtlicher Baulärm", "Seit zwei Wochen finden nachts Bauarbeiten auf der Baustelle in der Mustergasse statt. Es ist unmöglich zu schlafen.", new Anhang("baulaerm.pdf", "application/pdf", 10240L, "Bytes"), testBuerger1);
            beschwerdeRepository.save(testBeschwerde3);

            Beschwerde testBeschwerde4 = new Beschwerde("Straßenbeleuchtung", "Defekte Straßenlaternen", "In unserer Straße funktionieren drei Straßenlaternen nicht mehr. Es ist nachts sehr dunkel und unsicher.", new Anhang("strassenlaterne.jpg", "image/jpeg", 11264L, "Bytes"), testBuerger2);
            beschwerdeRepository.save(testBeschwerde4);

            Beschwerde testBeschwerde5 = new Beschwerde("Parkprobleme", "Falschparker auf Gehwegen", "Vor unserem Haus parken regelmäßig Autos auf dem Gehweg, was besonders für Kinder und ältere Menschen problematisch ist.", new Anhang("falschparker.jpg", "image/jpeg", 8192L, "Bytes"), testBuerger2);
            beschwerdeRepository.save(testBeschwerde5);

            Mitarbeiter testMitarbeiter2 = new Mitarbeiter(1L, "Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef");
            testMitarbeiter2.setPasswort(passwordEncoder.encode(testMitarbeiter2.getPasswort()));
            mitarbeiterRepository.save(testMitarbeiter2);
            //mitarbeiterService.registriereMitarbeiter(testMitarbeiter2);

            mitarbeiterService.Test();

        };
    }

}
