package com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure;


import com.cityfeedback.backend.mitarbeiterverwaltung.domain.model.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository für Mitarbeiter-Entitaeten, das Methoden zur Suche nach einem Mitarbeiter bietet
 *
 * @author Ann-Kathrin Meyerhof, Maik Bartels
 */
@Repository
public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Long> {
    Optional<Mitarbeiter> findByEmail(String email);

    Boolean existsByEmail(String email);
}

