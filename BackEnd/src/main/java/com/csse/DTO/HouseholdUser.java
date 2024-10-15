package com.csse.DTO;

import java.util.List;

public class HouseholdUser extends System_user{
    private int areaNumber;
    private int numberofResidents;

    public HouseholdUser() {
        super();
    }

    public HouseholdUser(String userid, String name, String email, String password, String role, String address, String contactNo, String userAccountType, String city, List<Trashbin> trashBins, int areaNumber, int numberofResidents) {
        super(userid, name, email, password, role, address, contactNo, userAccountType, city, trashBins);
        this.areaNumber = areaNumber;
        this.numberofResidents = numberofResidents;
    }

    public int getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(int areaNumber) {
        this.areaNumber = areaNumber;
    }

    public int getNumberofResidents() {
        return numberofResidents;
    }

    public void setNumberofResidents(int numberofResidents) {
        this.numberofResidents = numberofResidents;
    }
}
