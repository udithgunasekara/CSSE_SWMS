package com.csse.repo;

import com.csse.DTO.HouseholdUser;
import com.csse.firebase.FirestoreOperations;
import com.csse.util.Iutil.IdManagementStrategy;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import static com.csse.common.CommonConstraints.HOUSEHOLD_USER_COLLECTION_NAME;

public class HouseholdUserRepository {
    private final FirestoreOperations dbOperations;
    private final IdManagementStrategy idManager;

    public HouseholdUserRepository(Firestore firestore, FirestoreOperations dbOperations, IdManagementStrategy idManager) {
        this.dbOperations = dbOperations;
        this.idManager = idManager;
    }

    // Create operation
    public String createHouseholdUser(HouseholdUser user) throws ExecutionException, InterruptedException {
        String userid;
        do {
            userid = idManager.generateId();
        } while (!idManager.validateId(HOUSEHOLD_USER_COLLECTION_NAME, userid));
        user.setUserid(userid);
        dbOperations.saveDocument(HOUSEHOLD_USER_COLLECTION_NAME, userid, user);
        return userid;
    }

    // Read operation
    public Optional<HouseholdUser> getHouseholdUser(String userId) throws ExecutionException, InterruptedException {
        HouseholdUser user = dbOperations.getDocument(HOUSEHOLD_USER_COLLECTION_NAME, userId, HouseholdUser.class);
        return Optional.ofNullable(user);
    }

    // Update operation
    public void updateHouseholdUser(String userId, HouseholdUser updatedUser) throws ExecutionException, InterruptedException {
        dbOperations.saveDocument(HOUSEHOLD_USER_COLLECTION_NAME, userId, updatedUser);
    }

    // Delete operation
    public void deleteHouseholdUser(String userId) throws ExecutionException, InterruptedException {
        dbOperations.deleteDocument(HOUSEHOLD_USER_COLLECTION_NAME, userId);
    }

    // List all household users
    public List<HouseholdUser> getAllHouseholdUsers() throws ExecutionException, InterruptedException {
        return dbOperations.getAllDocuments(HOUSEHOLD_USER_COLLECTION_NAME, HouseholdUser.class);
    }

}
