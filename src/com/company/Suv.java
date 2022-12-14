// Tzurel Rauper 206543738
// David Galsberg 207759614
package com.company;

import java.io.IOException;

public class Suv extends Vehicle implements Runnable{

    public Suv(String name, int num, VehicleWasher v) {
        super(name, num, v);
    }

    @Override
    public String toString() {
        return "Suv-> " + getName() + " id-> " + getId();
    }

    @Override
    public void run() {
        try {
            getWasher().washer(this);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
