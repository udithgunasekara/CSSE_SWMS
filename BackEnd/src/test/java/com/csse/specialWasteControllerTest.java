package com.csse;

import com.csse.DTO.SpecialWasteDTO;
import com.csse.repo.SpecialWasteRepository;
import com.csse.Service.Imp.SpecialWasteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class specialWasteControllerTest {

    // Mocking the repository
    @Mock
    private SpecialWasteRepository specialWasteRepository;

    // Injecting the mocked service
    @InjectMocks
    private SpecialWasteServiceImpl specialWasteService;

    private SpecialWasteDTO specialWasteDTO;

    // Initializing the mock
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        specialWasteDTO = new SpecialWasteDTO();
        specialWasteDTO.setId("user123");
        specialWasteDTO.setStatus("Pending");
    }


    @Test
    void createSpecialWaste_Failure() {
        // Negative Test Case
        doThrow(new RuntimeException("Firestore error")).when(specialWasteRepository).saveSpecialWaste(specialWasteDTO);

        Optional<SpecialWasteDTO> result = specialWasteService.createSpecialWaste(specialWasteDTO);

        assertFalse(result.isPresent(), "Expected Optional to be empty");
        verify(specialWasteRepository, times(1)).saveSpecialWaste(specialWasteDTO);
    }

    @Test
    void getAllSpecialWastes_Success() throws ExecutionException, InterruptedException {
        // Positive Test Case
        List<SpecialWasteDTO> mockList = List.of(specialWasteDTO);
        when(specialWasteRepository.getAllSpecialWastes()).thenReturn(mockList);

        List<SpecialWasteDTO> result = specialWasteService.getAllSpecialWastes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pending", result.get(0).getStatus());
        verify(specialWasteRepository, times(1)).getAllSpecialWastes();
    }

    @Test
    void getAllSpecialWastes_Failure() throws ExecutionException, InterruptedException {
        // Negative Test Case
        when(specialWasteRepository.getAllSpecialWastes()).thenThrow(new ExecutionException("Firestore error", null));

        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            specialWasteService.getAllSpecialWastes();
        });

        assertEquals("Firestore error", exception.getCause().getMessage());
        verify(specialWasteRepository, times(1)).getAllSpecialWastes();
    }

    @Test
    void getSpecialWasteByUserId_Success() throws ExecutionException, InterruptedException {
        // Positive Test Case
        String userId = "user123";
        when(specialWasteRepository.getSpecialWasteByUserId(userId)).thenReturn(List.of(specialWasteDTO));

        List<SpecialWasteDTO> result = specialWasteService.getSpecialWasteByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pending", result.get(0).getStatus());
        verify(specialWasteRepository, times(1)).getSpecialWasteByUserId(userId);
    }

    @Test
    void getSpecialWasteByUserId_Empty() throws ExecutionException, InterruptedException {
        // Negative Test Case
        String userId = "nonExistingUser";
        when(specialWasteRepository.getSpecialWasteByUserId(userId)).thenReturn(List.of());

        List<SpecialWasteDTO> result = specialWasteService.getSpecialWasteByUserId(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected result list to be empty");
        verify(specialWasteRepository, times(1)).getSpecialWasteByUserId(userId);
    }
}
