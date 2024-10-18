package com.csse.repo;

import com.csse.DTO.CollectionHistory;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
@Repository
public class CollectionHistoryRepository {
    private static final String COLLECTION_NAME = "collectionhistory";
    private final Firestore firestore;

    public CollectionHistoryRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public String createNewCollectionHistor(CollectionHistory history) throws ExecutionException, InterruptedException {
        String historyid = generateuniqueuserid();
        history.setHistoryID(historyid);
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(historyid);
        ApiFuture<WriteResult> result = docRef.set(history);
        result.get(); // Wait for the operation to complete
        return docRef.getId();
    }


    public Optional<CollectionHistory> getHistory(String historyId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(historyId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return Optional.ofNullable(document.toObject(CollectionHistory.class));
        }
        return Optional.empty();
    }

    public List<CollectionHistory> getAllHistory(String userid) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);
        Query query = collectionReference.whereEqualTo("collecterID", userid);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> document = future.get().getDocuments();
        List<CollectionHistory> historylist = new ArrayList<>();
        for (QueryDocumentSnapshot doc : document) {
            historylist.add(doc.toObject(CollectionHistory.class));
        }
        return historylist;
    }

    public List<CollectionHistory> getAllHistory() throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);
        ApiFuture<QuerySnapshot> future = collectionReference.get();
        List<QueryDocumentSnapshot> document = future.get().getDocuments();
        List<CollectionHistory> historylist = new ArrayList<>();
        for (QueryDocumentSnapshot doc : document) {
            historylist.add(doc.toObject(CollectionHistory.class));
        }
        return historylist;
    }

    public void deleteAllHistory(String userid) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);
        Query query = collectionReference.whereEqualTo("collecterID", userid);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> document = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : document) {
            doc.getReference().delete();
        }
    }

    private String generateuniqueuserid() throws ExecutionException, InterruptedException {
        String historyid;
        do {
            historyid = UUID.randomUUID().toString().substring(0,8);
        } while (getHistory(historyid).isPresent());
        return historyid;
    }

    public List<QueryDocumentSnapshot> historyOfPastWeek() throws ExecutionException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusDays(7);

        String nowStr = now.toString();
        String oneWeekAgoStr = oneWeekAgo.toString();

        CollectionReference historyRef = firestore.collection(COLLECTION_NAME);
        Query query = historyRef.whereGreaterThanOrEqualTo("date",oneWeekAgoStr).whereLessThanOrEqualTo("date",nowStr);
        ApiFuture<QuerySnapshot> future = query.get();
        return future.get().getDocuments();
    }
}
