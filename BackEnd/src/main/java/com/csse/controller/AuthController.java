package com.csse.Controller;

import com.csse.DTO.BusinessUser;
import com.csse.DTO.HouseholdUser;
import com.csse.DTO.LoginRequest;
import com.csse.Service.AuthService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // New register method for business users
    @PostMapping("/register/business")
    public ResponseEntity<?> registerBusinessUser(@RequestBody BusinessUser businessUser) {
        return ResponseEntity.ok(authService.registerBusinessUser(businessUser));
    }

    // New register method for household users
    @PostMapping("/register/household")
    public ResponseEntity<?> registerHouseholdUser(@RequestBody HouseholdUser householdUser) {
        return ResponseEntity.ok(authService.registerHouseholdUser(householdUser));
    }


    // New login method
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            // Firebase authentication
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(request.getToken());
            String uid = decodedToken.getUid();

            // You can retrieve the user details and verify role from Firebase
            // In Firebase, role info can be stored in custom claims or database collections
            String role = request.getRole();

            return ResponseEntity.ok("Login successful. UID: " + uid);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }





}
