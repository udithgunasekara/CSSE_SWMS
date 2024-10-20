package com.csse.service.Imp;

import com.csse.DTO.Trashbin;
import com.csse.repo.RepoInterface.ITrashBinRepository;
import com.csse.repo.TrashBinRepository;
import com.csse.service.ITrashBinFactory;
import com.csse.service.ITrashBinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.MAX_TRACTOR_WEIGHT;

@Service
public class TrashbinServiceImpl implements ITrashBinService {

    private static final Logger logger = LoggerFactory.getLogger(TrashbinServiceImpl.class);

    private final ITrashBinRepository trashBinRepository;
    // define object to create trashbin object using factory design pattern
    private final ITrashBinFactory trashBinFactory;

    public TrashbinServiceImpl(TrashBinRepository trashBinRepository, ITrashBinFactory trashBinFactory) {
        this.trashBinRepository = trashBinRepository;
        this.trashBinFactory = trashBinFactory;
    }

    @Override
    public String createTrashBin(Trashbin trashBin) throws ExecutionException, InterruptedException {
        logger.info("Creating a new trash bin: {}", trashBin);
        String trashBinId = trashBinRepository.createTrashBin(trashBinFactory.createTrashBin(trashBin));
        logger.info("Trash bin created successfully with ID: {}", trashBinId);
        return trashBinId;
    }

    @Override
    public Optional<Trashbin> findTrashBinById(String id) throws ExecutionException, InterruptedException {
        logger.info("Finding trash bin by ID: {}", id);
        Optional<Trashbin> trashBin = trashBinRepository.findTrashbinById(id);
        if (trashBin.isPresent()) {
            logger.info("Trash bin found: {}", trashBin.get());
        } else {
            logger.warn("Trash bin with ID {} not found", id);
        }
        return trashBin;
    }

    @Override
    public List<Trashbin> findAllTrashBins() throws ExecutionException, InterruptedException {
        logger.info("Fetching all trash bins");
        List<Trashbin> trashBins = trashBinRepository.getAllTrashbins();
        logger.info("Found {} trash bins", trashBins.size());
        return trashBins;
    }

    @Override
    public List<Trashbin> trashBinsToCollect(String facilityId) throws ExecutionException, InterruptedException {
        logger.info("Selecting trash bins to collect for facility ID: {}", facilityId);
        List<Trashbin> allBins = trashBinRepository.getAllTrashbinsByFacId(facilityId);
        allBins.sort(Comparator.comparing(Trashbin::getWasteLevel).reversed());

        List<Trashbin> selectedBins = new ArrayList<>();
        double currentWeight = 0;

        for (Trashbin bin : allBins) {
            double filledWeight = bin.getWeight();
            if (currentWeight + filledWeight <= MAX_TRACTOR_WEIGHT) {
                selectedBins.add(bin);
                currentWeight += filledWeight;
                logger.debug("Selected trash bin ID: {} with weight: {}", bin.getTrashbinId(), filledWeight);
            } else {
                logger.debug("Skipping trash bin ID: {} as it exceeds the weight limit", bin.getTrashbinId());
                break;
            }
        }

        logger.info("Selected {} trash bins for collection with a total weight of {}", selectedBins.size(), currentWeight);
        return selectedBins;
    }

    @Override
    public List<Trashbin> findFullTrashBins() throws ExecutionException, InterruptedException {
        logger.info("Fetching all full trash bins");
        List<Trashbin> fullBins = trashBinRepository.getFullTrashbins();
        logger.info("Found {} full trash bins", fullBins.size());
        return fullBins;
    }



    @Override
    public void updateTrashBin(String id, Trashbin trashBin) throws ExecutionException, InterruptedException {

    }

    @Override
    public void deleteTrashBin(String id) throws ExecutionException, InterruptedException {

    }
}
