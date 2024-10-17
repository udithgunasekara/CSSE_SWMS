package com.csse.DTO;

public class StatTrashbinDto {
    private String trashbinId;
    private String type;
    private double weight;
    private double level;

    public StatTrashbinDto(String trashbinId, String type, double weight, double level) {
        this.trashbinId = trashbinId;
        this.type = type;
        this.weight = weight;
        this.level = level;
    }

    public StatTrashbinDto() {
    }

    public String getTrashbinId() {
        return trashbinId;
    }

    public void setTrashbinId(String trashbinId) {
        this.trashbinId = trashbinId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }
}
