package CalculatorWebApi;

import CalculatorWebApi.MathProblem.CalculatorResource;
import CalculatorWebApi.MathProblem.CalculatorService;
import CalculatorWebApi.MathProblem.MathProblemPojo;
import CalculatorWebApi.MathProblem.MathProblemPojoDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.confluent.rest.EmbeddedServerTestHarness;
import io.confluent.rest.RestConfigException;
import org.junit.jupiter.api.Test;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorResourceTest
        extends EmbeddedServerTestHarness<CalculatorConfig, CalculatorApplication>
{
    private final CalculatorService _service;
    private final String BaseUrl = "http://localhost:9998";

    public CalculatorResourceTest() throws RestConfigException
    {
        addResource(new CalculatorResource(config));

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(MathProblemPojo.class, new MathProblemPojoDeserializer());
        mapper.registerModule(module);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();

        _service = retrofit.create(CalculatorService.class);
    }

    @Test
    public void Addition_WhenInputsGreaterThanMaxInt_ShouldReturn200Success() throws IOException {
        // Arrange
        String[] inputs = new String[]{String.valueOf(Integer.MAX_VALUE), String.valueOf(Integer.MAX_VALUE)};
        Call<Void> call = _service.doAddition(inputs);

        // Act
        Response<Void> response = call.execute();

        // Assert
        assertEquals(200, response.code());
    }

    @Test
    public void Audit_WhenManyMathProbsAdded_ShouldReturnCompleteList() throws IOException {
        // Arrange
        int count = 3;
        for(int i = 0; i < count; i++)
        {
            populateRestServiceWithMathProblems();
        }

        Call<List<MathProblemPojo>> call = _service.getAudits();

        // Act
        retrofit2.Response<List<MathProblemPojo>> response = call.execute();

        // Assert
        List<MathProblemPojo> audits = response.body();
        assertEquals(audits.size(), count);
    }

    @Test
    public void Audit_WhenNoMathProbsAdded_ShouldReturnEmptyList() throws IOException {
        // Arrange
        Call<List<MathProblemPojo>> call = _service.getAudits();

        // Act
        retrofit2.Response<List<MathProblemPojo>> response = call.execute();

        // Assert
        List<MathProblemPojo> audits = response.body();
        assertEquals(audits.size(), 0);
    }

    @Test
    public void Division_WhenGivenZero_ShouldReturn400BadRequest() throws IOException {
        // Arrange
        Call<Void> call = _service.doDivision(new String[]{"0", "1"});

        // Act
        retrofit2.Response<Void> response = call.execute();

        // Assert
        assertEquals(400, response.code());
    }

    @Test
    public void Multiplication_WhenGivenZero_ShouldReturn200Success() throws IOException {
        // Act
        Call<Void> call = _service.doMultiplication(new String[]{"0", "1"});
        retrofit2.Response<Void> response = call.execute();

        // Assert
        assertEquals(response.code(), 200);
    }

    private int populateRestServiceWithMathProblems() throws IOException {
        Call<Void> call = _service.doAddition(new String[]{"1", "2", "3"});
        retrofit2.Response<Void> response = call.execute();
        return 1;
    }
}