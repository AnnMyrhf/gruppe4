package com.cityfeedback.backend.services;
import com.cityfeedback.backend.domain.Mitarbeiter;
import com.cityfeedback.backend.repositories.MitarbeiterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MitarbeiterService {
    private MitarbeiterRepository mitarbeiterRepository;

    public MitarbeiterService(MitarbeiterRepository mitarbeiterRepository){
        this.mitarbeiterRepository = mitarbeiterRepository;
    }

    public void createNewMitarbeiter(Mitarbeiter mitarbeiter){
        try {
            validateInput(mitarbeiter);
            mitarbeiterRepository.save(mitarbeiter);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void deleteMitarbeiter(Long id){
        Optional<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findById(id);
        mitarbeiterRepository.delete(mitarbeiter.get());
    }

    public static void validateInput(Mitarbeiter mitarbeiter) {
        String buchstabenRegEx = "^[a-zA-ZäöüÄÖÜß-]+$";
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // ueberprüft, ob E-Mail-Adresse aus einem Local-Part (Buchstaben, Zahlen, Unterstriche, Bindestriche, Punkte), einem @-Zeichen und einer Domain (mindestens zwei Teile, getrennt durch Punkte, und ein TLD mit zwei bis vier Zeichen) besteht.
        String telefonnummerRegex = "^\\d+$"; // nur Zahlen
        String passwortRegEx ="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; // Passwort mit mindestens 8 Zeichen und mindestens einem Buchstaben, einer Zahl und einem Sonderzeichen

        if (!mitarbeiter.getVorname().matches(buchstabenRegEx)) {
            throw new IllegalArgumentException("Vorname ist ungültig");
        }
        if (!mitarbeiter.getNachname().matches(buchstabenRegEx)) {
            throw new IllegalArgumentException("Nachname ist ungültig");
        }
        if (!mitarbeiter.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("E-Mail ist ungültig");
        }
        if (!mitarbeiter.getPasswort().matches(passwortRegEx)) {
            throw new IllegalArgumentException("Passwort ist ungültig");
        }

        if (!mitarbeiter.getTelefonnummer().matches(telefonnummerRegex)) {
            throw new IllegalArgumentException("Telefonnummer ist ungültig");
        }
    }
}
