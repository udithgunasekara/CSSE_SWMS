package com.csse.Service;

import com.csse.DTO.SpecialWasteDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;



public interface SpecialWasteService {
    Optional<SpecialWasteDTO> createSpecialWaste(SpecialWasteDTO specialWaste) throws ExecutionException, InterruptedException;

    List<SpecialWasteDTO> getAllSpecialWastes() throws ExecutionException, InterruptedException;

    void updateStatus(String id, String status);

    List<SpecialWasteDTO> getSpecialWasteByUserId(String userId) throws ExecutionException, InterruptedException;
}
