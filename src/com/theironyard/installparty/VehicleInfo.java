package com.theironyard.installparty;

public class VehicleInfo {

    int VIN;
    double odometer;
    double consumption;
    double lastOilChange;
    double engineSize;

    public VehicleInfo() {
    }

    public int getVIN() {
        return VIN;
    }

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getLastOilChange() {
        return lastOilChange;
    }

    public void setLastOilChange(double lastOilChange) {
        this.lastOilChange = lastOilChange;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }
}
