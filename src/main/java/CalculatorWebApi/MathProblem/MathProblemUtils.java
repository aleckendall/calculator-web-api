package CalculatorWebApi.MathProblem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class MathProblemUtils
{
    public static String convert(List<MathProblemPojo> mathProblemPojos)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            return objectMapper.writeValueAsString(mathProblemPojos);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
