package service;

import exceptions.IncorrectException;
import model.CarManufacturer;
import model.FuelType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {

    //регулярное выражение соответствует шаблонам 1234-BC-7 или 5-TAX-1234.
    private final String NUMBER = "^[0-9]{1}[-0-9]{1}[0-9,E,T,Y,I,O,P,A,H,K,X,C,B,M]{2}[-E,T,Y,I,O,P,A,H,K,X,C,B,M]{2}" +
            "[0-9E,T,Y,I,O,P,A,H,K,X,C,B,M]{1}[-0-9]{1}[0-9]{1,2}$";
    private final String VALUE = "[0-9]";

    //валидатор отвечает за проверку введенных данных(должно быть введено число)
    void validateValue(String value) throws IncorrectException {
        Pattern pattern = Pattern.compile(VALUE);
        Matcher matcher = pattern.matcher(value);
        boolean test = matcher.find();
        if (!test) {
            throw new IncorrectException("Должно быть введено число, а не " + value);
        }
    }

    //валидатор отвечает за введение гос номера в соответствующем формате
    public String validateNumber(String number) throws IncorrectException {
        Pattern pattern = Pattern.compile(NUMBER);
        Matcher matcher = pattern.matcher(number);
        boolean test = matcher.find();
        if (test) {
            return number;
        } else
            throw new IncorrectException("Некорректный номер автомобиля " + number +
                    "(проверьте что включен английский язык). " +
                    "Сделайте согласно шаблонам: 1234-BC-7 или 5-TAX-1234");

    }

    //валидатор проверяет соответствует ли введенный производитель, производителям из списка(enam)
    public CarManufacturer validateCarManufacturer(String made) {
        CarManufacturer carManufacturer;
        try {
            carManufacturer = CarManufacturer.valueOf(made.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Производителя вашего автомобиля " + made +
                    " нет в списке, проверьте правильность введенных данных или обратитесь в службу поддержки");
        }
        return carManufacturer;
    }

    //валидатор проверяет в правильном ли формате записано число, а так же выполняет прорверку, что введенная
// дата не позже сегодняшнего числа
    public LocalDate validateDate(String date) throws IncorrectException {
        LocalDate lc;
        try {
            lc = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Ошибка программы.В записи данных даты." + date +
                    " Дата записывается в формате 2000-11-01", date, 1);
        }
        if (lc.isAfter(LocalDate.now())) {
            throw new IncorrectException("Дата не может быть больше, чем сегоднешняя дата.");
        }

        return lc;
    }

    //валидатор проверяет что число введенное явялется целым и больше 0
    public int validateInt(String number) throws IncorrectException {
        int numeric;
        try {
            numeric = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Число " + number + " должно быть целым и не содержать лишних символов");
        }
        if (numeric <= 0) {
            throw new IncorrectException("Ошибка в числе " + numeric + "Число должно быть больше нуля");
        }
        return numeric;
    }

    //валидатор проверяет соответствует ли введенный тип топлива, типу топлива из списка(enam)
    public FuelType validateFuelType(String fuelType) {
        FuelType source;
        try {
            source = FuelType.valueOf(fuelType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Данного топлива автомобиля " + fuelType +
                    " нет в списке, проверьте правильность введенных данных или обратитесь в службу поддержки");
        }
        return source;
    }

    //валидатор проверяет, что число введенное больше 0
    public double validateDouble(String number) throws IncorrectException {
        double numeric;
        try {
            numeric = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Число " + number + " должно быть целым и не содержать лишних символов");
        }
        if (numeric <= 0) {
            throw new IncorrectException("Ошибка в числе " + numeric + "Число должно быть больше нуля");
        }
        return numeric;
    }
// валидатор проверяет корректность введения boolean
    public boolean validateBoolean(String value) throws IncorrectException {
        if (value.equals("true")) {
            return true;
        } else if (value.equals("false")) {
            return false;
        } else {
            throw new IncorrectException("Ошибка, программ должна принимать занчение либо true, либо false.");
        }
    }


}

