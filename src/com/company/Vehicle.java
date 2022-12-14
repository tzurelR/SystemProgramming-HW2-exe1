
package com.company;

public abstract class Vehicle {
    // abstract class Vehicle
    private final String name;
    private final int id;
    private VehicleWasher washer;
    private boolean isWashed;
    private double timer;

    public Vehicle(String name, int id, VehicleWasher v) {
        this.name = name;
        this.id = id;
        this.washer = v;
        isWashed = false;
        timer = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VehicleWasher getWasher() {
        return washer;
    }

    public boolean isWashed() {
        return isWashed;
    }

    public void setWashed(boolean washed) {
        isWashed = washed;
    }

    public void setTimer(double timer) {
        this.timer += timer;
    }

    public double getTimer() {
        return timer;
    }
}
