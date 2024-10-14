package com.csse.DTO;

import java.util.Collection;

public class Collection_model {
    private String modelname;
    private String fee;

    public Collection_model() {
    }

    public Collection_model(String modelname, String fee) {
        this.modelname = modelname;
        this.fee = fee;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
