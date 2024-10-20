package com.csse.service.Imp;

import com.csse.DTO.City;
import com.csse.DTO.ProcessingFacility;
import com.csse.repo.ProcessingFacilityRepository;
import com.csse.repo.RepoInterface.IProcessingFacility;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class ProcessingFacilityService {
    private final IProcessingFacility processingFacilityRepository;

    public ProcessingFacilityService(ProcessingFacilityRepository processingFacilityRepository) {
        this.processingFacilityRepository = processingFacilityRepository;
    }

    public ProcessingFacility createFacility(ProcessingFacility facility) throws ExecutionException, InterruptedException {
        return processingFacilityRepository.createFacility(facility);
    }

    public Optional<ProcessingFacility> findFacilityById(String id) throws ExecutionException, InterruptedException {
        return processingFacilityRepository.findFacilityById(id);
    }

    public List<ProcessingFacility> findAllProcessingFacilities() throws ExecutionException, InterruptedException {
        return processingFacilityRepository.findAllProcessingFacilities();
    }

    public void updateProcessingFacility(String id,ProcessingFacility facility) throws ExecutionException, InterruptedException {
        Optional<ProcessingFacility> facilityOptional = processingFacilityRepository.findFacilityById(id);
        if (facilityOptional.isPresent()) {
            ProcessingFacility selectedFacility = facilityOptional.get();
            selectedFacility.setFacilityAddress(facility.getFacilityAddress());
            selectedFacility.setLocations(facility.getLatitude(),facility.getLongitude());
            processingFacilityRepository.updateProcessingFacility(selectedFacility);
        } else {
            throw new IllegalArgumentException("Processing Facility not found with id: " + id);
        }
    }

    public void deleteProcessingFacility(String id) throws ExecutionException, InterruptedException {
        processingFacilityRepository.deleteProcessingFacility(id);
    }

}
