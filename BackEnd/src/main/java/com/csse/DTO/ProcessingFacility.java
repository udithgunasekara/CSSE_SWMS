package com.csse.DTO;

import lombok.Setter;
import org.springframework.http.ResponseEntity;

public class ProcessingFacility {
    @Setter
    private String facilityid;
    @Setter
    private String facilityAddress;
    private String latitude;
    private String longitude;

    public ProcessingFacility() {
    }

    public ProcessingFacility(String facilityid, String facilityAddress, String latitude, String longitude) {
        this.facilityid = facilityid;
        this.facilityAddress = facilityAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFacilityid() {
        return facilityid;
    }

    public String getFacilityAddress() {
        return facilityAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLocations(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
