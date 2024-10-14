package com.csse.repo;

import com.csse.DTO.City;
import com.csse.DTO.ProcessingFacility;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import static com.csse.common.CommonConstraints.P_FACILITY_COLLECTION_NAME;


@Repository
public class ProcessingFacilityRepository {

    private final Firestore firestore;

    public ProcessingFacilityRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public String createFacility(ProcessingFacility facility) throws ExecutionException, InterruptedException {
        String facilityId = generateUniqueFacilityId();
        facility.setFacilityid(facilityId);
        firestore.collection(P_FACILITY_COLLECTION_NAME).document(facilityId).set(facility).get();
        return facilityId;
    }

    public Optional<ProcessingFacility> findFacilityById(String id) throws ExecutionException, InterruptedException {
        return Optional.ofNullable(firestore.collection(P_FACILITY_COLLECTION_NAME).document(id).get().get().toObject(ProcessingFacility.class));
    }

    public List<ProcessingFacility> findAllProcessingFacilities() throws ExecutionException, InterruptedException {
        return firestore.collection(P_FACILITY_COLLECTION_NAME).get().get().toObjects(ProcessingFacility.class);
    }

    public void updateProcessingFacility(ProcessingFacility facility) throws ExecutionException, InterruptedException {
        firestore.collection(P_FACILITY_COLLECTION_NAME).document(facility.getFacilityid()).set(facility).get();
    }

    public void deleteProcessingFacility(String id) throws ExecutionException, InterruptedException {
        firestore.collection(P_FACILITY_COLLECTION_NAME).document(id).delete().get();
    }

    private String generateUniqueFacilityId() throws ExecutionException, InterruptedException {
        String facilityId;
        do {
            facilityId = UUID.randomUUID().toString().substring(0,8);
        } while (findFacilityById(facilityId).isPresent());
        return facilityId;
    }
}
