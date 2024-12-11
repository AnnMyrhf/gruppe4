package com.cityfeedback.backend;

import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.beschwerdeverwaltung.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.application.service.MitarbeiterService;
import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
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
            // Testobjekte
            Buerger testBuerger1 = new Buerger(1L, "Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "StarkesPW11?", beschwerden);
            testBuerger1.setPasswort(passwordEncoder.encode(testBuerger1.getPasswort()));
            buergerRepository.save(testBuerger1);
            Buerger testBuerger2 = new Buerger(2L, "Frau", "Peter", "Neu", "987654321", "PN@example.com", "StarkesPW11?", beschwerden);
            testBuerger2.setPasswort(passwordEncoder.encode(testBuerger2.getPasswort()));
            buergerRepository.save(testBuerger2);
            mitarbeiterRepository.save(new Mitarbeiter(1L,"Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef"));
            beschwerdeRepository.save(new Beschwerde(1L, new Date(), "OPEN", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf", testBuerger2));
            beschwerdeRepository.save(new Beschwerde(2L, new Date(), "OPEN", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf", testBuerger2
            ));
            beschwerdeRepository.save(new Beschwerde(3L, new Date(), "OPEN", "Infrastruktur", "Hoch", "A", true, "application/pdf", testBuerger1));
            beschwerdeRepository.save(new Beschwerde(4L, new Date(), "OPEN", "Infrastruktur", "Hoch", "B", true, "application/pdf", testBuerger1));
            beschwerdeRepository.save(new Beschwerde(5L, new Date(), "OPEN", "Infrastruktur", "Hoch", "Sehr geehrte Damen und Herren,\n" +
                    "\n" +
                    "ich wende mich an Sie, um meine Unzufriedenheit über die Bearbeitung meines Anliegens vom 15. Oktober 2024 auszudrücken. Trotz wiederholter Kontaktaufnahme und der Vorlage aller notwendigen Unterlagen wurde mein Anliegen bisher nicht abschließend bearbeitet.\n" +
                    "\n" +
                    "Besonders ärgerlich finde ich, dass ich seit sechs Wochen keine Rückmeldung erhalten habe, obwohl mir telefonisch zugesichert wurde, dass die Bearbeitung innerhalb von 14 Tagen abgeschlossen sei. Dies bringt für mich erhebliche Nachteile mit sich, da ich aufgrund der Verzögerung nicht wie geplant meine Bauarbeiten fortsetzen kann, was zu finanziellen Mehrkosten führt.\n" +
                    "\n" +
                    "Ich bitte Sie daher, die Bearbeitung meines Anliegens zu priorisieren und mich zeitnah über den aktuellen Stand zu informieren. Sollte es Hindernisse geben, teilen Sie mir diese bitte schriftlich mit.\n" +
                    "\n" +
                    "Vielen Dank im Voraus. Ich hoffe auf eine zügige Klärung der Angelegenheit.\n" +
                    "\n" +
                    "Mit freundlichen Grüßen\n" +

                    "Max Mustermann", true, "application/pdf", testBuerger1));
            mitarbeiterService.registriereMitarbeiter(new Mitarbeiter(1L, "Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef"));
            mitarbeiterService.Test();

        };
    }

}
