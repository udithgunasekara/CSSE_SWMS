package com.csse.service;
import com.csse.DTO.Collection_model;
import com.csse.repo.CollectionModelRepository;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.csse.common.CommonConstraints.COL_MODEL_COLLECTION_NAME;

@Service
public class Collection_model_service {

    private final CollectionModelRepository repository;

    public Collection_model_service(CollectionModelRepository repository) {
        this.repository = repository;
    }

   public List<Collection_model> getAllCollectionModels() throws ExecutionException, InterruptedException {
        return repository.getAllCollectionModels();
    }

}
