//package com.csse.Service.Imp;
//
//import com.csse.DTO.BusinessUser;
//import com.csse.DTO.HouseholdUser;
//import com.csse.Service.AuthService;
//
//
//import com.csse.repo.AuthRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthServiceImpl implements AuthService {
//
//    @Autowired
//    private AuthRepository firebaseRepository;
//
//    @Override
//    public String registerBusinessUser(BusinessUser businessUser) {
//        return firebaseRepository.saveBusinessUser(businessUser);
//    }
//
//    @Override
//    public String registerHouseholdUser(HouseholdUser householdUser) {
//        return firebaseRepository.saveHouseholdUser(householdUser);
//    }
//}



package com.csse.Service.Imp;

import com.csse.DTO.BusinessUser;
import com.csse.DTO.HouseholdUser;
import com.csse.DTO.LoginRequest;
import com.csse.Service.AuthService;
import com.csse.repo.AuthRepository;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

//    @Autowired
//    private final Firestore firestore;
//    public AuthServiceImpl(Firestore firestore) {
//        this.firestore = firestore;
//    }

    @Override
    public String registerBusinessUser(BusinessUser businessUser) {
        try {
            // Step 1: Create Firebase user
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(businessUser.getEmail())
                    .setPassword(businessUser.getPassword());

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            businessUser.setUserid(userRecord.getUid());

            // Step 2: Set custom claims (role)
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", "business");
            FirebaseAuth.getInstance().setCustomUserClaims(userRecord.getUid(), claims);

            // Step 3: Save user in Firestore
            return authRepository.saveBusinessUser(businessUser);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return "Error creating business user: " + e.getMessage();
        }
    }

    @Override
    public String registerHouseholdUser(HouseholdUser householdUser) {
        try {
            // Step 1: Create Firebase user
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(householdUser.getEmail())
                    .setPassword(householdUser.getPassword());

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            householdUser.setUserid(userRecord.getUid());

            // Step 2: Set custom claims (role)
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", "household");
            FirebaseAuth.getInstance().setCustomUserClaims(userRecord.getUid(), claims);

            // Step 3: Save user in Firestore
            return authRepository.saveHouseholdUser(householdUser);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return "Error creating household user: " + e.getMessage();
        }
    }

//    @Override
//    public boolean authenticate(LoginRequest loginDTO) {
//        try {
//            // Verify the ID token with Firebase
//            FirebaseAuth.getInstance().signInWithEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
//            return true; // User authenticated
//        } catch (FirebaseAuthException e) {
//            e.printStackTrace();
//            return false; // Authentication failed
//        }
//    }
}

