package model;

import java.time.LocalDate;

public class SmallCar extends Car implements Taxi {
    public SmallCar(String number, CarManufacturer carManufacturer, String model, LocalDate dateOfProduction, int mileage,
                    FuelType fuelType, int seat, boolean working, double initialCost, int lifeTime) {
        super(number, carManufacturer, model, dateOfProduction, mileage, fuelType, seat, working, initialCost, lifeTime);
    }

    @Override
    public String toString() {
        return "Легковой автомобиль{"+super.toString()+"}"+ "\n";

    }
}
