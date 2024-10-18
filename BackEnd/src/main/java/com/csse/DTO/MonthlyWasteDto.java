package com.csse.DTO;

import java.util.Map;

public class MonthlyWasteDto {
    private String month;
    private Map<String, Double> wasteStats;

    public MonthlyWasteDto() {
    }

    public MonthlyWasteDto(String month, Map<String, Double> wasteStats) {
        this.month = month;
        this.wasteStats = wasteStats;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Map<String, Double> getWasteStats() {
        return wasteStats;
    }

    public void setWasteStats(Map<String, Double> wasteStats) {
        this.wasteStats = wasteStats;
    }
}
