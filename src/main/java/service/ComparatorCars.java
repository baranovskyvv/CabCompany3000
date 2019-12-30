package service;


import model.Car;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


class ComparatorCars {

    //метод отвечает за алгоритмы сортировки, помещает эти алгоритмы в Map
    Map<String, Comparator<Car>> getComparator() {

        Map<String, Comparator<Car>> comparatorHashMap = new HashMap<>();

        Comparator<Car> byNumber = Comparator.comparing(Car::getNumber);

        Comparator<Car> byCarManufacturer = Comparator.comparing(Car::getCarManufacturer);

        Comparator<Car> byModel = Comparator.comparing(Car::getModel);

        Comparator<Car> byDateOfProduction = Comparator.comparing(Car::getDateOfProduction);

        Comparator<Car> byMileage = Comparator.comparingInt(Car::getMileage);

        Comparator<Car> byFuelType = Comparator.comparing(Car::getFuelType);

        Comparator<Car> bySeat = Comparator.comparingInt(Car::getSeat);

        Comparator<Car> byWorking = Comparator.comparing(Car::isWorking);

        Comparator<Car> byInitialCost = Comparator.comparingDouble(Car::getInitialCost);

        Comparator<Car> byLifeTime = Comparator.comparingInt(Car::getLifeTime);

        comparatorHashMap.put("1", byNumber);
        comparatorHashMap.put("2", byCarManufacturer);
        comparatorHashMap.put("3", byModel);
        comparatorHashMap.put("4", byDateOfProduction);
        comparatorHashMap.put("5", byMileage);
        comparatorHashMap.put("6", byFuelType);
        comparatorHashMap.put("7", bySeat);
        comparatorHashMap.put("8", byWorking);
        comparatorHashMap.put("9", byInitialCost);
        comparatorHashMap.put("10", byLifeTime);

        return comparatorHashMap;

    }
}