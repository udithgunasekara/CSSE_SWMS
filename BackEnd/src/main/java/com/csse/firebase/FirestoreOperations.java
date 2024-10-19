package com.csse.firebase;

import com.google.cloud.firestore.DocumentReference;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FirestoreOperations {
    DocumentReference getDocumentReference(String collectionName, String documentId);
    <T> T getDocument (String collectionName, String documentId, Class<T> classType) throws ExecutionException,InterruptedException;
    <T> void saveDocument(String collectionName, String id, T document) throws ExecutionException, InterruptedException;
    void deleteDocument(String collectionName, String id) throws ExecutionException, InterruptedException;
    <T> List<T> getAllDocuments(String collectionName, Class<T> classType) throws ExecutionException, InterruptedException;
}
