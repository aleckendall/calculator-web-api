package CalculatorWebApp.MathProblem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface MathProblemService
{
    @GET("calculator/addition")
    Call<Void> doAddition(@Query("inputs") String[] inputs);

    @GET("calculator/subtraction")
    Call<Void> doSubtraction(@Query("inputs") String[] inputs);

    @GET("calculator/multiplication")
    Call<Void> doMultiplication(@Query("inputs") String[] inputs);

    @GET("calculator/division")
    Call<Void> doDivision(@Query("inputs") String[] inputs);

    @GET("calculator/audit")
    Call<List<MathProblemPojo>> getAudits();
}
