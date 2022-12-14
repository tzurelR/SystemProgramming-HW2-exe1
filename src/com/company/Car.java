
package com.company;

import java.io.IOException;

public class Car extends Vehicle implements Runnable{

    public Car(String name, int num, VehicleWasher v) {
        super(name, num, v);
    }

    @Override
    public String toString() {
        return "Car-> " + getName() + " id-> " + getId();
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
