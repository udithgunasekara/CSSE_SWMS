package com.csse.Controller;

import com.csse.DTO.SpecialWasteDTO;
import com.csse.Service.SpecialWasteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController("/SpecialWaste")
//@CrossOrigin(origins = "http://localhost:3000")
public class SpecialWasteController {
     private final SpecialWasteService specialWasteService;
    private static final Logger logger = LoggerFactory.getLogger(SpecialWasteController.class);

     public SpecialWasteController(SpecialWasteService specialWasteService) {this.specialWasteService = specialWasteService;}


    //confirming cors origin connection
   // @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/test/cors")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Special waste controller is working");
    }




   @PostMapping("/requestSpecialWaste")
   public ResponseEntity<SpecialWasteDTO> requestSpecialWaste(@RequestBody SpecialWasteDTO specialWaste) {
    try {
        logger.info("Requesting special waste collection for user: " + specialWaste.getId());
        Optional<SpecialWasteDTO> result = specialWasteService.createSpecialWaste(specialWaste);
        logger.info("Special waste request created successfully");
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    } catch (ExecutionException | InterruptedException e) {
        logger.error("Error while requesting special waste collection", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


   // @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/test/getAllSpecialWastes")
    public ResponseEntity<List<SpecialWasteDTO>> getAllSpecialWastes() {
        try {
            logger.info("Fetching all special wastes");
            List<SpecialWasteDTO> specialWastes = specialWasteService.getAllSpecialWastes();
            logger.info("Special wastes fetched successfully");
            return ResponseEntity.ok(specialWastes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //updating status
    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestBody SpecialWasteDTO specialWaste) {
        try {
            specialWasteService.updateStatus(specialWaste.getId(), specialWaste.getStatus());
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating status");
        }
    }

    //get special waste by id
    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<SpecialWasteDTO>> getSpecialWasteByUserId(@PathVariable String userId) {
        try {
            logger.info("Fetching special wastes for user: " + userId);
            List<SpecialWasteDTO> specialWaste = specialWasteService.getSpecialWasteByUserId(userId);
            logger.info("Special wastes fetched successfully");
            return ResponseEntity.ok(specialWaste);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
