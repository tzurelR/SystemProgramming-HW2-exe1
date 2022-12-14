// Tzurel Rauper 206543738
// David Galsberg 207759614
package com.company;

import java.io.IOException;
import java.util.*;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException, IOException {

        // Input
        System.out.println("Welcome to Vehicle Washer!");
        int washingStation;
        int numOfVehicles;
        double lamdaForWashing;
        double lamdaForArriveVehicles;
        System.out.println("Put the number of the washing station:");
        washingStation = sc.nextInt();
        System.out.println("Put the number of the vehicles:");
        numOfVehicles = sc.nextInt();
        System.out.println("Put the lamda for the washing:");
        lamdaForWashing = sc.nextDouble();
        System.out.println("Put the lamda for the vehicles that arrive:");
        lamdaForArriveVehicles = sc.nextDouble();
        ArrayList<Thread> arr = new ArrayList<>();
        double nextTimeArrive = -(Math.log(Math.random())) / lamdaForArriveVehicles;
        // We did if for see how the vehicles wait :
        if(nextTimeArrive > 0.4) {
            nextTimeArrive = 0.2;
        }


        // Here Vehicle Washer start:
        long startTime = System.nanoTime();
        int toArriveSec = 1000000000;
        VehicleWasher v = new VehicleWasher(lamdaForWashing, numOfVehicles, washingStation);
        // Create the vehicles:
        for(int i = 0; i < numOfVehicles; i++) {

            // Time between vehicles:
            Thread.sleep((long) (nextTimeArrive * 1000));

            // get random numForCreating:
            int x = (int) (Math.random() * 4 + 1);

            if(x == 1) {
                Car car = new Car(("car" + i), i, v);
                Thread t = new Thread(car);
                t.start();
                arr.add(t);
            } else if(x == 2) {
                Suv suv = new Suv(("suv" + i), i, v);
                Thread t = new Thread(suv);
                t.start();
                arr.add(t);
            } else if(x == 3) {
                Truck truck = new Truck(("truck" + i), i, v);
                Thread t = new Thread(truck);
                t.start();
                arr.add(t);
            } else{
                MiniBus miniBus = new MiniBus(("miniBus" + i), i, v);
                Thread t = new Thread(miniBus);
                t.start();
                arr.add(t);
            }
        }
        



        // check that all the threads done:
        while(!arr.isEmpty()) {
            for(int i = 0; i < arr.size(); i++) {
                if(!arr.get(i).isAlive()) {
                    arr.remove(arr.get(i));
                }
            }
        }

        long endTime = System.nanoTime();
        v.getLogger().writeLine("-------------------------The total time that vehicle washer worked-------------------------");
        System.out.println("-------------------------The total time that vehicle washer worked-------------------------");
        System.out.println((endTime - startTime) / toArriveSec);
        v.printQueue();

        v.getLogger().writeLine("average time of cars: " + v.getSumTimersCar() / v.getCarWashed().size());
        System.out.println("average time of cars: " + v.getSumTimersCar() / v.getCarWashed().size());

        v.getLogger().writeLine("average time of suv: " + v.getSumTimersSuv() / v.getSuvWashed().size());
        System.out.println("average time of suv: " + v.getSumTimersSuv() / v.getSuvWashed().size());

        v.getLogger().writeLine("average time of truck: " + v.getSumTimersTruck() / v.getTruckWashed().size());
        System.out.println("average time of truck: " + v.getSumTimersTruck() / v.getTruckWashed().size());

        v.getLogger().writeLine("average time of mini bus: " + v.getSumTimersMiniBus() / v.getMiniBusWashed().size());
        System.out.println("average time of mini bus: " + v.getSumTimersMiniBus() / v.getMiniBusWashed().size());


        v.getLogger().closeWrite();














    }
}
