package com.csse.repo;

import com.csse.DTO.HouseholdUser;
import com.csse.DTO.WasteCollectionPersonal;
import com.csse.controller.WasteCollectionPersonalController;
import com.csse.firebase.FirestoreOperations;
import com.csse.repo.RepoInterface.ICollecitonPersonRepo;
import com.csse.util.Iutil.IdManagementStrategy;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.WASTE_COLLECTION_PERSON_COLLECTION_NAME;
@Repository
public class WasteCollectionPersonalRepo implements ICollecitonPersonRepo {
    private final Firestore firestore;
    private final FirestoreOperations dbOperations;
    private final IdManagementStrategy idManager;



    public WasteCollectionPersonalRepo(Firestore firestore, FirestoreOperations dbOperations, IdManagementStrategy idManager) {
        this.firestore = firestore;
        this.dbOperations = dbOperations;
        this.idManager = idManager;
    }

    @Override
    public String createWasteCollectionPersonal(WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException {
        String userid;
        do {
            userid = idManager.generateId();
        } while (!idManager.validateId(WASTE_COLLECTION_PERSON_COLLECTION_NAME, userid));
        wasteCollectionPersonal.setUserid(userid);
        dbOperations.saveDocument(WASTE_COLLECTION_PERSON_COLLECTION_NAME, userid, wasteCollectionPersonal);
        return userid;
    }
    @Override
    public Optional<WasteCollectionPersonal> getuser(String userId) throws ExecutionException, InterruptedException {
        WasteCollectionPersonal user = dbOperations.getDocument(
                WASTE_COLLECTION_PERSON_COLLECTION_NAME,
                userId,
                WasteCollectionPersonal.class
        );
        return Optional.ofNullable(user);
    }
    @Override
    public void updateWasteCollectionPersonal(String id, WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException {
        dbOperations.saveDocument(WASTE_COLLECTION_PERSON_COLLECTION_NAME, id, wasteCollectionPersonal);
    }
    @Override
    public List<WasteCollectionPersonal> getAllWasteCollectionPersonal() throws ExecutionException, InterruptedException {
        return dbOperations.getAllDocuments(
                WASTE_COLLECTION_PERSON_COLLECTION_NAME,
                WasteCollectionPersonal.class
        );
    }
    @Override
    public void deleteWasteCollectionPersonal(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(WASTE_COLLECTION_PERSON_COLLECTION_NAME).document(id);
        ApiFuture<WriteResult> result = docRef.delete();
        result.get();
    }

}

