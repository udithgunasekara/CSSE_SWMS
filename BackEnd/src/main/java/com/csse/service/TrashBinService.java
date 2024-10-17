package com.csse.service;

import com.csse.DTO.Trashbin;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface TrashBinService {

    public String createTrashBin(Trashbin trashBin) throws ExecutionException, InterruptedException;

    public Optional<Trashbin> findTrashBinById(String id) throws ExecutionException, InterruptedException;

    public List<Trashbin> findAllTrashBins() throws ExecutionException, InterruptedException;

    public List<Trashbin> findFullTrashBins() throws ExecutionException, InterruptedException;

    public List<Trashbin> trashBinsToCollect() throws ExecutionException, InterruptedException;

    public void updateTrashBin(String id,Trashbin trashBin) throws ExecutionException, InterruptedException;

    public void deleteTrashBin(String id) throws ExecutionException, InterruptedException;

//    public String updateTrashBinCollected(String id) throws ExecutionException, InterruptedException;


}
