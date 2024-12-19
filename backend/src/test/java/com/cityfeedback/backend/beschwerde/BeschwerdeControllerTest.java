package com.cityfeedback.backend.beschwerde;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.buergerverwaltung.model.Buerger;
import com.cityfeedback.backend.buergerverwaltung.infrastructure.BuergerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeschwerdeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeschwerdeRepository beschwerdeRepository;

    @Autowired
    private BuergerRepository buergerRepository;

    @Autowired
    private BeschwerdeService beschwerdeService;

    private Buerger testBuerger;

    @BeforeEach
    void setUp() {
        buergerRepository.deleteAll();
        beschwerdeRepository.deleteAll();

        // Testbürger erstellen
        testBuerger = new Buerger("Frau", "Maxi", "Musterfrau", "987654321", "maxi.musterfrau@example.com", "StarkesPW11?", null);
        buergerRepository.save(testBuerger);

        // Testbeschwerden erstellen
        Beschwerde beschwerde1 = new Beschwerde();
        beschwerde1.setTitel("Gültiger Titel");
        beschwerde1.setBeschwerdeTyp("Technik");
        beschwerde1.setTextfeld("Dies ist ein gültiger Text für die Beschwerde.");
        beschwerde1.setBuerger(testBuerger);
        beschwerdeRepository.save(beschwerde1);

        Beschwerde beschwerde2 = new Beschwerde();
        beschwerde2.setTitel("Noch eine Beschwerde");
        beschwerde2.setBeschwerdeTyp("Infrastruktur");
        beschwerde2.setTextfeld("Ein weiteres gültiges Textfeld für die Beschwerde.");
        beschwerde2.setBuerger(testBuerger);
        beschwerdeRepository.save(beschwerde2);


    }

    @Test
    void testGetBeschwerdenByBuergerId() throws Exception {
        // HTTP-Request simulieren und validieren
        mockMvc.perform(get("/buerger/dashboard/{id}", testBuerger.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titel", is("Gültiger Titel")))
                .andExpect(jsonPath("$[1].titel", is("Noch eine Beschwerde")));
    }

    @Test
    void testGetAllBeschwerden() throws Exception {
        // HTTP-Request simulieren und validieren
        mockMvc.perform(get("/mitarbeiter/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titel", is("Gültiger Titel")))
                .andExpect(jsonPath("$[1].titel", is("Noch eine Beschwerde")));
    }

    @Test
    void testGetBeschwerdeById() throws Exception {
        // Eine spezifische Beschwerde abrufen
        Beschwerde beschwerde = beschwerdeRepository.findAll().get(0);

        // HTTP-Request simulieren und validieren
        mockMvc.perform(get("/beschwerde/{id}", beschwerde.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titel", is("Gültiger Titel")));
    }

    @Test
    void testCreateBeschwerde() throws Exception {
        // JSON-Body für die Anfrage erstellen
        Map<String, Object> beschwerdeBody = new HashMap<>();
        beschwerdeBody.put("buergerId", testBuerger.getId());
        beschwerdeBody.put("titel", "Neue Beschwerde");
        beschwerdeBody.put("beschwerdeTyp", "Technik");
        beschwerdeBody.put("textfeld", "Dies ist eine neu erstellte Beschwerde.");

        Map<String, Object> anhang = new HashMap<>();
        anhang.put("dateiName", "test.pdf");
        anhang.put("datenTyp", "application/pdf");
        anhang.put("dateiGroesse", 12345);
        anhang.put("dateiEinheit", "KB");
        beschwerdeBody.put("anhang", anhang);

        // HTTP-Request simulieren und validieren
        mockMvc.perform(post("/beschwerde/erstellen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(beschwerdeBody)))
                .andExpect(status().isOk())
                .andExpect(content().string("Beschwerde erfolgreich erstellt!"));

        // Verifizieren, dass die Beschwerde gespeichert wurde
        Beschwerde gespeicherteBeschwerde = beschwerdeRepository.findAll().stream()
                .filter(b -> b.getTitel().equals("Neue Beschwerde"))
                .findFirst()
                .orElse(null);

        assertThat(gespeicherteBeschwerde).isNotNull();
        assertThat(gespeicherteBeschwerde.getTitel()).isEqualTo("Neue Beschwerde");
        assertThat(gespeicherteBeschwerde.getAnhang()).isNotNull();
        assertThat(gespeicherteBeschwerde.getAnhang().getDateiName()).isEqualTo("test.pdf");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
