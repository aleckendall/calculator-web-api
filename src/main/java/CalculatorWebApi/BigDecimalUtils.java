package CalculatorWebApi;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class BigDecimalUtils
{
    /*
        Throws NumberFormatException is any string within vals is not a valid integer.
     */
    public static List<BigDecimal> convert(String[] vals)
    {
        List<BigDecimal> bigDecimalList = Arrays.stream(vals).map(el -> new BigDecimal(el)).toList();
        return bigDecimalList;
    }
}
