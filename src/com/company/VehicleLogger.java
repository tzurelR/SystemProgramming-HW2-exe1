
package com.company;

import java.io.*;

public class VehicleLogger {

    FileWriter writer;
    FileReader reader;
    BufferedWriter w1;
    BufferedReader r1;
    File log = new File("log.txt");
    public VehicleLogger() throws IOException {
        try {
            writer = new FileWriter(log);
            reader = new FileReader(log);
        } catch (IOException e) {
            System.err.println("Throws from VehicleLogger");
        }

        w1 = new BufferedWriter(writer);
        r1 = new BufferedReader(reader);;
    }











    // methods for write:
    public synchronized void writeLine(String str) throws IOException {
        w1.write(str + "\n");

    }

    // methods to read:
    public synchronized String readLine() throws IOException {
        return r1.readLine();
    }

    public void closeWrite() throws IOException {
        w1.close();
    }

    public void closeReader() throws IOException {
        r1.close();
    }


}
