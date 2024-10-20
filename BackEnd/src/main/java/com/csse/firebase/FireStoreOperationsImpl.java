package com.csse.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
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
    @Override
    public <T> List<T> getDocumentsByFieldWithErrorHandling(
            String collectionName,
            String fieldName,
            Object fieldValue,
            Class<T> classType) throws ExecutionException, InterruptedException {

        try {
            CollectionReference collectionRef = firestore.collection(collectionName);

            // Validate inputs
            if (fieldName == null || fieldName.trim().isEmpty()) {
                throw new IllegalArgumentException("Field name cannot be null or empty");
            }
            if (fieldValue == null) {
                throw new IllegalArgumentException("Field value cannot be null");
            }

            // Create and execute query
            Query query = collectionRef.whereEqualTo(fieldName, fieldValue);
            ApiFuture<QuerySnapshot> future = query.get();

            // Handle timeout
            QuerySnapshot querySnapshot = future.get();
            if (querySnapshot == null) {
                throw new ExecutionException("Query returned null result", null);
            }

            // Process results
            List<T> results = new ArrayList<>();
            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                try {
                    T item = document.toObject(classType);
                    if (item != null) {
                        results.add(item);
                    }
                } catch (Exception e) {
                    // Log the error but continue processing other documents
                    System.err.println("Error converting document " + document.getId() + ": " + e.getMessage());
                }
            }

            return results;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            throw e;
        } catch (ExecutionException e) {
            throw new ExecutionException("Error executing Firestore query", e);
        }
    }

    public <T> List<T> getDocumentsByDateRange(
            String collectionName,
            String dateField,
            String startDate,
            String endDate,
            Class<T> classType) throws ExecutionException, InterruptedException {

        // Get collection reference
        CollectionReference collectionRef = firestore.collection(collectionName);

        // Create query with date range
        Query query = collectionRef
                .whereGreaterThanOrEqualTo(dateField, startDate)
                .whereLessThanOrEqualTo(dateField, endDate);

        // Execute query
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        // Convert results to requested type
        List<T> results = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            T item = document.toObject(classType);
            results.add(item);
        }

        return results;
    }

}
