
package com.company;

import java.io.IOException;

public class MiniBus extends Vehicle implements Runnable{

    public MiniBus(String name, int num, VehicleWasher v) {
        super(name, num, v);
    }

    @Override
    public String toString() {
        return "MiniBus-> " + getName() + " id-> " + getId();
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
