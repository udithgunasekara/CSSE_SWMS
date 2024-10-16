package com.csse.service.Imp;

import com.csse.DTO.CollectionHistory;
import com.csse.repo.CollectionHistoryRepository;
import com.csse.service.CollectionHIstoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
@Service
public class CollectionHistoryServiceImpl implements CollectionHIstoryService {

    private final CollectionHistoryRepository collectionHistoryRepository;

    public CollectionHistoryServiceImpl(CollectionHistoryRepository collectionHistoryRepository) {
        this.collectionHistoryRepository = collectionHistoryRepository;
    }

    @Override
    public String createNewCollectionHistor(String uesrid,String tagid) throws ExecutionException, InterruptedException {
        CollectionHistory history = new CollectionHistory();
        history.setCollecterID(uesrid);
        history.setTagid(tagid);
        LocalDate today = LocalDate.now();
        history.setDate(today.toString());
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        history.setTime(currentTime.format(timeFormatter));
        return collectionHistoryRepository.createNewCollectionHistor(history);
    }

    @Override
    public Optional<CollectionHistory> getHistory(String historyId) throws ExecutionException, InterruptedException {
        return collectionHistoryRepository.getHistory(historyId);
    }

    @Override
    public List<CollectionHistory> getAllHistory(String userid) throws ExecutionException, InterruptedException {
        return collectionHistoryRepository.getAllHistory(userid);
    }

    @Override
    public void deleteAllHistory(String userid) throws ExecutionException, InterruptedException {
        collectionHistoryRepository.deleteAllHistory(userid);
    }
}
