package com.csse.DTO;

import com.google.cloud.firestore.annotation.DocumentId;

public class City {

    private String activemodel;
    private String cityid;
    private String cityname;


    public City() {
    }

    public City(String activemodel, String cityname) {
        this.activemodel = activemodel;
        this.cityname = cityname;
    }

    public String getActivemodel() {
        return activemodel;
    }

    public void setActivemodel(String activemodel) {
        this.activemodel = activemodel;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
