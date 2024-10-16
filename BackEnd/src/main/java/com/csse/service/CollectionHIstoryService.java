package com.csse.service;

import com.csse.DTO.CollectionHistory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface CollectionHIstoryService {
    String createNewCollectionHistor(String uesrid,String tagid) throws ExecutionException, InterruptedException;

    Optional<CollectionHistory> getHistory(String historyId) throws ExecutionException, InterruptedException;

    List<CollectionHistory> getAllHistory(String userid) throws ExecutionException, InterruptedException;

    void deleteAllHistory(String userid) throws ExecutionException, InterruptedException;
}
