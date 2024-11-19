package com.cityfeedback.backend;

import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.domain.Mitarbeiter;
import com.cityfeedback.backend.repositories.BuergerRepository;
import com.cityfeedback.backend.repositories.MitarbeiterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Hauptklasse, welche die Anwendung ausfuehrt
 */
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MitarbeiterRepository mitarbeiterRepository, BuergerRepository buergerRepository){
        return args -> {
            buergerRepository.save(new Buerger(123L, "Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfau@example.com", "StarkesPW1?"));
            mitarbeiterRepository.save(new Mitarbeiter(1L,"Frau", "Anna", "Müller", "123456", "Hallo@web.com", "Hallo12!", "Verwaltung", "Chef"));
        };
    }

}
