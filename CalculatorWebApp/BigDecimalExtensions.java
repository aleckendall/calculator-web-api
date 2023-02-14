package CalculatorWebApp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BigDecimalExtensions
{
    public static List<BigDecimal> convertToBigDecimalList(Integer[] inputs)
    {
        List<BigDecimal> bigDecimalList = new ArrayList<BigDecimal>();
        for (Integer val: inputs)
        {
            bigDecimalList.add(new BigDecimal(val));
        }
        return bigDecimalList;
    }
}
