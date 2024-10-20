package com.csse.DTO;

import lombok.Getter;
import lombok.Setter;

public class OrganicTrashbin extends Trashbin{
    @Getter
    @Setter
    private String trashbinId;
    @Setter
    @Getter
    private String trashbinType;
    @Getter
    @Setter
    private double wasteLevel;
    @Setter
    @Getter
    private double weight;
    private boolean isFull;
    @Getter
    private String latitude;
    @Getter
    private String longitude;
    private boolean isAssigned;
    private boolean isCollected;
    @Getter
    @Setter
    private String facilityId;

    public OrganicTrashbin() {
    }

    public OrganicTrashbin(Trashbin trashbin) {
        this.trashbinId = trashbin.getTrashbinId();
        this.trashbinType = "Organic";
        this.wasteLevel = trashbin.getWasteLevel();
        this.weight = trashbin.getWeight();
        this.isFull = trashbin.isFull();
        this.latitude = trashbin.getLatitude();
        this.longitude = trashbin.getLongitude();
        this.isAssigned = trashbin.isAssigned();
        this.isCollected = trashbin.isCollected();
        this.facilityId = trashbin.getFacilityId();
    }

    public void setLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
