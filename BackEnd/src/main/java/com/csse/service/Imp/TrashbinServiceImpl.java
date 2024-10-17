package com.csse.service.Imp;

import com.csse.DTO.Trashbin;
import com.csse.repo.TrashBinRepository;
import com.csse.service.TrashBinService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.MAX_TRACTOR_WEIGHT;

@Service
public class TrashbinServiceImpl implements TrashBinService {

    private final TrashBinRepository trashBinRepository;

    public TrashbinServiceImpl(TrashBinRepository trashBinRepository) {
        this.trashBinRepository = trashBinRepository;
    }

    @Override
    public String createTrashBin(Trashbin trashBin) throws ExecutionException, InterruptedException {
        return trashBinRepository.createTrashBin(trashBin);
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
    public List<Trashbin> trashBinsToCollect() throws ExecutionException, InterruptedException {
        // Fetch all available trash bins
        List<Trashbin> allBins = trashBinRepository.getAllTrashbins();

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
