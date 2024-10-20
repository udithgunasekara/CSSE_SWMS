package com.csse.repo.RepoInterface;

import com.csse.DTO.ProcessingFacility;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface IProcessingFacility {
    ProcessingFacility createFacility(ProcessingFacility facility) throws ExecutionException, InterruptedException;

    Optional<ProcessingFacility> findFacilityById(String id) throws ExecutionException, InterruptedException;

    List<ProcessingFacility> findAllProcessingFacilities() throws ExecutionException, InterruptedException;

    void updateProcessingFacility(ProcessingFacility facility) throws ExecutionException, InterruptedException;

    void deleteProcessingFacility(String id) throws ExecutionException, InterruptedException;
}
