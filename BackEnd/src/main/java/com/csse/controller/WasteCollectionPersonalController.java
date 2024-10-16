package com.csse.controller;

import com.csse.DTO.WasteCollectionPersonal;
import com.csse.service.WasteCollectionPresonalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/cp")
public class WasteCollectionPersonalController {

    private final WasteCollectionPresonalService  wasteCollectionPresonalService;

    public WasteCollectionPersonalController(WasteCollectionPresonalService wasteCollectionPresonalService) {
        this.wasteCollectionPresonalService = wasteCollectionPresonalService;
    }

    //url: http://localhost:8080/api/cp
    @PostMapping
    public ResponseEntity<String> createWasteCollectionPersonal(@RequestBody WasteCollectionPersonal wasteCollectionPersonal) throws ExecutionException, InterruptedException {
        String wasteCollectionPersonalId = wasteCollectionPresonalService.createWasteCollectionPersonal(wasteCollectionPersonal);
        return new ResponseEntity<>(wasteCollectionPersonalId, HttpStatus.CREATED);
    }

//    url: http://localhost:8080/api/cp/update/1?userid=1
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWasteCollected(@PathVariable String id, @RequestParam String userid)  {
        try{
            String tagid = wasteCollectionPresonalService.updateWasteCollected(id,userid);
            return  ResponseEntity.ok("Trashbin with ID " + tagid + " updated successfully.");
        }catch (IllegalArgumentException e) {
            // Handle cases where the trashbin is empty
            return ResponseEntity.status(400).body("Trashbin is empty.");

        }catch (ExecutionException | InterruptedException e) {
            // Handle possible exceptions, like ExecutionException or InterruptedException
            return ResponseEntity.status(500).body("An error occurred while updating waste collection.");
        } catch (NoSuchElementException e) {
            // Handle cases where the trashbin or user was not found
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
