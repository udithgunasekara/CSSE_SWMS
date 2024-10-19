package com.csse.repo;

import com.csse.DTO.SpecialWasteDTO;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Repository
public class SpecialWasteRepository {
    private final Firestore firestore;
    public SpecialWasteRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public void saveSpecialWaste(SpecialWasteDTO specialWaste) {

        firestore.collection("special_waste").add(specialWaste);
    }

   public List<SpecialWasteDTO> getAllSpecialWastes() throws ExecutionException, InterruptedException {
    List<SpecialWasteDTO> specialWastes = new ArrayList<>();
    ApiFuture<QuerySnapshot> future = firestore.collection("special_waste").get();
    List<QueryDocumentSnapshot> documents = future.get().getDocuments();

    for (QueryDocumentSnapshot document : documents) {
        SpecialWasteDTO specialWaste = document.toObject(SpecialWasteDTO.class);
        specialWaste.setUUID(document.getId()); // Assign the document ID to the UUID field
        specialWastes.add(specialWaste);
    }
    return specialWastes;
}

    public void updateStatus(String id, String status) {

        firestore.collection("special_waste").document(id).update("status", status);
    }

    //waste all get by id
    public List<SpecialWasteDTO> findAll(String userId) {
        List<SpecialWasteDTO> result = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("special_waste").get();

        try {
            QuerySnapshot querySnapshot = future.get();
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                SpecialWasteDTO waste = document.toObject(SpecialWasteDTO.class);
                if (waste.getId().equals(userId)) {
                    result.add(waste);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }


    public List<SpecialWasteDTO> getSpecialWasteByUserId(String userId) throws ExecutionException, InterruptedException {
        // Query all documents in the special_waste collection
        ApiFuture<QuerySnapshot> future = firestore.collection("special_waste").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        // Find the document with matching userId
        List<SpecialWasteDTO> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            SpecialWasteDTO specialWaste = document.toObject(SpecialWasteDTO.class);
            if (userId.equals(specialWaste.getId())) {
                result.add(specialWaste);
            }
        }

        return result;
    }


}
