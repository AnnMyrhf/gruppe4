package com.cityfeedback.backend.beschwerde;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status;
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

    @Test
    void testGetBeschwerdeById_NotFound() {
        // Mocking
        Long beschwerdeId = 1L;
        when(beschwerdeRepository.findById(beschwerdeId)).thenReturn(Optional.empty());

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> beschwerdeService.getBeschwerde(beschwerdeId));
        verify(beschwerdeRepository, times(1)).findById(beschwerdeId);
    }

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

    @Test
    void testGetAllBeschwerden_NotFound() {
        // Mocking
        when(beschwerdeRepository.findAll()).thenReturn(List.of());

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> beschwerdeService.getAllBeschwerden());
        verify(beschwerdeRepository, times(1)).findAll();
    }

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

    @Test
    void testGetBeschwerdenByBuergerId_NotFound() {
        // Mocking
        Long buergerId = 1L;
        when(beschwerdeRepository.findByBuerger_Id(buergerId)).thenReturn(List.of());

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> beschwerdeService.getBeschwerdenByBuergerId(buergerId));
        verify(beschwerdeRepository, times(1)).findByBuerger_Id(buergerId);
    }

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

    @Test
    void testCreateBeschwerde_DataIntegrityViolationException() {
        // Mocking
        Long buergerId = 1L;
        String titel = "Test Titel";
        String beschwerdeTyp = "Typ1";
        String textfeld = "Test Text";
        MultipartFile mockFile = mock(MultipartFile.class);
        Buerger mockBuerger = new Buerger();
        Beschwerde beschwerde = new Beschwerde();

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
