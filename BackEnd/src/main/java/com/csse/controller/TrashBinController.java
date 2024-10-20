package com.csse.controller;

import com.csse.DTO.Trashbin;
import com.csse.service.ITrashBinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/trashbin")
public class TrashBinController {

    private static final Logger logger = LoggerFactory.getLogger(TrashBinController.class);

    private final ITrashBinService trashBinService;

    public TrashBinController(ITrashBinService trashBinService) {
        this.trashBinService = trashBinService;
    }

    // URL: http://localhost:8080/api/trashbin
    @PostMapping
    public ResponseEntity<String> createTrashBin(@RequestBody Trashbin trashbin) throws ExecutionException, InterruptedException {
        logger.info("Received request to create a new trash bin: {}", trashbin);
        String trashbinID;
        try {
            trashbinID = trashBinService.createTrashBin(trashbin);
            logger.info("Trash bin created successfully with ID: {}", trashbinID);
            return new ResponseEntity<>(trashbinID, HttpStatus.CREATED);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred while creating trash bin: {}", e.getMessage());
            throw e;  // rethrow or handle appropriately
        }
    }

    // URL: http://localhost:8080/api/trashbin/{id}
    @GetMapping("/{id}")
    public ResponseEntity<List<Trashbin>> trashBinToCollectById(@PathVariable String id) throws ExecutionException, InterruptedException {
        logger.info("Fetching trash bins to collect for ID: {}", id);
        try {
            List<Trashbin> trashbinList = trashBinService.trashBinsToCollect(id);
            logger.info("Found {} trash bins to collect for ID: {}", trashbinList.size(), id);
            return ResponseEntity.ok(trashbinList);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred while fetching trash bins for ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // URL: http://localhost:8080/api/trashbin/all
    @GetMapping("/all")
    public ResponseEntity<List<Trashbin>> getAllTrashbins() {
        logger.info("Fetching all trash bins");
        try {
            List<Trashbin> trashbins = trashBinService.findAllTrashBins();
            logger.info("Found {} trash bins", trashbins.size());
            return new ResponseEntity<>(trashbins, HttpStatus.OK);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred while fetching all trash bins: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Uncomment this method if needed in the future
    // @GetMapping("/full")
    // public ResponseEntity<List<Trashbin>> getFullTrashBin() throws ExecutionException, InterruptedException {
    //     logger.info("Fetching full trash bins");
    //     List<Trashbin> trashbinList = trashBinService.findFullTrashBins();
    //     logger.info("Found {} full trash bins", trashbinList.size());
    //     return ResponseEntity.ok(trashbinList);
    // }
}