package com.csse.Service.Imp;

import com.csse.DTO.SpecialWasteDTO;
import com.csse.Service.SpecialWasteService;
import com.csse.repo.SpecialWasteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
@Service
public class SpecialWasteServiceImpl implements SpecialWasteService {


    private final SpecialWasteRepository specialWasteRepository;

    public SpecialWasteServiceImpl(SpecialWasteRepository specialWasteRepository) {
        this.specialWasteRepository = specialWasteRepository;
    }

    // Constructor injection of the repository
    @Override
   public Optional<SpecialWasteDTO> createSpecialWaste(SpecialWasteDTO specialWaste) {
    try {
        // Set the current date
        specialWaste.setDate(java.time.LocalDate.now().toString());

        // Save special waste details to Firestore
        specialWaste.setStatus("Pending");
        specialWasteRepository.saveSpecialWaste(specialWaste);

        return Optional.of(specialWaste);
    } catch (Exception e) {
        System.err.println("Error while saving special waste details: " + e.getMessage());
        return Optional.empty();
    }
}

    // Example notification method
    private void sendNotification(SpecialWasteDTO specialWaste) {
        System.out.println("Notification sent for special waste: " + specialWaste.getId());
    }

    @Override
    public List<SpecialWasteDTO> getAllSpecialWastes() throws ExecutionException, InterruptedException {
        return specialWasteRepository.getAllSpecialWastes();
    }

    //status update function
    @Override
    public void updateStatus(String id, String status) {
        specialWasteRepository.updateStatus(id, status);
    }

    //get special waste by id
    @Override
    public List<SpecialWasteDTO> getSpecialWasteByUserId(String userId) throws ExecutionException, InterruptedException {
        List<SpecialWasteDTO> specialWastes = specialWasteRepository.getSpecialWasteByUserId(userId);
        return specialWastes;
    }

}
