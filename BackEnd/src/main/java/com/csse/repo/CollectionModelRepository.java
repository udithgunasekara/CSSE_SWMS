package com.csse.repo;

import com.csse.DTO.Collection_model;
import com.csse.firebase.FirestoreOperations;
import com.csse.repo.RepoInterface.ICollectionModel;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.COL_MODEL_COLLECTION_NAME;
@Repository
public class CollectionModelRepository implements ICollectionModel {
    private final FirestoreOperations dbOperations;
    public CollectionModelRepository(Firestore firestore, FirestoreOperations dbOperations) {
        this.dbOperations = dbOperations;
    }

    @Override
    //get all collection models details
    public List<Collection_model> getAllCollectionModels() throws ExecutionException, InterruptedException {
        return dbOperations.getAllDocuments(COL_MODEL_COLLECTION_NAME, Collection_model.class);
    }

}
