package com.cityfeedback.backend.beschwerdeverwaltung.api;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Anhang;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createBeschwerde(@RequestBody Map<String, Object> body) {
        // Extrahiere die buergerId und konvertiere sie in Long
        Number buergerIdNumber = (Number) body.get("buergerId");
        Long buergerId = buergerIdNumber.longValue();

        // Baue das Beschwerde-Objekt
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel((String) body.get("titel"));
        beschwerde.setBeschwerdeTyp((String) body.get("beschwerdeTyp"));
        beschwerde.setTextfeld((String) body.get("textfeld"));

        // Anhang extrahieren und setzen (falls vorhanden)
        if (body.containsKey("anhang")) {
            Map<String, Object> anhangMap = (Map<String, Object>) body.get("anhang");
            Anhang anhang = new Anhang();
            anhang.setDateiName((String) anhangMap.get("dateiName"));
            anhang.setDatenTyp((String) anhangMap.get("datenTyp"));
            anhang.setDateiGroesse(((Number) anhangMap.get("dateiGroesse")).longValue());
            anhang.setDateiEinheit((String) anhangMap.get("dateiEinheit"));
            beschwerde.setAnhang(anhang);
        }

        // Übergib die Daten an den Service
        return beschwerdeService.createBeschwerde(beschwerde, buergerId);
    }



}