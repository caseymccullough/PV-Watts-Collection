package com.techelevator.pvwatts.model;

public class Utility {

    private int utilityId;
    private String name;

    public Utility() {
    }

    public Utility(int utilityId, String name) {
        this.utilityId = utilityId;
        this.name = name;
    }

    public int getUtilityId() {
        return utilityId;
    }

    public String getName() {
        return name;
    }

    public void setUtilityId(int utilityId) {
        this.utilityId = utilityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Utility{" +
                "utilityId=" + utilityId +
                ", name='" + name + '\'' +
                '}';
    }
}
