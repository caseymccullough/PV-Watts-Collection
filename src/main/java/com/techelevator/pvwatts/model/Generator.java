package com.techelevator.pvwatts.model;

public class Generator {
    private int generatorId;
    private int utilityId;
    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private double systemSize;
    private int moduleType;
    private int arrayType;
    private double tilt;

    public Generator() {
    }

    public Generator(int generatorId, int utilityId, String name, String streetAddress1, String streetAddress2, String city, String state, String zipCode, double systemSize, int moduleType, int arrayType, double tilt) {
        this.generatorId = generatorId;
        this.utilityId = utilityId;
        this.name = name;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.systemSize = systemSize;
        this.moduleType = moduleType;
        this.arrayType = arrayType;
        this.tilt = tilt;
    }

    public int getGeneratorId() {
        return generatorId;
    }

    public int getUtilityId() {
        return utilityId;
    }

    public String getName() {
        return name;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public double getSystemSize() {
        return systemSize;
    }

    public int getModuleType() {
        return moduleType;
    }

    public int getArrayType() {
        return arrayType;
    }

    public double getTilt() {
        return tilt;
    }

    public void setGeneratorId(int generatorId) {
        this.generatorId = generatorId;
    }

    public void setUtilityId(int utilityId) {
        this.utilityId = utilityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setSystemSize(double systemSize) {
        this.systemSize = systemSize;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public void setArrayType(int arrayType) {
        this.arrayType = arrayType;
    }

    public void setTilt(double tilt) {
        this.tilt = tilt;
    }

    @Override
    public String toString() {
        return "Generator{" +
                "generatorId=" + generatorId +
                ", utilityId=" + utilityId +
                ", name='" + name + '\'' +
                ", streetAddress1='" + streetAddress1 + '\'' +
                ", streetAddress2='" + streetAddress2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", systemSize=" + systemSize +
                ", moduleType=" + moduleType +
                ", arrayType=" + arrayType +
                ", tilt=" + tilt +
                '}';
    }
}
