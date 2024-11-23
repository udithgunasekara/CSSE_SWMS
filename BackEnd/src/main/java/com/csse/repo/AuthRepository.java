//package com.csse.repo;
//
//import com.csse.DTO.BusinessUser;
//import com.csse.DTO.HouseholdUser;
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.WriteResult;
//import com.google.firebase.cloud.FirestoreClient;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class AuthRepository {
//
//    private final Firestore firestore;// use this firestore initalizor
//
//    public AuthRepository(Firestore firestore) {
//        this.firestore = firestore;
//    }
//    private static final String COLLECTION_BUSINESS = "business";
//    private static final String COLLECTION_HOUSEHOLDER = "householder";
//
//    public String saveBusinessUser(BusinessUser businessUser) {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> future = dbFirestore.collection(COLLECTION_BUSINESS).document(businessUser.getUserid()).set(businessUser);
//        try {
//            return future.get().getUpdateTime().toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error saving business user";
//        }
//    }
//
//    public String saveHouseholdUser(HouseholdUser householdUser) {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> future = dbFirestore.collection(COLLECTION_HOUSEHOLDER).document(householdUser.getUserid()).set(householdUser);
//        try {
//            return future.get().getUpdateTime().toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error saving household user";
//        }
//    }
//}


package com.csse.repo;

import com.csse.DTO.BusinessUser;
import com.csse.DTO.HouseholdUser;
import com.csse.DTO.LoginRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class AuthRepository{

    private final Firestore firestore;

    public AuthRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    private static final String COLLECTION_BUSINESS = "business";
    private static final String COLLECTION_HOUSEHOLDER = "householder";
    private static final String COLLECTION_COLLECTOR = "collector";
    private static final String COLLECTION_AUTHORITY = "authority";

    public String saveBusinessUser(BusinessUser businessUser) {
        ApiFuture<WriteResult> future = firestore.collection(COLLECTION_BUSINESS)
                .document(businessUser.getUserid()).set(businessUser);
        try {
            return future.get().getUpdateTime().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving business user";
        }
    }

    public String saveHouseholdUser(HouseholdUser householdUser) {
        ApiFuture<WriteResult> future = firestore.collection(COLLECTION_HOUSEHOLDER)
                .document(householdUser.getUserid()).set(householdUser);
        try {
            return future.get().getUpdateTime().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving household user";
        }
    }

    public Object getUserByEmail(String email, String userAccountType) {
        String collection = userAccountType.equals("business") ? COLLECTION_BUSINESS : COLLECTION_HOUSEHOLDER;
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection(collection).whereEqualTo("email", email).get();
            QuerySnapshot querySnapshot = future.get();
            if (!querySnapshot.isEmpty()) {
                // Assuming we just want the first document
                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                if (userAccountType.equals("business")) {
                    return document.toObject(BusinessUser.class);
                } else {
                    return document.toObject(HouseholdUser.class);
                }
            } else {
                return null; // No user found
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


}
