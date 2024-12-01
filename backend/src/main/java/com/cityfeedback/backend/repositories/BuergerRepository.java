package com.cityfeedback.backend.repositories;

import com.cityfeedback.backend.domain.Buerger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuergerRepository extends JpaRepository<Buerger, Long> {
        Optional<Buerger> findByEmail(String email);

        Boolean existsByEmail(String email);
}
