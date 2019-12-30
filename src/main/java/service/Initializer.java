package service;


import db.Database;
import exceptions.IncorrectException;
import model.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;


//Класс отвечает за общение с пользователем
public class Initializer {
    private Database database = new Database();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Validator validator = new Validator();
    private ArrayList<Car> cars;

    //блок отвечает за считывание текстового файла
    {
        try {
            cars = database.getCarsFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        System.out.println("Добрый день! Вас приветствует программа \"Таксопарк 3000\"!");
    }

    //метод отвечает за главное меню
    public void starter() throws IOException {

        System.out.println("Введите номер функции которая для Вас необходима:" + "\n" +
                "1. Вывод всего автотранспорта." + "\n" +
                "2. Рассчет стоимости автопарка на сегодняшний день." + "\n" +
                "3. Сортировка автопарка по необходимым критериям." + "\n" +
                "4. Поиск грузового транспорта по количеству сидений и грузоподъемности" + "\n" +
                "5. Добавление транспортной единицы." + "\n" +
                "6. Удаление транспортной единицы" + "\n" +
                "7. Измененеие характеристик транспортной единицы" + "\n" +
                "0. Выход");

        String function = reader.readLine();
        switch (function) {
            case "1":
                showList();
                break;
            case "2":
                getDepreciation();
                break;
            case "3":
                sortCars();
                break;
            case "4":
                findCar();
                break;
            case "5":
                addCar();
                break;
            case "6":
                removeCar();
                break;
            case "7":
                updateCar();
                break;
            case "0":
                reader.close();
                System.out.println("Спасибо, что пользуетесь нашим программным продуктом!");
                System.exit(0);
            default:
                System.out.println("Ошибка. Данного значения не существует. Проверьте номер функции " +
                        "и попробуйте еще раз.");
        }

        starter();
    }

    //метод отвечает за реализацию обновление данных
    private void updateCar() throws IOException {
        System.out.println("Введите гос. номер автомобиля который необходимо изменить:");
        String number = reader.readLine();
        database.updateCar(number, cars, reader);
    }

    //метод отвечает за реализацию добавление нового автомобиля
    private void addCar() {
        System.out.println("Данные должны быть введены согласно шаблону и разделены  ; . " + "\n" +
                "Шаблон для легкового авто: госномер(1234-AA-1);Производитель(на английском языке)" + "\n" +
                ";Модель авто;дата производства(2000-01-02);" + "\n" +
                "пробег;Вид топлива(на английском и с большой буквы);количество сидений;" + "\n" +
                "Работает ил не работает авто(true,false);стоимость авто;срок службы в месяцах" + "\n" +
                "Шаблон для грузового авто: госномер(1234-AA-1);Производитель(на английском языке);" + "\n" +
                "дата производства(2000-01-02);пробег;Вид топлива(на английском языке);" + "\n" +
                "количество сидений;Работает или не работает авто(true,false);" +
                "стоимость авто(в бел.руб.);срок службы в месяцах;грузоподъёмность(в кг);" + "\n" +
                "высота грузового отсека;ширина;длинна;");
        try {
            String line = reader.readLine();
            database.addCar(line, cars);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //метод отвечает за реализацию сортировки, по различным параметрам
    private void sortCars() throws IOException {
        Sorter sorter = new Sorter();
        ComparatorCars carComparator = new ComparatorCars();
        Map<String, Comparator<Car>> comparatorMap = carComparator.getComparator();

        System.out.println("Выберите переменные(не больше 5) по которым будет проводиться сортировка:" + "\n" +
                "1) Госномер" + "\n" +
                "2) Производитель" + "\n" +
                "3) Модель авто" + "\n" +
                "4) Дата производства" + "\n" +
                "5) Пробег" + "\n" +
                "6) Тип топлива" + "\n" +
                "7) Количество сидений" + "\n" +
                "8) Рабочее состояние" + "\n" +
                "9) Первоначальная стоимость" + "\n" +
                "10)Срок службы" + "\n" +
                "Вводить данные необходимо через ЗАПЯТУЮ например(5,1,3)");
        String[] values = reader.readLine().trim().split(",");
        int length = values.length;
        if (length > 5) {
            try {
                throw new IncorrectException("Введено больше 5 значений в параметры сортировки");
            } catch (IncorrectException e) {
                e.printStackTrace();
            }
        }
        for (String value : values) {
            try {
                validator.validateValue(value);
            } catch (IncorrectException e) {
                e.printStackTrace();
                starter();
            }
        }
        if (length == 1) {
            sorter.sort(cars, comparatorMap.get(values[0]));
        } else if (length == 2) {
            sorter.sort(cars, comparatorMap.get(values[0]), comparatorMap.get(values[1]));
        } else if (length == 3) {
            sorter.sort(cars, comparatorMap.get(values[0]), comparatorMap.get(values[1]), comparatorMap.get(values[2]));
        } else if (length == 4) {
            sorter.sort(cars, comparatorMap.get(values[0]), comparatorMap.get(values[1]), comparatorMap.get(values[2]),
                    comparatorMap.get(values[3]));
        } else if (length == 5) {
            sorter.sort(cars, comparatorMap.get(values[0]), comparatorMap.get(values[1]), comparatorMap.get(values[2]),
                    comparatorMap.get(values[3]), comparatorMap.get(values[4]));
        }
    }

    //метод отвечает за подсчет общей остаточной стоимости транспортных средств
    private void getDepreciation() {
        Depreciation depreciation = new Depreciation();
        double deprecations = 0;
        for (Car car : cars) {
            double price = car.getInitialCost();
            int lifeTime = car.getLifeTime();
            LocalDate past = car.getDateOfProduction();
            deprecations = deprecations + depreciation.calculateDepreciation(price, lifeTime, past);
        }
        System.out.println(new DecimalFormat("#.##").format(deprecations));
    }

    //метод отвечает за вывод списка авто
    private void showList() {
        System.out.println(cars);
    }

    // метод отвечает за реализацию поиска авто
    private void findCar() throws IOException {
        Finder finder = new Finder(cars);
        System.out.print("Введите необходимое количество сидений:");
        try {
            int seat = validator.validateInt(reader.readLine());
            System.out.println("Введите вес перевозимого груза");
            int weight = validator.validateInt(reader.readLine());
            finder.findValues(seat, weight);
        } catch (IncorrectException e) {
            e.printStackTrace();
        }
    }

    //метод отвечает за реализацию удаления авто
    private void removeCar() throws IOException {
        System.out.println("Введите номер автомобиля:");
        try {
            String number = validator.validateNumber(reader.readLine());
            database.removeCar(number, cars);
        } catch (IncorrectException e) {
            e.printStackTrace();
        }
    }
}
