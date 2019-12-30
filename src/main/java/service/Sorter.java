package service;

import model.Car;

import java.util.ArrayList;
import java.util.Comparator;

//класс отвечает за перегрузку метода sort, где можно вносить от 1 до 5 параметров сортировки
class Sorter {

    void sort(ArrayList<Car> arrayCar, Comparator<Car> anyComp1, Comparator<Car> anyComp2, Comparator<Car> anyComp3,
              Comparator<Car> anyComp4, Comparator<Car> anyComp5) {
        arrayCar.sort(anyComp1.thenComparing(anyComp2.thenComparing(anyComp3.thenComparing(anyComp4).thenComparing(anyComp5))));
        System.out.println(arrayCar);
    }

    void sort(ArrayList<Car> arrayCar, Comparator<Car> anyComp1, Comparator<Car> anyComp2, Comparator<Car> anyComp3,
              Comparator<Car> anyComp4) {
        arrayCar.sort(anyComp1.thenComparing(anyComp2.thenComparing(anyComp3.thenComparing(anyComp4))));
        System.out.println(arrayCar);
    }

    void sort(ArrayList<Car> arrayCar, Comparator<Car> anyComp1, Comparator<Car> anyComp2, Comparator<Car> anyComp3) {
        arrayCar.sort(anyComp1.thenComparing(anyComp2.thenComparing(anyComp3)));
        System.out.println(arrayCar);
    }

    void sort(ArrayList<Car> arrayCar, Comparator<Car> anyComp1, Comparator<Car> anyComp2) {
        arrayCar.sort(anyComp1.thenComparing(anyComp2));
        System.out.println(arrayCar);
    }

    void sort(ArrayList<Car> arrayCar, Comparator<Car> anyComp1) {
        arrayCar.sort(anyComp1);
        System.out.println(arrayCar);
    }
}