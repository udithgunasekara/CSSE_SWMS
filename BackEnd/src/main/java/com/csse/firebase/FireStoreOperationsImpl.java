package com.csse.firebase;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
@Component
public class FireStoreOperationsImpl implements FirestoreOperations{
    private final Firestore firestore;

    public FireStoreOperationsImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public DocumentReference getDocumentReference(String collectionName, String documentId) {
        return firestore.collection(collectionName).document(documentId);
    }

    @Override
    public <T> T getDocument(String collectionName, String documentId, Class<T> classType) throws ExecutionException, InterruptedException {
        return firestore.collection(collectionName).document(documentId).get().get().toObject(classType);
    }

    @Override
    public <T> void saveDocument(String collectionName, String id, T document) throws ExecutionException, InterruptedException {
         firestore.collection(collectionName).document(id).set(document).get();
    }

    @Override
    public void deleteDocument(String collectionName, String id) throws ExecutionException, InterruptedException {
        firestore.collection(collectionName).document(id).delete().get();
    }

    @Override
    public <T> List<T> getAllDocuments(String collectionName, Class<T> classType) throws ExecutionException, InterruptedException {
        return firestore.collection(collectionName).get().get().toObjects(classType);
    }
}
