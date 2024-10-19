package com.csse.controller;

import com.csse.DTO.Trashbin;
import com.csse.service.TrashBinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/trashbin")
public class TrashBinController {

    private final TrashBinService trashBinService;

    public TrashBinController(TrashBinService trashBinService) {
        this.trashBinService = trashBinService;
    }

    // URL: http://localhost:8080/api/trashbin
    @PostMapping
    public ResponseEntity<String> createTrashBin(@RequestBody Trashbin trashbin) throws ExecutionException, InterruptedException {
        String trashbinID = trashBinService.createTrashBin(trashbin);
        return new ResponseEntity<>(trashbinID, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/trashbin/{id}
    @GetMapping("/{id}")
    public ResponseEntity<List<Trashbin>> trashBinToCollectById(@PathVariable String id) throws ExecutionException, InterruptedException {
        List<Trashbin> trashbinList = trashBinService.trashBinsToCollect(id);
        return ResponseEntity.ok(trashbinList);
    }

    // URL: http://localhost:8080/api/trashbin/all
    @GetMapping("/all")
    public ResponseEntity<List<Trashbin>> getAllTrashbins() {
        try {
            List<Trashbin> trashbins = trashBinService.findAllTrashBins();
            return new ResponseEntity<>(trashbins, HttpStatus.OK);
        } catch (ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Uncomment this method if needed in the future
    // @GetMapping("/full")
    // public ResponseEntity<List<Trashbin>> getFullTrashBin() throws ExecutionException, InterruptedException {
    //     List<Trashbin> trashbinList = trashBinService.findFullTrashBins();
    //     return ResponseEntity.ok(trashbinList);
    // }
}