package CalculatorWebApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class MathProblemConverter
{
    public static MathProblemPojo[] convert(String mathProblemJSONs)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            return objectMapper.readValue(mathProblemJSONs, MathProblemPojo[].class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

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
