package com.csse.repo.RepoInterface;

import com.csse.DTO.WasteCollectionPersonal;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ICollecitonPersonRepo {
    public String createWasteCollectionPersonal(WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException;
    public Optional<WasteCollectionPersonal> getuser(String userId) throws ExecutionException, InterruptedException;
    public void updateWasteCollectionPersonal(String id, WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException;
    public List<WasteCollectionPersonal> getAllWasteCollectionPersonal() throws ExecutionException, InterruptedException;
    public void deleteWasteCollectionPersonal(String id) throws ExecutionException, InterruptedException;

}
