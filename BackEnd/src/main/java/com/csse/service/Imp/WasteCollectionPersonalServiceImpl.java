package com.csse.service.Imp;

import com.csse.DTO.Trashbin;
import com.csse.DTO.WasteCollectionPersonal;
import com.csse.repo.CityRepository;
import com.csse.repo.TrashBinRepository;
import com.csse.repo.WasteCollectionPersonalRepo;
import com.csse.service.WasteCollectionPresonalService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class WasteCollectionPersonalServiceImpl implements WasteCollectionPresonalService {

    private final WasteCollectionPersonalRepo wasteCollectionPersonalRepo;
    private final TrashBinRepository trashBinRepository;

    public WasteCollectionPersonalServiceImpl(WasteCollectionPersonalRepo wasteCollectionPersonalRepo, TrashBinRepository trashBinRepository) {
        this.wasteCollectionPersonalRepo = wasteCollectionPersonalRepo;
        this.trashBinRepository = trashBinRepository;
    }

    @Override
    public String createWasteCollectionPersonal(WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException {
        return wasteCollectionPersonalRepo.createWasteCollectionPersonal(wasteCollectionPersonal);
    }

    @Override
    public String updateWasteCollected(String id, String userid) throws ExecutionException, InterruptedException {
        Trashbin trashbin = trashBinRepository.findTrashbinById(id).get();
        trashbin.setCollected(true);
        trashbin.setAssigned(false);
        trashbin.setWasteLevel(0);
        trashBinRepository.updateTrashbin(trashbin);
        WasteCollectionPersonal user = wasteCollectionPersonalRepo.getuser(userid).get();
        user.setWasteCollected(user.getWasteCollected()+1);
        wasteCollectionPersonalRepo.updateWasteCollectionPersonal(id,user);
        return id;
    }


}
