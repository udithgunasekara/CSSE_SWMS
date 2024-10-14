package com.csse.repo;

import com.csse.DTO.Collection_model;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.COL_MODEL_COLLECTION_NAME;
@Repository
public class CollectionModelRepository {
    private final Firestore firestore;

    public CollectionModelRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    //get all collection models details
    public List<Collection_model> getAllCollectionModels() throws ExecutionException, InterruptedException {
        return firestore.collection(COL_MODEL_COLLECTION_NAME).get().get().toObjects(Collection_model.class);
    }

}
