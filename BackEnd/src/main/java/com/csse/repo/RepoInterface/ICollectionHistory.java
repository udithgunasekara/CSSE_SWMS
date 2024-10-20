package com.csse.repo.RepoInterface;

import com.csse.DTO.CollectionHistory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ICollectionHistory {
    public String createNewCollectionHistor(CollectionHistory history) throws ExecutionException, InterruptedException;
    public Optional<CollectionHistory> getHistory(String historyId) throws ExecutionException, InterruptedException;
    public List<CollectionHistory> getAllHistoryByCollector(String userid) throws ExecutionException, InterruptedException;
    public List<CollectionHistory> getAllHistory() throws ExecutionException, InterruptedException;
    public List<CollectionHistory> historyOfPastWeek() throws ExecutionException, InterruptedException;
}
