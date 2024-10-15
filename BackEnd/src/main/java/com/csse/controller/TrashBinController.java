package com.csse.controller;

import com.csse.DTO.Trashbin;
import com.csse.DTO.WasteCollectionPersonal;
import com.csse.service.TrashBinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/trashbin")
public class TrashBinController {

    private final TrashBinService trashBinService;

    public TrashBinController(TrashBinService trashBinService) {
        this.trashBinService = trashBinService;
    }

    //url: http://localhost:8080/api/trashbin
    @PostMapping
    public ResponseEntity<String> createTrashBin(@RequestBody Trashbin trashbin) throws ExecutionException, InterruptedException {
        String trashbinID = trashBinService.createTrashBin(trashbin);
        return new ResponseEntity<>(trashbinID, HttpStatus.CREATED);
    }
}
