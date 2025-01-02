package com.cityfeedback.backend.beschwerde;

import com.cityfeedback.backend.beschwerdeverwaltung.api.BeschwerdeController;
import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeschwerdeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeschwerdeService beschwerdeService;

    private Beschwerde testBeschwerde;

    @BeforeEach
    void setUp() {
        testBeschwerde = new Beschwerde();
        testBeschwerde.setId(1L);
        testBeschwerde.setTitel("Testbeschwerde");
        testBeschwerde.setTextfeld("Testinhalt");
        testBeschwerde.setStatus(Status.EINGEGANGEN);
    }

    @Test
    void testGetBeschwerdenByBuergerId() throws Exception {
        when(beschwerdeService.getBeschwerdenByBuergerId(1L)).thenReturn(Arrays.asList(testBeschwerde));

        mockMvc.perform(get("/buerger/dashboard/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titel").value("Testbeschwerde"));
    }

    @Test
    void testGetAllBeschwerden() throws Exception {
        when(beschwerdeService.getAllBeschwerden()).thenReturn(Arrays.asList(testBeschwerde));

        mockMvc.perform(get("/mitarbeiter/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titel").value("Testbeschwerde"));
    }

    @Test
    void testGetBeschwerde() throws Exception {
        when(beschwerdeService.getBeschwerde(1L)).thenReturn(testBeschwerde);

        mockMvc.perform(get("/beschwerde/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titel").value("Testbeschwerde"));
    }

    @Test
    void testCreateBeschwerde() throws Exception {
        when(beschwerdeService.createBeschwerde(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.anyLong()))
             .thenReturn(ResponseEntity.ok("Beschwerde erfolgreich erstellt."));

        mockMvc.perform(multipart("/beschwerde/erstellen")
                        .param("buergerId", "1")
                        .param("titel", "Neue Beschwerde")
                        .param("beschwerdeTyp", "Technik")
                        .param("textfeld", "Dies ist eine Testbeschwerde."))
                .andExpect(status().isOk())
                .andExpect(content().string("Beschwerde erfolgreich erstellt."));
    }

    @Test
    void testUpdateKommentar() throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("kommentar", "Aktualisierter Kommentar");

        when(beschwerdeService.updateKommentar(1L, "Aktualisierter Kommentar")).thenReturn(testBeschwerde);

        mockMvc.perform(put("/beschwerde/1/kommentar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"kommentar\":\"Aktualisierter Kommentar\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titel").value("Testbeschwerde"));
    }

    @Test
    void testUpdateStatus() throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("status", "IN_BEARBEITUNG");

        testBeschwerde.setStatus(Status.IN_BEARBEITUNG);
        when(beschwerdeService.updateStatus(1L, "IN_BEARBEITUNG")).thenReturn(testBeschwerde);

        mockMvc.perform(put("/beschwerde/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"IN_BEARBEITUNG\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_BEARBEITUNG"));
    }
}
