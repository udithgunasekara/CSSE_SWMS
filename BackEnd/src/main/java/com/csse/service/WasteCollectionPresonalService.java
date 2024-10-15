package com.csse.service;

import com.csse.DTO.WasteCollectionPersonal;

import java.util.concurrent.ExecutionException;

public interface WasteCollectionPresonalService {

    public String createWasteCollectionPersonal(WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException;

    public String updateWasteCollected(String id,String userid) throws ExecutionException, InterruptedException;
}
