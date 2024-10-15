package com.csse.DTO;

import lombok.Getter;
import lombok.Setter;

public class Trashbin {
    @Getter
    @Setter
    private String trashbinId;
    @Setter
    @Getter
    private String trashbinType;
    @Getter
    @Setter
    private String wateLevel;
    private boolean isFull;
    @Getter
    private String latitude;
    @Getter
    private String longitude;
    private boolean isAssigned;
    private boolean isCollected;

    public Trashbin() {
    }

    public Trashbin(String trashbinId, String trashbinType, String wateLevel, boolean isFull, String latitude, String longitude, boolean isAssigned, boolean isCollected) {
        this.trashbinId = trashbinId;
        this.trashbinType = trashbinType;
        this.wateLevel = wateLevel;
        this.isFull = isFull;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isAssigned = isAssigned;
        this.isCollected = isCollected;
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