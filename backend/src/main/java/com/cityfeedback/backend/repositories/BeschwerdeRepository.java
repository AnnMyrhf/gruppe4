package com.cityfeedback.backend.repositories;

import com.cityfeedback.backend.domain.Beschwerde;
import com.cityfeedback.backend.domain.Buerger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeschwerdeRepository extends JpaRepository<Beschwerde, Long> {

    Optional<Beschwerde> findByBuerger(Buerger buerger);
    Boolean existsByBuerger(Buerger buerger);
}

