package com.csse.service.Imp;

import com.csse.DTO.Trashbin;
import com.csse.repo.TrashBinRepository;
import com.csse.service.TrashBinService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
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
        return null;
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
