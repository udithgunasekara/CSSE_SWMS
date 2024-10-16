package com.csse.controller;

import com.csse.DTO.CollectionHistory;
import com.csse.service.CollectionHIstoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/history")
public class CollectionHistoryController {
    private  final CollectionHIstoryService collectionHistoryService;

    public CollectionHistoryController(CollectionHIstoryService collectionHIstoryService) {
        this.collectionHistoryService = collectionHIstoryService;
    }

    // Create a new CollectionHistory
    //url: http://localhost:8080/api/history/create
   /* @PostMapping("/create")
    public ResponseEntity<String> createNewCollectionHistory(@RequestBody CollectionHistory history) {
        try {
            String historyId = collectionHistoryService.createNewCollectionHistor(history);
            return ResponseEntity.ok("Collection history created with ID: " + historyId);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error creating collection history: " + e.getMessage());
        }
    }*/

    // Get a single CollectionHistory by ID
    @GetMapping("/{historyId}")
    public ResponseEntity<CollectionHistory> getHistory(@PathVariable String historyId) {
        try {
            Optional<CollectionHistory> history = collectionHistoryService.getHistory(historyId);
            return history.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(404).body(null)); // Return 404 if not found
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null); // Handle error scenario
        }
    }

    // Get all collection histories for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CollectionHistory>> getAllHistory(@PathVariable String userId) {
        try {
            List<CollectionHistory> histories = collectionHistoryService.getAllHistory(userId);
            return ResponseEntity.ok(histories);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null); // Handle error scenario
        }
    }

    // Delete all collection histories for a specific user
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAllHistory(@PathVariable String userId) {
        try {
            collectionHistoryService.deleteAllHistory(userId);
            return ResponseEntity.ok("All collection histories for user " + userId + " deleted.");
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error deleting collection histories: " + e.getMessage());
        }
    }

}
