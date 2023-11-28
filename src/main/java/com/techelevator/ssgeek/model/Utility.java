package com.techelevator.ssgeek.model;

public class Utility {

    private int utilityId;
    private String name;

    private String state;
    private String zipCode;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Utility() {
    }

    public Utility(int utilityId, String name, String state, String zipCode) {
        this.utilityId = utilityId;
        this.name = name;
        this.state = state;
        this.zipCode = zipCode;
    }

    public int getUtilityId() {
        return utilityId;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setUtilityId(int utilityId) {
        this.utilityId = utilityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Utility{" +
                "utilityId=" + utilityId +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
