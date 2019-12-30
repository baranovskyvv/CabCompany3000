
import exceptions.IncorrectException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.Validator;

import java.time.format.DateTimeParseException;

public class TestValidator {
    private Validator validator;

    @Before
    public void testValidator() {
        validator = new Validator();
    }

    @Test
    public void testValidatorNumber() throws IncorrectException {
        String testValue1 = validator.validateNumber("1593-BC-7");
        String testValue2 = validator.validateNumber("7-TAX-3574");
        Assert.assertEquals("1593-BC-7", testValue1);
        Assert.assertEquals("7-TAX-3574", testValue2);
    }

    @Test(expected = IncorrectException.class)
    public void testValidatorNumberBadValue() throws IncorrectException {
        validator.validateNumber("1593-B-7");

    }

    @Test
    public void testValidatorCarManufacturer() {
        String testValue1 = String.valueOf(validator.validateCarManufacturer("Renault"));
        String testValue2 = String.valueOf(validator.validateCarManufacturer("BMW"));
        Assert.assertEquals("RENAULT", testValue1);
        Assert.assertEquals("BMW", testValue2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidatorCarManufacturerBadValue() throws IllegalArgumentException {
        validator.validateCarManufacturer("Пежо");


    }

    @Test
    public void testValidatorDate() throws IncorrectException {
        String testValue1 = String.valueOf(validator.validateDate("2000-11-01"));
        String testValue2 = String.valueOf(validator.validateDate("2019-10-17"));
        Assert.assertEquals("2000-11-01", testValue1);
        Assert.assertEquals("2019-10-17", testValue2);
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidatorDateBadValue() throws DateTimeParseException, IncorrectException {
        validator.validateDate("200-511-01");
    }

    @Test(expected = IncorrectException.class)
    public void testValidatorDateMoreThenNow() throws IncorrectException {
        validator.validateDate("2020-11-01");
    }


    @Test
    public void testValidatorInt() throws IncorrectException {
        String testValue1 = String.valueOf(validator.validateInt("2810"));
        String testValue2 = String.valueOf(validator.validateInt("159"));
        Assert.assertEquals("2810", testValue1);
        Assert.assertEquals("159", testValue2);
    }

    @Test(expected = NumberFormatException.class)
    public void testValidatorIntBadValue() throws IncorrectException {
        validator.validateInt("159.5");
    }

    @Test(expected = IncorrectException.class)
    public void testValidatorIntLessThenOne() throws IncorrectException {
        validator.validateInt("0");
    }

    @Test
    public void testValidatorFuelType() {
        String testValue1 = String.valueOf(validator.validateFuelType("PETROL"));
        String testValue2 = String.valueOf(validator.validateFuelType("ELECTRICITY"));
        Assert.assertEquals("PETROL", testValue1);
        Assert.assertEquals("ELECTRICITY", testValue2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidatorBadFuelType() {
        validator.validateFuelType("PETRO");

    }

    @Test
    public void testValidatorDouble() throws IncorrectException {
        String testValue1 = String.valueOf(validator.validateDouble("159.6"));
        String testValue2 = String.valueOf(validator.validateDouble("159.99"));
        Assert.assertEquals("159.6", testValue1);
        Assert.assertEquals("159.99", testValue2);
    }

    @Test(expected = NumberFormatException.class)
    public void testValidatorDoubleBadValue() throws IncorrectException {
        validator.validateDouble("159q5");
    }

    @Test(expected = IncorrectException.class)
    public void testValidatorDoubleLessThenOne() throws IncorrectException {
        validator.validateDouble("0");
    }

    @Test
    public void testValidatorBoolean() throws IncorrectException {
        boolean value1 = validator.validateBoolean("true");
        boolean value2 = validator.validateBoolean("false");
        Assert.assertTrue(value1);
        Assert.assertFalse(value2);
    }
    @Test(expected = IncorrectException.class)
    public void testValidatorBadBoolean() throws IncorrectException {
      validator.validateBoolean("519");}

}
