package com.csse.service.Imp;

import com.csse.DTO.Trashbin;
import com.csse.repo.RepoInterface.ITrashBinRepository;
import com.csse.repo.TrashBinRepository;
import com.csse.service.ITrashBinFactory;
import com.csse.service.ITrashBinService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.MAX_TRACTOR_WEIGHT;

@Service
public class TrashbinServiceImpl implements ITrashBinService {

    private final ITrashBinRepository trashBinRepository;
    private final ITrashBinFactory trashBinFactory;

    public TrashbinServiceImpl(TrashBinRepository trashBinRepository, ITrashBinFactory trashBinFactory) {
        this.trashBinRepository = trashBinRepository;
        this.trashBinFactory = trashBinFactory;
    }

    @Override
    public String createTrashBin(Trashbin trashBin) throws ExecutionException, InterruptedException {
        return trashBinRepository.createTrashBin(trashBinFactory.createTrashBin(trashBin));
    }

    @Override
    public Optional<Trashbin> findTrashBinById(String id) throws ExecutionException, InterruptedException {
        return Optional.empty();
    }

    @Override
    public List<Trashbin> findAllTrashBins() throws ExecutionException, InterruptedException {
            return trashBinRepository.getAllTrashbins();
    }

    @Override
    public List<Trashbin> trashBinsToCollect(String facilityId) throws ExecutionException, InterruptedException {
        // Fetch all available trash bins
        List<Trashbin> allBins = trashBinRepository.getAllTrashbinsByFacId(facilityId);

        // Sort bins by fill percentage in descending order
        allBins.sort(Comparator.comparing(Trashbin::getWasteLevel).reversed());

        List<Trashbin> selectedBins = new ArrayList<>();
        double currentWeight = 0;

        // Select bins until total weight reaches tractor's capacity
        for (Trashbin bin : allBins) {
            double filledWeight = bin.getWeight();
            if (currentWeight + filledWeight <= MAX_TRACTOR_WEIGHT) {
                selectedBins.add(bin);
                currentWeight += filledWeight;
            } else {
                break;
            }
        }

        return selectedBins;
    }

    @Override
    public List<Trashbin> findFullTrashBins() throws ExecutionException, InterruptedException {
        return trashBinRepository.getFullTrashbins();
    }

    @Override
    public void updateTrashBin(String id, Trashbin trashBin) throws ExecutionException, InterruptedException {

    }

    @Override
    public void deleteTrashBin(String id) throws ExecutionException, InterruptedException {

    }

}
