package com.csse.controller;

import com.csse.DTO.WasteCollectionPersonal;
import com.csse.service.WasteCollectionPresonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/cp")
public class WasteCollectionPersonalController {

    private static final Logger logger = LoggerFactory.getLogger(WasteCollectionPersonalController.class);
    private final WasteCollectionPresonalService  wasteCollectionPresonalService;

    public WasteCollectionPersonalController(WasteCollectionPresonalService wasteCollectionPresonalService) {
        this.wasteCollectionPresonalService = wasteCollectionPresonalService;
    }

    //url: http://localhost:8080/api/cp
    @PostMapping
    public ResponseEntity<String> createWasteCollectionPersonal(@RequestBody WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException {
        logger.info("Creating a new WasteCollectionPersonal with email: {}", wasteCollectionPersonal.getEmail());
        try {
            String wasteCollectionPersonalId = wasteCollectionPresonalService.createWasteCollectionPersonal(wasteCollectionPersonal);
            logger.info("WasteCollectionPersonal created successfully with ID: {}", wasteCollectionPersonalId);
            return new ResponseEntity<>(wasteCollectionPersonalId, HttpStatus.CREATED);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred while creating WasteCollectionPersonal", e);
            throw e;
        }
    }

//    url: http://localhost:8080/api/cp/update/1?userid=1
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWasteCollected(@PathVariable String id, @RequestParam String userid)  {
        logger.info("Updating waste collection for Trashbin ID: {} by user: {}", id, userid);
        try{
            String tagid = wasteCollectionPresonalService.updateWasteCollected(id,userid);
            logger.info("Waste collected successfully for Trashbin ID: {}", tagid);
            return  ResponseEntity.ok("Trashbin " + tagid + " collected successfully.");
        }catch (IllegalArgumentException e) {
            // Handle cases where the trashbin is empty
            logger.warn("Attempt to collect waste from an empty Trashbin with ID: {}", id);
            return ResponseEntity.status(400).body("Trashbin is empty.");

        }catch (ExecutionException | InterruptedException e) {
            // Handle possible exceptions, like ExecutionException or InterruptedException
            logger.error("Error occurred while updating waste collection for Trashbin ID: {}", id, e);
            return ResponseEntity.status(500).body("An error occurred while updating waste collection.");
        } catch (NoSuchElementException e) {
            // Handle cases where the trashbin or user was not found
            logger.warn("Trashbin or user not found for Trashbin ID: {} or User ID: {}", id, userid);
            return ResponseEntity.status(404).body("Trashbin or user not found.");
        }
    }


}

//sample postman request
/*{
        "name": "ajith",
        "email": "ajith@gmail.com",
        "password": 123344,
        "role": "collecter",
        "contactNo": "8976876547654",
        "wasteCollected": 0
        }*/
