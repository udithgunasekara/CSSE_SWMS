package com.csse.serviceTesting;
import com.csse.DTO.Trashbin;
import com.csse.repo.RepoInterface.ITrashBinRepository;
import com.csse.repo.TrashBinRepository;
import com.csse.service.ITrashBinFactory;
import com.csse.service.Imp.TrashbinServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class TrashbinServiceTest {
    private TrashbinServiceImpl trashbinService;
    private TrashBinRepository trashBinRepository;
    private ITrashBinFactory trashBinFactory;

    @BeforeEach
    void setUp() {
        trashBinRepository = Mockito.mock(TrashBinRepository.class);
        trashBinFactory = Mockito.mock(ITrashBinFactory.class);
        trashbinService = new TrashbinServiceImpl(trashBinRepository, trashBinFactory);
    }

    @Test
    void testCreateTrashBin() throws ExecutionException, InterruptedException {
        // Given
        Trashbin trashbin = new Trashbin();
        when(trashBinFactory.createTrashBin(trashbin)).thenReturn(trashbin);
        when(trashBinRepository.createTrashBin(trashbin)).thenReturn("testId");

        // When
        String actualId = trashbinService.createTrashBin(trashbin);

        // Then
        assertEquals("testId", actualId);
    }

    @Test
    void testFindAllTrashBins() throws ExecutionException, InterruptedException {
        // Given
        Trashbin trashbin = new Trashbin();
        List<Trashbin> expectedTrashbins = new ArrayList<>();
        expectedTrashbins.add(trashbin);
        when(trashBinRepository.getAllTrashbins()).thenReturn(expectedTrashbins);

        // When
        List<Trashbin> actualTrashbins = trashbinService.findAllTrashBins();

        // Then
        assertEquals(expectedTrashbins, actualTrashbins);
    }

    @Test
    void testFindFullTrashBins() throws ExecutionException, InterruptedException {
        // Given
        Trashbin trashbin = new Trashbin();
        List<Trashbin> expectedTrashbins = new ArrayList<>();
        expectedTrashbins.add(trashbin);
        when(trashBinRepository.getFullTrashbins()).thenReturn(expectedTrashbins);

        // When
        List<Trashbin> actualTrashbins = trashbinService.findFullTrashBins();

        // Then
        assertEquals(expectedTrashbins, actualTrashbins);
    }

    @Test
    void testTrashBinsToCollect() throws ExecutionException, InterruptedException {
        // Given
        Trashbin trashbin = new Trashbin();
        trashbin.setWeight(100); // Set a weight for the test
        List<Trashbin> allBins = new ArrayList<>();
        allBins.add(trashbin);
        when(trashBinRepository.getAllTrashbinsByFacId(anyString())).thenReturn(allBins);

        // When
        List<Trashbin> selectedBins = trashbinService.trashBinsToCollect("facilityId");

        // Then
        assertEquals(1, selectedBins.size());
        assertEquals(trashbin, selectedBins.get(0));
    }
}

