package com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure;


import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Long> {
    Optional<Mitarbeiter> findByEmail(String email);

    Boolean existsByEmail(String email);
}

