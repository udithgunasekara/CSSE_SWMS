package com.csse.Controller;

import com.csse.DTO.SpecialWasteDTO;
import com.csse.Service.SpecialWasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Controller
@RestController("/SpecialWaste")
public class SpecialWasteController {
     private final SpecialWasteService specialWasteService;

     public SpecialWasteController(SpecialWasteService specialWasteService) {this.specialWasteService = specialWasteService;}

   @PostMapping("/requestSpecialWaste")
   public ResponseEntity<SpecialWasteDTO> requestSpecialWaste(@RequestBody SpecialWasteDTO specialWaste) throws ExecutionException, InterruptedException {
         Optional<SpecialWasteDTO> result = specialWasteService.createSpecialWaste(specialWaste);
         return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
}

    @GetMapping("/getAllSpecialWastes")
    public ResponseEntity<List<SpecialWasteDTO>> getAllSpecialWastes() {
        try {
            List<SpecialWasteDTO> specialWastes = specialWasteService.getAllSpecialWastes();
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
            List<SpecialWasteDTO> specialWaste = specialWasteService.getSpecialWasteByUserId(userId);
            return ResponseEntity.ok(specialWaste);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
