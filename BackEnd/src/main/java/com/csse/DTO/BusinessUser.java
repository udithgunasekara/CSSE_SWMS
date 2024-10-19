package com.csse.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessUser extends System_user{

    private int businessRegistrationNumber;
    private String businessType;
    private int employeeCount;
    private String taxNumber;

    @Override
   public void setRole(String role) {
        super.setRole("System_user_account");
    }
    @Override
    public void setUserAccountType(String userAccountType) {
        super.setUserAccountType("business");
        }

    //what are the values i need to set in role and userAccountType ?

}