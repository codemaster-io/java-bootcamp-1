package org.example;

public class Main {
    public static void main(String[] args) {
        MotorizedVehicle motorizedVehicle = new Bicycle();
        motorizedVehicle.start("Cycle started rolling without paddling");
        motorizedVehicle.print();
//        PaddledVehicle paddledVehicle = new Bicycle();
//        paddledVehicle.start("Cycle started rolling with paddling");
        AutoRickshaw autoRickshaw = new Toto();
        autoRickshaw.honk();
        autoRickshaw.breaks();
        Bicycle bicycle = new Bicycle();
        bicycle.print();
    }
}