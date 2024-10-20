package com.csse.serviceTesting;
import com.csse.DTO.Trashbin;
import com.csse.DTO.WasteCollectionPersonal;
import com.csse.repo.RepoInterface.ICollecitonPersonRepo;
import com.csse.repo.TrashBinRepository;
import com.csse.service.Imp.ICollectionHistoryServiceImpl;
import com.csse.service.Imp.WasteCollectionPersonalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class WasteCollectionPersonalTest {
    @Mock
    private ICollecitonPersonRepo wasteCollectionPersonalRepo;

    @Mock
    private TrashBinRepository trashBinRepository;

    @Mock
    private ICollectionHistoryServiceImpl collectionHistoryService;

    @InjectMocks
    private WasteCollectionPersonalServiceImpl wasteCollectionPersonalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWasteCollectionPersonal() throws ExecutionException, InterruptedException {
        // Given
        WasteCollectionPersonal wasteCollectionPersonal = new WasteCollectionPersonal();
        wasteCollectionPersonal.setEmail("test@gmail.com");

        // When
        when(wasteCollectionPersonalRepo.createWasteCollectionPersonal(any(WasteCollectionPersonal.class))).thenReturn("12345");

        // Then
        String result = wasteCollectionPersonalService.createWasteCollectionPersonal(wasteCollectionPersonal);
        assertEquals("12345", result);
        verify(wasteCollectionPersonalRepo, times(1)).createWasteCollectionPersonal(any(WasteCollectionPersonal.class));
    }

    @Test
    void testUpdateWasteCollected_Success() throws ExecutionException, InterruptedException {
        // Given
        Trashbin trashbin = new Trashbin();
        trashbin.setWasteLevel(50);  // Waste present
        trashbin.setWeight(20);

        WasteCollectionPersonal user = new WasteCollectionPersonal();
        user.setWasteCollected(5);

        when(trashBinRepository.findTrashbinById("1")).thenReturn(Optional.of(trashbin));
        when(wasteCollectionPersonalRepo.getuser("1")).thenReturn(Optional.of(user));

        // When
        String result = wasteCollectionPersonalService.updateWasteCollected("1", "1");

        // Then
        assertEquals("1", result);
        verify(collectionHistoryService, times(1)).createNewCollectionHistor("1", "1", 20.0);
        verify(trashBinRepository, times(1)).updateTrashbin(any(Trashbin.class));
        verify(wasteCollectionPersonalRepo, times(1)).updateWasteCollectionPersonal(eq("1"), any(WasteCollectionPersonal.class));
    }

    @Test
    void testUpdateWasteCollected_TrashbinEmpty() throws ExecutionException, InterruptedException {
        // Given
        Trashbin trashbin = new Trashbin();
        trashbin.setWasteLevel(0);  // No waste

        when(trashBinRepository.findTrashbinById("1")).thenReturn(Optional.of(trashbin));

        // Then
        assertThrows(IllegalArgumentException.class, () -> wasteCollectionPersonalService.updateWasteCollected("1", "1"));
        verify(trashBinRepository, times(1)).findTrashbinById("1");
        verify(collectionHistoryService, never()).createNewCollectionHistor(anyString(), anyString(), anyDouble());
        verify(wasteCollectionPersonalRepo, never()).updateWasteCollectionPersonal(anyString(), any(WasteCollectionPersonal.class));
    }

    @Test
    void testUpdateWasteCollected_TrashbinNotFound() throws ExecutionException, InterruptedException {
        // Given
        when(trashBinRepository.findTrashbinById("1")).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> wasteCollectionPersonalService.updateWasteCollected("1", "1"));
        verify(trashBinRepository, times(1)).findTrashbinById("1");
        verify(collectionHistoryService, never()).createNewCollectionHistor(anyString(), anyString(), anyDouble());
        verify(wasteCollectionPersonalRepo, never()).updateWasteCollectionPersonal(anyString(), any(WasteCollectionPersonal.class));
    }

    @Test
    void testUpdateWasteCollected_UserNotFound() throws ExecutionException, InterruptedException {
        // Given
        Trashbin trashbin = new Trashbin();
        trashbin.setWasteLevel(50);

        when(trashBinRepository.findTrashbinById("1")).thenReturn(Optional.of(trashbin));
        when(wasteCollectionPersonalRepo.getuser("1")).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> wasteCollectionPersonalService.updateWasteCollected("1", "1"));
        verify(trashBinRepository, times(1)).findTrashbinById("1");
        verify(collectionHistoryService, never()).createNewCollectionHistor(anyString(), anyString(), anyDouble());
        verify(wasteCollectionPersonalRepo, never()).updateWasteCollectionPersonal(anyString(), any(WasteCollectionPersonal.class));
    }
}
