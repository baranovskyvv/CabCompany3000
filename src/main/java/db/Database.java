package db;

import exceptions.IncorrectException;
import model.*;
import service.Validator;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;

public class Database {
    private Validator validator = new Validator();

    //метод отвечает за создание списка в котром будут введены корректные данные, а так же без дубликаций гос.номеров
    public ArrayList<Car> getCarsFromFile() throws Exception {
        BufferedReader reader = getBufferedReader();
        ArrayList<Car> cars = new ArrayList<>();
        return checkDuplicate(reader, cars);
    }

    //метод отвечает за добавление авто в список и текстовый файл согласно шаблонам
    public void addCar(String line, ArrayList<Car> cars) {
        String[] carDetails = line.trim().split(";");
        if (!carDetails[0].equals("")) {
            try {
                String number = carDetails[0];
                try {
                    checkDuplicate(cars, number);
                } catch (IncorrectException e) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("Автомобиль не добавлен.");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    return;
                }
                cars.add(checkAndCreateCar(carDetails));
                saveInFile(cars);
                System.out.println("Автомобиль добавлен!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //метод отвечает за удаление авто из списка и из текстового файла по гос.номеру
    public void removeCar(String number, ArrayList<Car> cars) throws IOException {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getNumber().equals(number)) {
                cars.remove(i);
                System.out.println("Автомобиль с гос.номером " + number + " удален");
                break;
            }
        }
        saveInFile(cars);
    }

    //метод отвечает за сохранение корректных данных в файл
    private void saveInFile(ArrayList<Car> cars) throws IOException {
        FileWriter writer = new FileWriter("src\\main\\resources\\fileWithCars.txt", false);
        BufferedWriter bufferWriter = new BufferedWriter(writer);

        for (Car car : cars) {
            String number = car.getNumber();
            String carManufacturer = String.valueOf(car.getCarManufacturer());
            String model = car.getModel();
            String date = String.valueOf(car.getDateOfProduction());
            String mileage = String.valueOf(car.getMileage());
            String fuelType = String.valueOf(car.getFuelType());
            String seat = String.valueOf(car.getSeat());
            String working = String.valueOf(car.isWorking());
            String price = String.valueOf(car.getInitialCost());
            String lifeTime = String.valueOf(car.getLifeTime());
            if (car instanceof Truck) {
                String carryingCapacityInKg = String.valueOf(((Truck) car).getCarryingCapacity());
                String baggageHighInMM = String.valueOf(((Truck) car).getBaggageHigh());
                String baggageWidthInMM = String.valueOf(((Truck) car).getBaggageWidth());
                String baggageLengthInMM = String.valueOf(((Truck) car).getBaggageLength());
                bufferWriter.write(number + ";" + carManufacturer + ";" + model +
                        ";" + date + ";" + mileage + ";" + fuelType + ";" + seat + ";" + working + ";" + price + ";"
                        + lifeTime + ";" + carryingCapacityInKg + ";" + baggageHighInMM + ";" + baggageWidthInMM + ";"
                        + baggageLengthInMM + "\n");
            } else
                bufferWriter.write(number + ";" + carManufacturer + ";" + model +
                        ";" + date + ";" + mileage + ";" + fuelType + ";" + seat + ";" + working + ";" + price + ";" + lifeTime + "\n");
        }

        bufferWriter.close();
    }

    //метод проверки дублирование гос.номеров при добавлении или изменении автомобиля
    private void checkDuplicate(ArrayList<Car> cars, String number) throws IncorrectException {
        for (Car car : cars) {
            if (car.getNumber().equals(number)) {
                throw new IncorrectException("Введен дублирующийся номер автомобиля " + car.getNumber());
            }
        }
    }

    //метод проверки текстового файла на дубликацию гос.номеров автомобилей
    private ArrayList<Car> checkDuplicate(BufferedReader reader, ArrayList<Car> cars) throws Exception {
        getCars(reader, cars);
        cars.sort(Comparator.comparing(Car::getNumber));

        for (int i = 0; i < cars.size() - 1; i++) {
            if (cars.get(i).hashCode() == cars.get(i + 1).hashCode()) {
                throw new IncorrectException("Введен дублирующийся номер автомобиля " + cars.get(i).getNumber() +
                        ". Исправьте запись в fileWithCars.txt");
            }
        }
        return cars;
    }

    //метод осуществляет получение одной строчки из текстового файла и разбиение её на отдельные части с занисением в массив
    private void getCars(BufferedReader reader, ArrayList<Car> cars) throws Exception {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] carDetails = line.trim().split(";");
            if (!carDetails[0].equals("")) {
                cars.add(checkAndCreateCar(carDetails));
            }
        }

    }

    //метод отвечает за определение типа транспортного средства, прорверку входящих данных
// и создание транспортных средств определенного типа
    private Car checkAndCreateCar(String[] carDetails) throws Exception {
        int i = 0;
        if (carDetails.length == 10 || carDetails.length == 14) {
            String number = validator.validateNumber(carDetails[i++]);
            CarManufacturer carManufacturer = validator.validateCarManufacturer(carDetails[i++]);
            String model = carDetails[i++];
            LocalDate date = validator.validateDate(carDetails[i++]);
            int mileage = validator.validateInt(carDetails[i++]);
            FuelType fuelType = validator.validateFuelType(carDetails[i++]);
            int seat = validator.validateInt(carDetails[i++]);
            boolean working = validator.validateBoolean(carDetails[i++]);
            double price = validator.validateDouble(carDetails[i++]);
            int lifeTime = validator.validateInt(carDetails[i++]);
            if (carDetails.length == 14) {
                int carryingCapacityInKg = validator.validateInt(carDetails[i++]);
                int baggageHighInMM = validator.validateInt(carDetails[i++]);
                int baggageWidthInMM = validator.validateInt(carDetails[i++]);
                int baggageLengthInMM = validator.validateInt(carDetails[i]);

                return createTruck(number, carManufacturer, model, date, mileage, fuelType, seat, working, price,
                        lifeTime, carryingCapacityInKg, baggageHighInMM, baggageWidthInMM, baggageLengthInMM);
            }
            return createCar(number, carManufacturer, model, date, mileage, fuelType, seat, working, price, lifeTime);
        } else {
            throw new IncorrectException("Нехватка или излишки данных об авто." +
                    "Данные должны быть введены согласно шаблону и разделены  ; . " +
                    "Шаблон для легкового авто: госномер(1234-AA-1);Производитель(на английском языке, с заглавной" +
                    " буквы);Модель авто;дата производства(2000-01-02);" +
                    "пробег;Вид топлива(на английском и с большой буквы);количество сидений;" +
                    "Работает ил не работает авто(true,false);стоимость авто;срок службы в месяцах" +
                    "Шаблон для грузового авто: госномер(1234-AA-1);Производитель(на английском языке, с заглавной буквы);" +
                    "дата производства(2000-01-02);" +
                    "пробег;Вид топлива(на английском и с большой буквы);" +
                    "количество сидений;Работает или не работает авто(true,false);" +
                    "стоимость авто;срок службы в месяцах;грузоподъёмность(в кг);" +
                    "высота грузового отсека;ширина;длинна; ");
        }
    }

    //метод отвечает за создание легкого авто либо микроавтобуса
    private Car createCar(String number, CarManufacturer carManufacturer, String model, LocalDate date,
                          int mileage, FuelType fuelType, int seat, boolean working, double price, int lifeTime) {
        if (seat < 6) {
            return new SmallCar(number, carManufacturer, model, date, mileage, fuelType, seat, working, price, lifeTime);
        } else {
            return new Van(number, carManufacturer, model, date, mileage, fuelType, seat, working, price, lifeTime);
        }
    }

    //метод отвечает за создание грузового автомобиля
    private Car createTruck(String number, CarManufacturer carManufacturer, String model, LocalDate date,
                            int mileage, FuelType fuelType, int seat, boolean working, double price, int lifeTime,
                            int carryingCapacityInKg, int baggageHighInMM,
                            int baggageWidthInMM, int baggageLengthInMM) {
        return new Truck(number, carManufacturer, model, date, mileage, fuelType, seat, working,
                price, lifeTime, carryingCapacityInKg, baggageHighInMM, baggageWidthInMM, baggageLengthInMM);
    }

    //метод отвечает за считываение файла
    private BufferedReader getBufferedReader() throws FileNotFoundException {
        File file = new File("src\\main\\resources\\fileWithCars.txt");
        return new BufferedReader(new FileReader(file));
    }

    //метод отвечает за обновление данных автомобиля
    public void updateCar(String number, ArrayList<Car> cars, BufferedReader reader) throws IOException {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getNumber().equals(number)) {
                System.out.println("Автомобиль и его исходные данные:");
                System.out.println("1. Гос.номер: " + cars.get(i).getNumber());
                System.out.println("2. Производитель: " + cars.get(i).getCarManufacturer());
                System.out.println("3. Модель:" + cars.get(i).getModel());
                System.out.println("4. Дата производства:" + cars.get(i).getDateOfProduction());
                System.out.println("5. Пробег:" + cars.get(i).getMileage());
                System.out.println("6. Тип топлива:" + cars.get(i).getFuelType());
                System.out.println("7. Количество мест:" + cars.get(i).getSeat());
                System.out.println("8. Является ли рабочим:" + cars.get(i).isWorking());
                System.out.println("9. Начальная стоимость:" + cars.get(i).getInitialCost());
                System.out.println("10. Срок службы:" + cars.get(i).getLifeTime());
                if (cars.get(i) instanceof Truck) {
                    System.out.println("11. Грузоподъемность: " + ((Truck) cars.get(i)).getCarryingCapacity());
                    System.out.println("12. Высота грузового отсека: " + ((Truck) cars.get(i)).getBaggageHigh());
                    System.out.println("13. Ширина грузового отсека: " + ((Truck) cars.get(i)).getBaggageWidth());
                    System.out.println("14. Длинна грузового отсека: " + ((Truck) cars.get(i)).getBaggageLength());
                }
                System.out.println("0. Выйти в главное меню");
                System.out.println("Введите номер данных которые необходимо изменить:");
                int num = Integer.parseInt(reader.readLine());
                switch (num) {
                    case 1:
                        System.out.println("Введите исправленнный номер авто, в соответствии с форматом:");
                        try {
                            String newNumber = validator.validateNumber(reader.readLine());
                            checkDuplicate(cars, newNumber);
                            cars.get(i).setNumber(newNumber);
                            System.out.println("Изменения гос номера внесены");
                        } catch (IncorrectException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        System.out.println("Введите изменеие в производителе:");
                        try {
                            CarManufacturer manufacturer = validator.validateCarManufacturer(reader.readLine());
                            cars.get(i).setCarManufacturer(manufacturer);
                            System.out.println("Изменения производителя номера внесены");
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        System.out.println("Введите измеенение в модели авто:");
                        String model = reader.readLine();
                        cars.get(i).setModel(model);
                        System.out.println("Изменения модели авто внесены");

                        break;
                    case 4:
                        System.out.println("Введите изменение в дате производства:");
                        LocalDate date;

                        try {
                            date = validator.validateDate(reader.readLine());
                            cars.get(i).setDateOfProduction(date);
                            System.out.println("Изменения в дату производства внесены");

                        } catch (DateTimeParseException | IncorrectException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        System.out.println("Введите измение в пробеге:");
                        try {
                            int mileage = validator.validateInt(reader.readLine());
                            cars.get(i).setMileage(mileage);
                            System.out.println("Изменения в пробег авто внесены");

                        } catch (IncorrectException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        System.out.println("Введите измение в топливе:");
                        try {
                            FuelType fuelType = validator.validateFuelType(reader.readLine());
                            cars.get(i).setFuelType(fuelType);
                            System.out.println("Изменения в вид топлива внесены");

                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        System.out.println("Введите измение в количестве мест:");

                        try {
                            int seat = validator.validateInt(reader.readLine());
                            cars.get(i).setSeat(seat);
                            System.out.println("Изменения в количество сидений внесены");

                        } catch (IncorrectException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        System.out.println("Введите измение в работе авто(true/false):");

                        try {
                            boolean working = validator.validateBoolean(reader.readLine());
                            cars.get(i).setWorking(working);
                            System.out.println("Изменения в состояние внесены");

                        } catch (IncorrectException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 9:
                        System.out.println("Введите измение в начальной стоимости:");
                        try {
                            double price = validator.validateDouble(reader.readLine());
                            cars.get(i).setInitialCost(price);
                            System.out.println("Изменения стоимости внесены");

                        } catch (IncorrectException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 10:
                        System.out.println("Введите измение в сроке службе(в месяцах):");
                        try {
                            int lifeTime = validator.validateInt(reader.readLine());
                            cars.get(i).setLifeTime(lifeTime);
                            System.out.println("Изменения сроков службы внесены");

                        } catch (IncorrectException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (cars.get(i) instanceof Truck) {
                    switch (num) {
                        case 11:
                            System.out.println("Введите измение в грузоподъемности:");

                            try {
                                int carryingCapacity = validator.validateInt(reader.readLine());
                                ((Truck) cars.get(i)).setCarryingCapacity(carryingCapacity);
                                System.out.println("Изменения грузоподъёмности внесены");
                            } catch (IncorrectException | NumberFormatException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 12:
                            System.out.println("Введите измение в высоте грузового отсека:");

                            try {
                                int high = validator.validateInt(reader.readLine());
                                ((Truck) cars.get(i)).setBaggageHigh(high);
                                System.out.println("Изменения высоты грузового отсека внесены");

                            } catch (IncorrectException | NumberFormatException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 13:
                            System.out.println("Введите измение в ширене грузового отсека:");

                            try {
                                int width = validator.validateInt(reader.readLine());
                                ((Truck) cars.get(i)).setBaggageWidth(width);
                                System.out.println("Изменения ширины грузового отсека внесены");
                            } catch (IncorrectException | NumberFormatException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 14:
                            System.out.println("Введите измение в длинне грузового отсека:");

                            try {
                                int length = validator.validateInt(reader.readLine());
                                ((Truck) cars.get(i)).setBaggageLength(length);
                                System.out.println("Изменения длинны грузового отсека внесены");
                            } catch (IncorrectException | NumberFormatException e) {
                                e.printStackTrace();
                            }
                            break;

                    }
                }
                saveInFile(cars);

            }
        }

    }
}






