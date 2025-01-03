package com.cityfeedback.backend.beschwerdeverwaltung.infrastructure;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface BeschwerdeRepository extends JpaRepository<Beschwerde, Long> {
    List<Beschwerde> findByBuerger(Optional<Buerger> buerger);
    List<Beschwerde> findByBuerger_Id(Long buergerId);
    // Boolean existsByBuerger(Buerger buerger);
}

