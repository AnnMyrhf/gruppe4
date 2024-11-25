package com.cityfeedback.backend.repositories;

import com.cityfeedback.backend.domain.Beschwerde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeschwerdeRepository extends JpaRepository<Beschwerde, Long> {

}
