package com.csse.Service;

import com.csse.DTO.BusinessUser;
import com.csse.DTO.HouseholdUser;
import com.csse.DTO.LoginRequest;

public interface AuthService {
    String registerBusinessUser(BusinessUser businessUser);
    String registerHouseholdUser(HouseholdUser householdUser);
   // boolean authenticate(LoginRequest loginDTO);

}
