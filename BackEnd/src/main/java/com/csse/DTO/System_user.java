package com.csse.DTO;

import org.apache.el.parser.BooleanNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class System_user extends User{
    private String address;
    private String contactNo;
    private String userAccountType;
    private String city;
    private List<Trashbin> trashBins;

    public System_user() {
        this.trashBins = new ArrayList<>();
    }

    public System_user(String userid, String name, String email, String password, String role, String address, String contactNo, String userAccountType, String city, List<Trashbin> trashBins) {
        super(userid, name, email, password, role);
        this.address = address;
        this.contactNo = contactNo;
        this.userAccountType = userAccountType;
        this.city = city;
        this.trashBins = trashBins;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(String userAccountType) {
        this.userAccountType = userAccountType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Trashbin> getTrashBins() {
        return trashBins;
    }

    public void setTrashBins(List<Trashbin> trashBins) {
        this.trashBins = trashBins;
    }
}
