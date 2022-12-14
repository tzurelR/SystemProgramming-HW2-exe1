// Tzurel Rauper 206543738
// David Galsberg 207759614
package com.company;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class VehicleWasher {

    // This is the lists that require, we implement them in queues:
    private Queue<Vehicle> waitVehicle;
    private Queue<Vehicle> nowWashing;
    private Queue<Vehicle> carWashed;
    private Queue<Vehicle> suvWashed;
    private Queue<Vehicle> truckWashed;
    private Queue<Vehicle> miniBusWashed;
    private VehicleLogger logger;

    private int washingStation;
    // lamda time is for the next time
    private double lamdaTime;
    private double nextTime;
    private int numberOfVehicles;
    private double sumTimersCar;
    private double sumTimersSuv;
    private double sumTimersTruck;
    private double sumTimersMiniBus;


    public VehicleWasher(double lamda, int numberOfVehicles, int washingStation) throws IOException {
        lamdaTime = lamda;
        this.numberOfVehicles = numberOfVehicles;
        this.washingStation = washingStation;
        // We want a fairness in out threads:
        waitVehicle = new ArrayDeque<>();
        nowWashing = new ArrayDeque<>();
        carWashed = new ArrayDeque<>();
        suvWashed = new ArrayDeque<>();
        truckWashed = new ArrayDeque<>();
        miniBusWashed = new ArrayDeque<>();
        nextTime = setNextTime();
        System.out.println();
        sumTimersCar = 0;
        sumTimersSuv = 0;
        sumTimersTruck = 0;
        sumTimersMiniBus = 0;
        logger = new VehicleLogger();


    }

    // function for nextTime:
    public double setNextTime() {

        // ln = Math.log()

        double x = -(Math.log(Math.random())) / lamdaTime;
        return x;
    }






    public void washer(Vehicle v) throws InterruptedException, IOException {

        while (!v.isWashed()) {

            synchronized (this) {
                // In the question request that all the cars first enter to waitVehicle:
                while (washingStation <= 0) {
                    try {
                        logger.writeLine("************************************");

                        System.out.println("************************************");
                        logger.writeLine("There isn't empty station-> " + v.getName() + " this thread enter to waitVehicle:");
                        System.out.println("There isn't empty station-> " + v.getName() + " this thread enter to waitVehicle:");
                        addWaitVehicle(v);
                        wait();
                    } catch (InterruptedException | IOException e) {
                        System.err.println("throw from washer in VehicleWasher");
                    }
                }

                if(v == waitVehicle.peek() || waitVehicle.isEmpty()) {
                    addNowWashing(v);
                    v.setWashed(true);
                }
            }
        } // END big while
        try {
                v.setTimer(nextTime);
                Thread.sleep((long) (nextTime * 1000));
            } catch (InterruptedException e) {
                System.err.println("throws from sleeping!");
            }
        Vehicle temp = nowWashing.peek();
        removeWaitVehicle(temp);
        removeNowWashing(temp);
        theVehiclesThatWashed(temp);


    }














    public synchronized void theVehiclesThatWashed(Vehicle v) throws IOException {

        if (v instanceof Car) {
            carWashed.add(v);
            sumTimersCar += v.getTimer();
            logger.writeLine(v.getName() + " adding to carWashed: ");
            System.out.println(v.getName() + " adding to carWashed: ");
            logger.writeLine(carWashed.toString());
            System.out.println(carWashed);
            System.out.println();
        } else if (v instanceof Suv) {
            suvWashed.add(v);
            sumTimersSuv += v.getTimer();
            logger.writeLine(v.getName() + " adding to suvWashed: ");
            System.out.println(v.getName() + " adding to suvWashed: ");
            logger.writeLine(suvWashed.toString());
            System.out.println(suvWashed);
            System.out.println();
        } else if (v instanceof Truck) {
            truckWashed.add(v);
            sumTimersTruck += v.getTimer();
            logger.writeLine(v.getName() + " adding to truckWashed: ");
            System.out.println(v.getName() + " adding to truckWashed: ");
            logger.writeLine(truckWashed.toString());
            System.out.println(truckWashed);
            System.out.println();
        } else {
            miniBusWashed.add(v);
            sumTimersMiniBus += v.getTimer();
            logger.writeLine(v.getName() + " adding to miniBusWashed: ");
            System.out.println(v.getName() + " adding to miniBusWashed: ");
            logger.writeLine(miniBusWashed.toString());
            System.out.println(miniBusWashed);
            System.out.println();
        }

    }

    public synchronized void removeWaitVehicle(Vehicle v) throws IOException {
        // check that the waitVehicle isn't empty:
        if(!waitVehicle.isEmpty() && waitVehicle.peek() == v) {
            waitVehicle.remove();
            logger.writeLine("waitVehicle after the removing:");
            System.out.println("waitVehicle after the removing:");
            System.out.println(waitVehicle);
            System.out.println();
        }
    }

    public synchronized void addWaitVehicle(Vehicle v) throws IOException {

        // check if the thread isn't exist:
        if(!waitVehicle.contains(v)) {
            logger.writeLine(v.getName() + " arrive to washer");
            System.out.println(v.getName() + " arrive to washer");
            waitVehicle.add(v);
            logger.writeLine("waitVehicle after the adding");
            System.out.println("waitVehicle after the adding:");
            System.out.println(waitVehicle);
            System.out.println();
        }
    }

    public synchronized void removeNowWashing(Vehicle v) throws IOException {
            washingStation++;
            nowWashing.remove();
            logger.writeLine(v.getName() + " finish the washing, this thread will remove from nowWashing:");
            System.out.println(v.getName() + " finish the washing, this thread will remove from nowWashing:");
            logger.writeLine(nowWashing.toString());
            System.out.println(nowWashing);
            System.out.println();
            notifyAll();
    }

    public void addNowWashing(Vehicle v) throws InterruptedException, IOException {
        //sleep in the wash process:

        synchronized (this) {
            System.out.println("Before addNow the num of washing: " + washingStation);
            washingStation--;
            System.out.println("After addNow the num of washing: " + washingStation);

            // check the thread isn't exist in the nowWashing:
            if(!nowWashing.contains(v)) {
                nowWashing.add(v);
                logger.writeLine("There is empty station (station number " + (washingStation + 1) + ") -> " + v.getName() + " washing now, this thread enter to nowWashing:");
                System.out.println("There is empty station (station number " + (washingStation + 1) + ") -> " + v.getName() + " washing now, this thread enter to nowWashing:");
                System.out.println(nowWashing);
                logger.writeLine(nowWashing.toString());
                System.out.println();
            }
        }

    }

    public void printQueue() throws IOException {
        System.out.println();
        logger.writeLine("waitVehicle:" + waitVehicle.toString());
        System.out.println("waitVehicle:");
        System.out.println(waitVehicle);
        logger.writeLine("nowWashing:" + nowWashing.toString());
        System.out.println("nowWashing:");
        System.out.println(nowWashing);
        logger.writeLine("carWashed:" + carWashed.toString());
        System.out.println("carWashed");
        System.out.println(carWashed);
        logger.writeLine("suvWashed:" + suvWashed.toString());
        System.out.println("suvWashed:");
        System.out.println(suvWashed);
        logger.writeLine("truckWashed:" + truckWashed.toString());
        System.out.println("truckWashed:");
        System.out.println(truckWashed);
        logger.writeLine("miniBusWashed:" + miniBusWashed.toString());
        System.out.println("miniBusWashed:");
        System.out.println(miniBusWashed);
    }

    public double getSumTimersCar() {
        return sumTimersCar;
    }

    public double getSumTimersSuv() {
        return sumTimersSuv;
    }

    public double getSumTimersTruck() {
        return sumTimersTruck;
    }

    public double getSumTimersMiniBus() {
        return sumTimersMiniBus;
    }

    public Queue<Vehicle> getCarWashed() {
        return carWashed;
    }

    public Queue<Vehicle> getMiniBusWashed() {
        return miniBusWashed;
    }

    public Queue<Vehicle> getSuvWashed() {
        return suvWashed;
    }

    public Queue<Vehicle> getTruckWashed() {
        return truckWashed;
    }

    public VehicleLogger getLogger() {
        return logger;
    }
}
