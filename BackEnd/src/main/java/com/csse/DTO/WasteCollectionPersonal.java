package com.csse.DTO;

public class WasteCollectionPersonal extends User{
    private String contactNo;
    private double wasteCollected;

    public WasteCollectionPersonal() {
    }

    public WasteCollectionPersonal(String userid, String name, String email, String password, String role, String contactNo, double wasteCollected) {
        super(userid, name, email, password, role);
        this.contactNo = contactNo;
        this.wasteCollected = wasteCollected;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public double getWasteCollected() {
        return wasteCollected;
    }

    public void setWasteCollected(double wasteCollected) {
        this.wasteCollected = wasteCollected;
    }
}
