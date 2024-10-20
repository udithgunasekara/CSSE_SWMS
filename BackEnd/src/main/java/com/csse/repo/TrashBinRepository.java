package com.csse.repo;

import com.csse.DTO.Trashbin;
import com.csse.repo.RepoInterface.ITrashBinRepository;
import com.google.cloud.firestore.Firestore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.TRASHBIN_COLLECTION_NAME;

@Repository
public class TrashBinRepository implements ITrashBinRepository {

    private static final Logger logger = LoggerFactory.getLogger(TrashBinRepository.class);

    private final Firestore firestore;

    public TrashBinRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public String createTrashBin(Trashbin trashbin) throws ExecutionException, InterruptedException {
        logger.info("Creating a new trash bin: {}", trashbin);
        String trashbinId = generateUniqueTrashbinId();
        trashbin.setTrashbinId(trashbinId);
        firestore.collection(TRASHBIN_COLLECTION_NAME).document(trashbinId).set(trashbin).get();
        logger.info("Trash bin created successfully with ID: {} {}", trashbinId, trashbin);
        return trashbinId;
    }

    public List<Trashbin> getAllTrashbins() throws ExecutionException, InterruptedException {
        logger.info("Fetching all trash bins");
        List<Trashbin> trashbins = firestore.collection(TRASHBIN_COLLECTION_NAME).get().get().toObjects(Trashbin.class);
        logger.info("Found {} trash bins", trashbins.size());
        return trashbins;
    }

    public List<Trashbin> getAllTrashbinsByFacId(String facilityId) throws ExecutionException, InterruptedException {
        logger.info("Fetching all trash bins for facility ID: {}", facilityId);
        List<Trashbin> trashbins = firestore.collection(TRASHBIN_COLLECTION_NAME).whereEqualTo("facilityId", facilityId).get().get().toObjects(Trashbin.class);
        logger.info("Found {} trash bins for facility ID: {}", trashbins.size(), facilityId);
        return trashbins;
    }

    public List<Trashbin> getFullTrashbins() throws ExecutionException, InterruptedException {
        logger.info("Fetching all full trash bins");
        List<Trashbin> fullTrashbins = firestore.collection(TRASHBIN_COLLECTION_NAME)
                .whereEqualTo("full", true)
                .get().get()
                .toObjects(Trashbin.class);
        logger.info("Found {} full trash bins", fullTrashbins.size());
        return fullTrashbins;
    }

    public Optional<Trashbin> findTrashbinById(String id) throws ExecutionException, InterruptedException {
        logger.info("Finding trash bin by ID: {}", id);
        Optional<Trashbin> trashbin = Optional.ofNullable(firestore.collection(TRASHBIN_COLLECTION_NAME).document(id).get().get().toObject(Trashbin.class));
        if (trashbin.isPresent()) {
            logger.info("Trash bin found: {}", trashbin.get());
        } else {
            logger.warn("Trash bin with ID {} not found", id);
        }
        return trashbin;
    }

    public void updateTrashbin(Trashbin trashbin) throws ExecutionException, InterruptedException {
        logger.info("Updating trash bin with ID: {}", trashbin.getTrashbinId());
        firestore.collection(TRASHBIN_COLLECTION_NAME).document(trashbin.getTrashbinId()).set(trashbin).get();
        logger.info("Trash bin with ID {} updated successfully", trashbin.getTrashbinId());
    }

    public void deleteTrashbin(String id) throws ExecutionException, InterruptedException {
        logger.info("Deleting trash bin with ID: {}", id);
        firestore.collection(TRASHBIN_COLLECTION_NAME).document(id).delete().get();
        logger.info("Trash bin with ID {} deleted successfully", id);
    }

    public String generateUniqueTrashbinId() throws ExecutionException, InterruptedException {
        logger.info("Generating a unique ID for the trash bin");
        String binId;
        do {
            binId = UUID.randomUUID().toString().substring(0, 8);
        } while (findTrashbinById(binId).isPresent());
        logger.info("Generated unique ID: {}", binId);
        return binId;
    }
}