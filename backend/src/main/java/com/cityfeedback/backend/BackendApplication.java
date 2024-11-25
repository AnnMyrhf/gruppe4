package com.cityfeedback.backend;

import com.cityfeedback.backend.domain.Beschwerde;
import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.domain.Mitarbeiter;
import com.cityfeedback.backend.repositories.BeschwerdeRepository;
import com.cityfeedback.backend.repositories.BuergerRepository;
import com.cityfeedback.backend.repositories.MitarbeiterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * Hauptklasse, welche die Anwendung ausfuehrt
 */
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MitarbeiterRepository mitarbeiterRepository, BuergerRepository buergerRepository, BeschwerdeRepository beschwerdeRepository){
        return args -> {
            buergerRepository.save(new Buerger(1L, "Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "StarkesPW1?"));
            mitarbeiterRepository.save(new Mitarbeiter("Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef"));
            beschwerdeRepository.save(new Beschwerde(new Date(), "OPEN", "Infrastruktur", "Hoch", "Beschwerdetext", true, "application/pdf"));
            beschwerdeRepository.save(new Beschwerde(new Date(), "OPEN", "Infrastruktur", "Hoch", "A", true, "application/pdf"));
            beschwerdeRepository.save(new Beschwerde(new Date(), "OPEN", "Infrastruktur", "Hoch", "B", true, "application/pdf"));
            beschwerdeRepository.save(new Beschwerde(new Date(), "OPEN", "Infrastruktur", "Hoch", "C", true, "application/pdf"));
        };
    }
}
