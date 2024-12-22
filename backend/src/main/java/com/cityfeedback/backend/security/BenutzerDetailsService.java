package com.cityfeedback.backend.security;

import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterRepository;
import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Logik, um mitarbeiterspezifische Daten zu laden
 *
 * @author Ann-Kathrin Meyerhof
 */
@Service
public class BenutzerDetailsService implements UserDetailsService {

    @Autowired
    BuergerRepository buergerRepository;

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;

    @Override
    @Transactional
    // für Transaktionsmanagement (weil JPA das nicht bietet), um benutzerdefinierte Rollback-Regeln zu konfigurieren
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Zuerst pruefen, ob es sich um einen Mitarbeiter handelt
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findByEmail(email).orElse(null);
        if (mitarbeiter != null) {
            return mitarbeiter;
        }

            // Wenn kein Mitarbeiter gefunden wurde, dann ist es ein Bürger
            Buerger buerger = buergerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " konnte nicht gefunden werden"));
            return buerger;
        }
    }

