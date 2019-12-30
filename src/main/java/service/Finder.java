package service;

import model.Car;
import model.Truck;

import java.util.ArrayList;

class Finder {

    private final ArrayList<Car> list;

    Finder(ArrayList<Car> list) {
        this.list = list;
    }
//метод ищет грузовые авто, по необходимому количеству сидений и грузоподъемности
    void findValues(int seat, int carryingCapacity) {
        list.stream()
                .filter(car -> car instanceof Truck &&
                        car.getSeat() >= seat &&
                        ((Truck) car).getCarryingCapacity() > carryingCapacity)
                .forEach(System.out::println);
    }

}
