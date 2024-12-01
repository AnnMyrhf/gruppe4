package com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure;


import com.cityfeedback.backend.mitarbeiterverwaltung.model.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Long> {

}
