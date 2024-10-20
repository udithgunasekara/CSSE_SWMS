package com.csse.repo;

import com.csse.DTO.CollectionHistory;
import com.csse.firebase.FirestoreOperations;
import com.csse.repo.RepoInterface.ICollectionHistory;
import com.csse.util.Iutil.IdManagementStrategy;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import static com.csse.common.CommonConstraints.COLLECTION_HISTORY_COLLECTION_NAME;
@Repository
public class CollectionHistoryRepository  implements ICollectionHistory {
    private final Firestore firestore;
    private final FirestoreOperations dbOperations;
    private final IdManagementStrategy idManager;

    public CollectionHistoryRepository(Firestore firestore, FirestoreOperations dbOperations, IdManagementStrategy idManager) {
        this.firestore = firestore;
        this.dbOperations = dbOperations;
        this.idManager = idManager;
    }
    @Override
    public String createNewCollectionHistor(CollectionHistory history) throws ExecutionException, InterruptedException {
        String historyid;
        do {
            historyid = idManager.generateId();
        } while (!idManager.validateId(COLLECTION_HISTORY_COLLECTION_NAME, historyid));

        history.setHistoryID(historyid);
        dbOperations.saveDocument(COLLECTION_HISTORY_COLLECTION_NAME, historyid, history);
        return historyid;
    }

    @Override
    public Optional<CollectionHistory> getHistory(String historyId) throws ExecutionException, InterruptedException {
        CollectionHistory history = dbOperations.getDocument(COLLECTION_HISTORY_COLLECTION_NAME, historyId, CollectionHistory.class);
        return Optional.ofNullable(history);
    }

    @Override
    public List<CollectionHistory> getAllHistoryByCollector(String userid) throws ExecutionException, InterruptedException {
        return dbOperations.getDocumentsByFieldWithErrorHandling(COLLECTION_HISTORY_COLLECTION_NAME, "collecterID", userid, CollectionHistory.class);
    }

    @Override
    public List<CollectionHistory> getAllHistory() throws ExecutionException, InterruptedException {
        return dbOperations.getAllDocuments(COLLECTION_HISTORY_COLLECTION_NAME, CollectionHistory.class);
    }

    @Override
    public List<CollectionHistory> historyOfPastWeek() throws ExecutionException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusDays(7);

        return dbOperations.getDocumentsByDateRange(
                COLLECTION_HISTORY_COLLECTION_NAME,
                "date",
                oneWeekAgo.toString(),
                now.toString(),
                CollectionHistory.class
        );
    }
}
