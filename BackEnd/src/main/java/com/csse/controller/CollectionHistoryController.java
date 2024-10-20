package com.csse.controller;

import com.csse.DTO.CollectionHistory;
import com.csse.service.ICollectionHIstoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/history")
public class CollectionHistoryController {
    private  final ICollectionHIstoryService ICollectionHistoryService;

    public CollectionHistoryController(ICollectionHIstoryService ICollectionHIstoryService) {
        this.ICollectionHistoryService = ICollectionHIstoryService;
    }

    // since the collection history is created when collector collect the waste this method is not needed

    // Create a new CollectionHistory
    //url: http://localhost:8080/api/history/create
   /* @PostMapping("/create")
    public ResponseEntity<String> createNewCollectionHistory(@RequestBody CollectionHistory history) {
        try {
            String historyId = ICollectionHistoryService.createNewCollectionHistor(history);
            return ResponseEntity.ok("Collection history created with ID: " + historyId);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error creating collection history: " + e.getMessage());
        }
    }*/

    // Get a single CollectionHistory by ID
    //url: http://localhost:8080/api/history/1
    @GetMapping("/{historyId}")
    public ResponseEntity<CollectionHistory> getHistory(@PathVariable String historyId) {
        try {
            Optional<CollectionHistory> history = ICollectionHistoryService.getHistory(historyId);
            return history.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(404).body(null)); // Return 404 if not found
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null); // Handle error scenario
        }
    }

    //url: http://localhost:8080/api/history/user/1
    // Get all collection histories for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CollectionHistory>> getAllHistory(@PathVariable String userId) {
        try {
            List<CollectionHistory> histories = ICollectionHistoryService.getAllHistory(userId);
            return ResponseEntity.ok(histories);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null); // Handle error scenario
        }
    }


    //this controller mehtod is created for test perpose only do not use this since stat page take all data from collection history

    // Delete all collection histories for a specific user
    /*@DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAllHistory(@PathVariable String userId) {
        try {
            ICollectionHistoryService.deleteAllHistory(userId);
            return ResponseEntity.ok("All collection histories for user " + userId + " deleted.");
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error deleting collection histories: " + e.getMessage());
        }
    }*/

}
