package com.csse.repo;

import com.csse.DTO.HouseholdUser;
import com.csse.DTO.WasteCollectionPersonal;
import com.csse.controller.WasteCollectionPersonalController;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import shaded_package.javax.validation.constraints.Null;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.CITY_COLLECTION_NAME;
@Repository
public class WasteCollectionPersonalRepo {
    private static final String COLLECTION_NAME = "wastecollectionpersonal";
    private final Firestore firestore;

    public WasteCollectionPersonalRepo(Firestore firestore) {
        this.firestore = firestore;
    }

    public String createWasteCollectionPersonal(WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException {
        String userid = generateuniqueuserid();
        wasteCollectionPersonal.setUserid(userid);
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(userid);
        ApiFuture<WriteResult> result = docRef.set(wasteCollectionPersonal);
        result.get(); // Wait for the operation to complete
        return docRef.getId();
    }


    public Optional<WasteCollectionPersonal> getuser(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return Optional.ofNullable(document.toObject(WasteCollectionPersonal.class));
        }
        return Optional.empty();
    }

    public void updateWasteCollectionPersonal(String id, WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<WriteResult> result = docRef.set(wasteCollectionPersonal);
        result.get();
    }



    private String generateuniqueuserid() throws ExecutionException, InterruptedException {
        String userid;
        do {
            userid = UUID.randomUUID().toString().substring(0,8);
        } while (getuser(userid).isPresent());
        return userid;
    }

}

