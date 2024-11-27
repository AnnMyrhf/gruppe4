package com.cityfeedback.backend.buergerverwaltung.infrastructure;

import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuergerRepository extends JpaRepository<Buerger, Long> {
        Optional<Buerger> findByEmail(String email);

        Boolean existsByEmail(String email);

}
