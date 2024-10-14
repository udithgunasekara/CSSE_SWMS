package com.csse.service;
import com.csse.DTO.Collection_model;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.COL_MODEL_COLLECTION_NAME;

@Service
public class Collection_model_service {

    private final Firestore firestore;

    @Autowired
    public Collection_model_service(Firestore firestore) {
        this.firestore = firestore;
    }

    public String saveWasteModel(Collection_model model) throws ExecutionException, InterruptedException {
        CollectionReference models = firestore.collection(COL_MODEL_COLLECTION_NAME);
        WriteResult writeResult = models.document().set(model).get();
        return writeResult.getUpdateTime().toString();
    }

}
