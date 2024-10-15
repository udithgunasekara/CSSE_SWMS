package com.csse.repo;

import com.csse.DTO.Trashbin;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.TRASHBIN_COLLECTION_NAME;
@Repository
public class TrashBinRepository {
    private final Firestore firestore;

    public TrashBinRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public String createTrashBin(Trashbin trashbin) throws ExecutionException, InterruptedException {
        String trashbinId = generateUniqueTrashbinId();
        trashbin.setTrashbinId(trashbinId);
        firestore.collection(TRASHBIN_COLLECTION_NAME).document(trashbinId).set(trashbin).get();
        return trashbinId;
    }

    public List<Trashbin> getAllTrashbins() throws ExecutionException, InterruptedException {
        return firestore.collection(TRASHBIN_COLLECTION_NAME).get().get().toObjects(Trashbin.class);
    }

    public Optional<Trashbin> findTrashbinById(String id) throws ExecutionException, InterruptedException {
        return Optional.ofNullable(firestore.collection(TRASHBIN_COLLECTION_NAME).document(id).get().get().toObject(Trashbin.class));
    }

    public void updateTrashbin(Trashbin trashbin) throws ExecutionException, InterruptedException {
        firestore.collection(TRASHBIN_COLLECTION_NAME).document(trashbin.getTrashbinId()).set(trashbin).get();
    } 

    public void deleteTrashbin(String id) throws ExecutionException, InterruptedException {
        firestore.collection(TRASHBIN_COLLECTION_NAME).document(id).delete().get();
    }

    private String generateUniqueTrashbinId() throws ExecutionException, InterruptedException {
        String binId;
        do {
            binId = UUID.randomUUID().toString().substring(0,8);
        } while (findTrashbinById(binId).isPresent());
        return binId;
    }
}
