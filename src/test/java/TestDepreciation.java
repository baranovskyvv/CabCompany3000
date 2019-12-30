import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.Depreciation;

import java.time.LocalDate;

public class TestDepreciation {
 private Depreciation depreciation;

    @Before
    public void testDepreciation(){
depreciation=new Depreciation();
    }

    @Test
    public void testCalculateMonthlyCharge(){
       String a= String.valueOf(depreciation.calculateDepreciation(12000,12, LocalDate.parse("2019-01-01")));
        Assert.assertEquals("1000.0",a);
    }
}
