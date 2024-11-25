package com.cityfeedback.backend.security;


import com.cityfeedback.backend.domain.Buerger;
import com.cityfeedback.backend.repositories.BuergerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Logik, um benutzerspezifische Daten zu laden
 *
 * @author Ann-Kathrin Meyerhof
 */
@Service
public class BenutzerDetailsService implements UserDetailsService {
    @Autowired
    BuergerRepository buergerRepository;

    @Override
    @Transactional
    // für Transaktionsmanagement (weil JPA das nicht bietet), um benutzerdefinierte Rollback-Regeln zu konfigurieren
    public Buerger loadUserByUsername(String email) throws UsernameNotFoundException {
        return buergerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " konnte nicht gefunden werden"));
    }

}

