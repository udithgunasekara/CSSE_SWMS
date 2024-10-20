/*package com.csse.DTO;

public class CollectionHistory {
    private String historyID;
    private String collecterID;
    private String tagid;
    private String date;
    private String time;
    private String weight;

    public CollectionHistory() {
    }

    public CollectionHistory(String historyID, String collecterID, String tagid, String date, String time, String weight) {
        this.historyID = historyID;
        this.collecterID = collecterID;
        this.tagid = tagid;
        this.date = date;
        this.time = time;
        this.weight = weight;
    }

    public String getHistoryID() {
        return historyID;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public String getCollecterID() {
        return collecterID;
    }

    public void setCollecterID(String collecterID) {
        this.collecterID = collecterID;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}


*/


// CollectionHistory.java
package com.csse.DTO;

public class CollectionHistory {
    private String historyID;
    private String collecterID;
    private String date;
    private String time;
    private String tagid;
    private Double weight;

    // Default constructor
    public CollectionHistory() {}

    // Constructor with all fields
    public CollectionHistory(String historyID, String collecterID, String date, String time, String tagid, Double weight) {
        this.historyID = historyID;
        this.collecterID = collecterID;
        this.date = date;
        this.time = time;
        this.tagid = tagid;
        this.weight = weight;
    }

    // Getters and setters
    public String getHistoryID() {
        return historyID;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public String getCollecterID() {
        return collecterID;
    }

    public void setCollecterID(String collecterID) {
        this.collecterID = collecterID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}