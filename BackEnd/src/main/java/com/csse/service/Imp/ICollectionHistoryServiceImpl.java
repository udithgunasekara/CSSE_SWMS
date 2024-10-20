package com.csse.service.Imp;

import com.csse.DTO.CollectionHistory;
import com.csse.repo.CollectionHistoryRepository;
import com.csse.repo.RepoInterface.ICollectionHistory;
import com.csse.service.ICollectionHIstoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
@Service
public class ICollectionHistoryServiceImpl implements ICollectionHIstoryService {

    private final ICollectionHistory collectionHistoryRepository;

    public ICollectionHistoryServiceImpl(CollectionHistoryRepository collectionHistoryRepository) {
        this.collectionHistoryRepository = collectionHistoryRepository;
    }

    //create a new history
    @Override
    public String createNewCollectionHistor(String uesrid,String tagid,Double weight) throws ExecutionException, InterruptedException {
        CollectionHistory history = new CollectionHistory();
        history.setCollecterID(uesrid);
        history.setTagid(tagid);
        history.setWeight(weight);
        LocalDate today = LocalDate.now();
        history.setDate(today.toString());
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        history.setTime(currentTime.format(timeFormatter));
        return collectionHistoryRepository.createNewCollectionHistor(history);
    }

    //get a specific history by historyid
    @Override
    public Optional<CollectionHistory> getHistory(String historyId) throws ExecutionException, InterruptedException {
        return collectionHistoryRepository.getHistory(historyId);
    }

    //get all history of a specific user
    @Override
    public List<CollectionHistory> getAllHistory(String userid) throws ExecutionException, InterruptedException {
        return collectionHistoryRepository.getAllHistoryByCollector(userid);
    }

    //!!caution!! do not use this method. created for test purposes only.
    /*@Override
    public void deleteAllHistory(String userid) throws ExecutionException, InterruptedException {
        collectionHistoryRepository.deleteAllHistory(userid);
    }*/


}
