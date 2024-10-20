package com.csse.repo;

import com.csse.DTO.City;
import com.csse.DTO.ProcessingFacility;
import com.csse.firebase.FirestoreOperations;
import com.csse.repo.RepoInterface.IProcessingFacility;
import com.csse.util.Iutil.IdManagementStrategy;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import static com.csse.common.CommonConstraints.P_FACILITY_COLLECTION_NAME;


@Repository
public class ProcessingFacilityRepository implements IProcessingFacility {

    private final FirestoreOperations dbOperations;
    private final IdManagementStrategy idManager;

    public ProcessingFacilityRepository(FirestoreOperations dbOperations, IdManagementStrategy idManager) {
        this.dbOperations = dbOperations;
        this.idManager = idManager;
    }


    public ProcessingFacility createFacility(ProcessingFacility facility) throws ExecutionException, InterruptedException {
        String facilityId;

        do{
            facilityId = idManager.generateId();
        }while(!idManager.validateId(P_FACILITY_COLLECTION_NAME, facilityId));

        facility.setFacilityid(facilityId);
        dbOperations.saveDocument(P_FACILITY_COLLECTION_NAME, facilityId, facility);
        return facility;
    }

    public Optional<ProcessingFacility> findFacilityById(String id) throws ExecutionException, InterruptedException {
        return Optional.ofNullable(dbOperations.getDocument(P_FACILITY_COLLECTION_NAME, id, ProcessingFacility.class));
    }

    public List<ProcessingFacility> findAllProcessingFacilities() throws ExecutionException, InterruptedException {
        return dbOperations.getAllDocuments(P_FACILITY_COLLECTION_NAME, ProcessingFacility.class);
    }

    public void updateProcessingFacility(ProcessingFacility facility) throws ExecutionException, InterruptedException {
        dbOperations.saveDocument(P_FACILITY_COLLECTION_NAME, facility.getFacilityid(), facility);
    }

    public void deleteProcessingFacility(String id) throws ExecutionException, InterruptedException {
        dbOperations.deleteDocument(P_FACILITY_COLLECTION_NAME, id);
    }
}
