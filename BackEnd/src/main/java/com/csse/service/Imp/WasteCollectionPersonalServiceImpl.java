package com.csse.service.Imp;

import com.csse.DTO.CollectionHistory;
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
    private final CollectionHistoryServiceImpl collectionHistoryService;

    public WasteCollectionPersonalServiceImpl(WasteCollectionPersonalRepo wasteCollectionPersonalRepo, TrashBinRepository trashBinRepository, CollectionHistoryServiceImpl collectionHistoryService) {
        this.wasteCollectionPersonalRepo = wasteCollectionPersonalRepo;
        this.trashBinRepository = trashBinRepository;
        this.collectionHistoryService = collectionHistoryService;
    }

    @Override
    public String createWasteCollectionPersonal(WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException {
        return wasteCollectionPersonalRepo.createWasteCollectionPersonal(wasteCollectionPersonal);
    }

    @Override
    public String updateWasteCollected(String id, String userid) throws ExecutionException, InterruptedException {
        Trashbin trashbin = trashBinRepository.findTrashbinById(id).get();
        if (trashbin.getWasteLevel() > 0) {
            collectionHistoryService.createNewCollectionHistor(userid,id, trashbin.getWeight());
            trashbin.setCollected(true);
            trashbin.setAssigned(false);
            trashbin.setWasteLevel(0);
            trashbin.setWeight(0);
            trashbin.setFull(false);
            trashBinRepository.updateTrashbin(trashbin);
            WasteCollectionPersonal user = wasteCollectionPersonalRepo.getuser(userid).get();
            user.setWasteCollected(user.getWasteCollected()+1);
            wasteCollectionPersonalRepo.updateWasteCollectionPersonal(userid,user);

        }else{
            throw new IllegalArgumentException("Trashbin is empty");
        }
        return id;
    }


}
