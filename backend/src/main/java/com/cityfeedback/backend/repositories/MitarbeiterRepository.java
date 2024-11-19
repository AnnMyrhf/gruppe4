package com.cityfeedback.backend.repositories;


import com.cityfeedback.backend.domain.Mitarbeiter;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MitarbeiterRepository {

    private List<Mitarbeiter> mitarbeiter = new ArrayList<>();

    public List<Mitarbeiter> findAll() {
        return mitarbeiter;
    }

    public Optional<Mitarbeiter> findById(Integer id){
        return mitarbeiter.stream()
                .filter(mitarbeiter -> mitarbeiter.getId() == id)
                .findFirst();
    }

    public void create(Mitarbeiter mitarbeiter){
        this.mitarbeiter.add(mitarbeiter);
    }

    public void delete(Integer id){
        this.mitarbeiter.removeIf(mitarbeiter -> mitarbeiter.getId().equals(id));
    }

    @PostConstruct
    private void init(){
        mitarbeiter.add(new Mitarbeiter(
                1,
                "Frau",
                "Anna",
                "Müller",
                "123456",
                "Hallo@web.com",
                "Hallo12!",
                "Verwaltung",
                "Chef"));
    }
}
