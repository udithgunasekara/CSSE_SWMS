package com.csse.controller;

import com.csse.DTO.City;
import com.csse.DTO.ProcessingFacility;
import com.csse.service.ProcessingFacilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/profacility")
public class ProcessingFacilityController {
    private final ProcessingFacilityService processingFacilityService;

    public ProcessingFacilityController(ProcessingFacilityService service) {
        this.processingFacilityService = service;
    }

    @PostMapping
    public ResponseEntity<String> createFacility(@RequestBody ProcessingFacility facility) {
        try {
            String id = processingFacilityService.createFacility(facility);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating facility: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessingFacility> getFacilityById(@PathVariable String id) {
        try {
            Optional<ProcessingFacility> facility = processingFacilityService.findFacilityById(id);
            return facility.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProcessingFacility>> getAllProcessingFacilities() {
        try {
            List<ProcessingFacility> facilities = processingFacilityService.findAllProcessingFacilities();
            return ResponseEntity.ok(facilities);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProcessingFacility(@PathVariable String id, @RequestBody ProcessingFacility facility) {
        try {
            processingFacilityService.updateProcessingFacility(id, facility);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessingFacility(@PathVariable String id) {
        try {
            processingFacilityService.deleteProcessingFacility(id);
            return ResponseEntity.ok().build();
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
