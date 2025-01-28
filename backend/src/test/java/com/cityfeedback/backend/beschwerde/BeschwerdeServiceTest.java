package com.cityfeedback.backend.beschwerde;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import com.cityfeedback.backend.buergerverwaltung.application.service.BuergerService;
import com.cityfeedback.backend.buergerverwaltung.domain.model.Buerger;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BeschwerdeServiceTest {

    @Mock
    private BeschwerdeRepository beschwerdeRepository;

    @Mock
    private BuergerService buergerService;

    @InjectMocks
    private BeschwerdeService beschwerdeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testet die Methode getBeschwerdeById für den Fall, dass eine Beschwerde gefunden wird.
     * Überprüft, ob das Ergebnis korrekt zurückgegeben wird und die Repository-Methode aufgerufen wird.
     */
    @Test
    void testGetBeschwerdeById_Found() {
        // Mocking
        Long beschwerdeId = 1L;
        Beschwerde mockBeschwerde = new Beschwerde();
        mockBeschwerde.setId(beschwerdeId);
        when(beschwerdeRepository.findById(beschwerdeId)).thenReturn(Optional.of(mockBeschwerde));

        // Test
        Beschwerde result = beschwerdeService.getBeschwerde(beschwerdeId);

        // Verify
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(beschwerdeId);
        verify(beschwerdeRepository, times(1)).findById(beschwerdeId);
    }

    /**
     * Testet die Methode getBeschwerdeById für den Fall, dass keine Beschwerde gefunden wird.
     * Erwartet eine IllegalArgumentException und überprüft den Repository-Aufruf.
     */
    @Test
    void testGetBeschwerdeById_NotFound() {
        // Mocking
        Long beschwerdeId = 1L;
        when(beschwerdeRepository.findById(beschwerdeId)).thenReturn(Optional.empty());

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> beschwerdeService.getBeschwerde(beschwerdeId));
        verify(beschwerdeRepository, times(1)).findById(beschwerdeId);
    }

    /**
     * Testet die Methode getAllBeschwerden für den Fall, dass Beschwerden gefunden werden.
     * Überprüft, ob die korrekte Anzahl an Beschwerden zurückgegeben wird.
     */
    @Test
    void testGetAllBeschwerden_Found() {
        // Mocking
        Beschwerde beschwerde1 = new Beschwerde();
        Beschwerde beschwerde2 = new Beschwerde();
        when(beschwerdeRepository.findAll()).thenReturn(Arrays.asList(beschwerde1, beschwerde2));

        // Test
        List<Beschwerde> result = beschwerdeService.getAllBeschwerden();

        // Verify
        assertThat(result).hasSize(2);
        verify(beschwerdeRepository, times(1)).findAll();
    }

    /**
     * Testet die Methode getAllBeschwerden für den Fall, dass keine Beschwerden gefunden werden.
     * Überprüft, ob eine leere Liste zurückgegeben wird.
     */
    @Test
    void testGetAllBeschwerden_NotFound() {
        // Mocking
        when(beschwerdeRepository.findAll()).thenReturn(List.of());

        // Test & Verify
        List<Beschwerde> result = beschwerdeService.getAllBeschwerden();
        assertTrue(result.isEmpty(), "Die Liste sollte leer sein");
        verify(beschwerdeRepository, times(1)).findAll();
    }

    /**
     * Testet die Methode getBeschwerdenByBuergerId für den Fall, dass Beschwerden gefunden werden.
     * Überprüft, ob die korrekte Anzahl an Beschwerden zurückgegeben wird.
     */
    @Test
    void testGetBeschwerdenByBuergerId_Found() {
        // Mocking
        Long buergerId = 1L;
        Beschwerde beschwerde1 = new Beschwerde();
        Beschwerde beschwerde2 = new Beschwerde();
        when(beschwerdeRepository.findByBuerger_Id(buergerId)).thenReturn(Arrays.asList(beschwerde1, beschwerde2));

        // Test
        List<Beschwerde> result = beschwerdeService.getBeschwerdenByBuergerId(buergerId);

        // Verify
        assertThat(result).hasSize(2);
        verify(beschwerdeRepository, times(1)).findByBuerger_Id(buergerId);
    }

    /**
     * Testet die Methode getBeschwerdenByBuergerId für den Fall, dass keine Beschwerden gefunden werden.
     * Überprüft, ob eine leere Liste zurückgegeben wird.
     */
    @Test
    void testGetBeschwerdenByBuergerId_NotFound() {
        // Mocking
        Long buergerId = 1L;
        when(beschwerdeRepository.findByBuerger_Id(buergerId)).thenReturn(List.of());

        // Test & Verify
        List<Beschwerde> result = beschwerdeService.getBeschwerdenByBuergerId(buergerId);
        assertTrue(result.isEmpty(), "Die Liste sollte leer sein");
        verify(beschwerdeRepository, times(1)).findByBuerger_Id(buergerId);
    }

    /**
     * Testet die Methode createBeschwerde für den erfolgreichen Fall.
     * Überprüft, ob die Beschwerde erfolgreich erstellt und gespeichert wird.
     */
    @Test
    void testCreateBeschwerde_Success() throws IOException {
        // Mocking
        Long buergerId = 1L;
        String titel = "Test Titel";
        String beschwerdeTyp = "Typ1";
        String textfeld = "Test Text";
        MultipartFile mockFile = mock(MultipartFile.class);

        // Mock MultipartFile
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("testfile.txt");
        when(mockFile.getContentType()).thenReturn("text/plain");
        when(mockFile.getSize()).thenReturn(1234L);
        when(mockFile.getBytes()).thenReturn("Test File Content".getBytes());

        // Mock Buerger und Repository
        Buerger mockBuerger = new Buerger();
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel(titel);

        when(buergerService.getBuergerById(buergerId)).thenReturn(Optional.of(mockBuerger));
        when(beschwerdeRepository.save(any(Beschwerde.class))).thenReturn(beschwerde);

        // Test
        ResponseEntity<?> response = beschwerdeService.createBeschwerde(titel, beschwerdeTyp, textfeld, mockFile, buergerId);

        // Verify
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(buergerService, times(1)).getBuergerById(buergerId);
        verify(beschwerdeRepository, times(1)).save(any(Beschwerde.class));
    }

    /**
     * Testet die Methode createBeschwerde für den Fall, dass der Bürger nicht gefunden wird.
     * Erwartet eine Fehlermeldung und überprüft, dass keine Beschwerde gespeichert wird.
     */
    @Test
    void testCreateBeschwerde_BuergerNotFound() {
        // Mocking
        Long buergerId = 1L;
        String titel = "Test Titel";
        String beschwerdeTyp = "Typ1";
        String textfeld = "Test Text";
        MultipartFile mockFile = mock(MultipartFile.class);

        when(buergerService.getBuergerById(buergerId)).thenReturn(Optional.empty());

        // Test
        ResponseEntity<?> response = beschwerdeService.createBeschwerde(titel, beschwerdeTyp, textfeld, mockFile, buergerId);

        // Verify
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody()).isEqualTo("Fehler: Bürger mit ID 1 nicht gefunden.");
        verify(buergerService, times(1)).getBuergerById(buergerId);
        verify(beschwerdeRepository, never()).save(any(Beschwerde.class));
    }

    /**
     * Testet die Methode createBeschwerde für den Fall einer Datenintegritätsverletzung.
     * Überprüft, ob ein Fehler korrekt behandelt wird.
     */
    @Test
    void testCreateBeschwerde_DataIntegrityViolationException() {
        // Mocking
        Long buergerId = 1L;
        String titel = "Test Titel";
        String beschwerdeTyp = "Typ1";
        String textfeld = "Test Text";
        MultipartFile mockFile = mock(MultipartFile.class);
        Buerger mockBuerger = new Buerger();

        DataIntegrityViolationException exception = new DataIntegrityViolationException("Datenbankfehler", new ConstraintViolationException("Constraint verletzt", null));

        when(buergerService.getBuergerById(buergerId)).thenReturn(Optional.of(mockBuerger));
        when(beschwerdeRepository.save(any(Beschwerde.class))).thenThrow(exception);

        // Test
        ResponseEntity<?> response = beschwerdeService.createBeschwerde(titel, beschwerdeTyp, textfeld, mockFile, buergerId);

        // Verify
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        verify(buergerService, times(1)).getBuergerById(buergerId);
        verify(beschwerdeRepository, times(1)).save(any(Beschwerde.class));
    }

    /**
     * Testet die Methode createBeschwerde für den Fall eines unerwarteten Fehlers.
     * Überprüft, ob der Fehler korrekt behandelt wird.
     */
    @Test
    void testCreateBeschwerde_InternalServerError() {
        // Mocking
        Long buergerId = 1L;
        String titel = "Test Titel";
        String beschwerdeTyp = "Typ1";
        String textfeld = "Test Text";
        MultipartFile mockFile = mock(MultipartFile.class);
        Buerger mockBuerger = new Buerger();
        Beschwerde beschwerde = new Beschwerde();
        beschwerde.setTitel("Test Titel");

        when(buergerService.getBuergerById(buergerId)).thenReturn(Optional.of(mockBuerger));
        when(beschwerdeRepository.save(any(Beschwerde.class))).thenThrow(new RuntimeException("Unerwarteter Fehler"));

        // Test
        ResponseEntity<?> response = beschwerdeService.createBeschwerde(titel, beschwerdeTyp, textfeld, mockFile, buergerId);

        // Verify
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody()).isEqualTo("Fehler: Unerwarteter Fehler");
        verify(buergerService, times(1)).getBuergerById(buergerId);
        verify(beschwerdeRepository, times(1)).save(any(Beschwerde.class));
    }
}
