package model;

import java.time.LocalDate;

public class Van extends Car implements Taxi {
    public Van(String number, CarManufacturer CarManufacturer, String model, LocalDate dateOfProduction,
               int mileage, FuelType FuelType, int seat, boolean working, double initialCostInBYN, int lifeTime) {
        super(number, CarManufacturer, model, dateOfProduction, mileage, FuelType, seat, working, initialCostInBYN, lifeTime);
    }

    @Override
    public String toString() {
        return "Микроавтобус{"+super.toString()+"}"+ "\n";

    }
}
