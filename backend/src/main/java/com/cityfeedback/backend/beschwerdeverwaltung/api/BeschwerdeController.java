package com.cityfeedback.backend.beschwerdeverwaltung.api;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class BeschwerdeController {

    BeschwerdeService beschwerdeService;

    @GetMapping("/buerger/dashboard/{id}")
    public List<Beschwerde> getBeschwerdenByBuergerId(@PathVariable("id") Long id){
        return beschwerdeService.getBeschwerdenByBuergerId(id);
    }

    @GetMapping("/mitarbeiter/dashboard")
    public List<Beschwerde> getAllBeschwerden(){
        return beschwerdeService.getAllBeschwerden();
    }

    @GetMapping("/beschwerde/{id}")
    public Beschwerde getBeschwerde(@PathVariable("id") Long id){
        return beschwerdeService.getBeschwerde(id);
    }

    @PostMapping("beschwerde/erstellen")
    public ResponseEntity<?> createBeschwerde(
            @RequestParam("buergerId") Long buergerId,
            @RequestParam("titel") String titel,
            @RequestParam("beschwerdeTyp") String beschwerdeTyp,
            @RequestParam("textfeld") String textfeld,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        return beschwerdeService.createBeschwerde(titel, beschwerdeTyp, textfeld, file, buergerId);
    }

    @PutMapping("/beschwerde/{id}/update")
    public ResponseEntity<?> updateKommentar(@PathVariable Long id, @RequestBody Map<String, String> body) {

        Beschwerde aktualisierteBeschwerde = beschwerdeService.updateBeschwerde(id, body);
        return ResponseEntity.ok(aktualisierteBeschwerde);
    }

//    @PutMapping("/beschwerde/{id}/status")
//    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
//        String status = body.get("status");
//
//        Beschwerde aktualisierteBeschwerde = beschwerdeService.updateStatus(id, status);
//        return ResponseEntity.ok(aktualisierteBeschwerde);
//    }




}