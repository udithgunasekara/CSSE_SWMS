package com.csse.util;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class UniqueIdValidator {
    private final Firestore firestore;

    public UniqueIdValidator(Firestore firestore) {
        this.firestore = firestore;
    }

    //check whether the id is unique or not
    public Boolean isUniqueIdValid(String collectionName,String id) throws ExecutionException, InterruptedException {
        return !firestore.collection(collectionName)
                .document(id)
                .get()
                .get()
                .exists();
    }
}
