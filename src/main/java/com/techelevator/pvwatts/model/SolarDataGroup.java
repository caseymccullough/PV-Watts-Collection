package com.techelevator.pvwatts.model;

import java.util.Arrays;

public class SolarDataGroup {
    private double [] acMonthly;
    private double acAnnual;
    private double solarRadiationAnnual;
    private double capacityFactor;

    public SolarDataGroup() {
        acMonthly = new double[12];
    }

    public double[] getAcMonthly() {
        return acMonthly;
    }

    public double getAcAnnual() {
        return acAnnual;
    }

    public double getSolarRadiationAnnual() {
        return solarRadiationAnnual;
    }

    public double getCapacityFactor() {
        return capacityFactor;
    }

    public void setAcMonthly(double[] acMonthly) {
        this.acMonthly = acMonthly;
    }

    public void setAcAnnual(double acAnnual) {
        this.acAnnual = acAnnual;
    }

    public void setSolarRadiationAnnual(double solarRadiationAnnual) {
        this.solarRadiationAnnual = solarRadiationAnnual;
    }

    public void setCapacityFactor(double capacityFactor) {
        this.capacityFactor = capacityFactor;
    }

    @Override
    public String toString() {
        return "SolarDataGroup{" +
                "acMonthly=" + Arrays.toString(acMonthly) +
                ", acAnnual=" + acAnnual +
                ", solarRadiationAnnual=" + solarRadiationAnnual +
                ", capacityFactor=" + capacityFactor +
                '}';
    }
}
