package com.csse.repo;

import com.csse.DTO.HouseholdUser;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class HouseholdUserRepository {

    private static final String COLLECTION_NAME = "householdUsers";
    private final Firestore firestore;

    public HouseholdUserRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // Create operation
    public String createHouseholdUser(HouseholdUser user) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document();
        ApiFuture<WriteResult> result = docRef.set(user);
        result.get(); // Wait for the operation to complete
        return docRef.getId();
    }

    // Read operation
    public Optional<HouseholdUser> getHouseholdUser(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return Optional.ofNullable(document.toObject(HouseholdUser.class));
        }
        return Optional.empty();
    }

    // Update operation
    public void updateHouseholdUser(String userId, HouseholdUser updatedUser) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(userId);
        ApiFuture<WriteResult> result = docRef.set(updatedUser);
        result.get(); // Wait for the operation to complete
    }

    // Delete operation
    public void deleteHouseholdUser(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(userId);
        ApiFuture<WriteResult> result = docRef.delete();
        result.get(); // Wait for the operation to complete
    }

    // List all household users
    public List<HouseholdUser> getAllHouseholdUsers() throws ExecutionException, InterruptedException {
        List<HouseholdUser> users = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            users.add(document.toObject(HouseholdUser.class));
        }
        return users;
    }

    private String generateUniqueHHUserId() throws ExecutionException, InterruptedException {
        String HHUID;
        do {
            HHUID = UUID.randomUUID().toString().substring(0,8);
        } while (getHouseholdUser(HHUID).isPresent());
        return HHUID;
    }


}
