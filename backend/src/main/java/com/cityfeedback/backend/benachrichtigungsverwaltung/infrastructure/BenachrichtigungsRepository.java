package com.cityfeedback.backend.benachrichtigungsverwaltung.infrastructure;

import com.cityfeedback.backend.benachrichtigungsverwaltung.model.Benachrichtigung;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenachrichtigungsRepository extends JpaRepository<Benachrichtigung, Long> {

    //List<Benachrichtigung> findByBeschwerde(Optional<Beschwerde> beschwerde);
    //List<Benachrichtigung> findByBeschwerde_Id(Long beschwerdeId);
}
