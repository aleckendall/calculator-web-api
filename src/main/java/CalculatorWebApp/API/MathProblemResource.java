package CalculatorWebApp.API;

import CalculatorWebApp.BigDecimalUtils;
import CalculatorWebApp.IntegerOperationEnum;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

import CalculatorWebApp.MathProblemPojo;
import CalculatorWebApp.MathProblemStorageSingleton;

@Path("/calculate/{operation}")
public class MathProblemResource
{
    CalculatorConfig config;

    @Inject
    private MathProblemStorageSingleton mathProblemStorageSingleton;

    public MathProblemResource(CalculatorConfig config)
    {
        this.config = config;
    }

    /*
    Performs a calculation on a list of integers.
    The calculation assumes that the inputs are correctly ordered as-is.
     */
    @GET
    public Response calculate(
            @PathParam("operation") String operation,
            @QueryParam("inputs") String[] inputs)
    {
        IntegerOperationEnum operationEnum;
        try
        {
            operationEnum = IntegerOperationEnum.valueOf(operation.toUpperCase());
        }
        catch(IllegalArgumentException e)
        {
            return Response.status(404)
                    .entity("Operation is not supported: " + operation)
                    .build();
        }

        List<BigDecimal> bigDecimalInputs;
        try
        {
            bigDecimalInputs = BigDecimalUtils.convert(inputs);
        }
        catch (NumberFormatException e)
        {
            return Response.status(404)
                    .entity("Input is not a valid integer.")
                    .build();
        }


        MathProblemPojo mathProblemPojo = new MathProblemPojo(bigDecimalInputs, operationEnum);
        mathProblemStorageSingleton.addMathProblem(mathProblemPojo);

        return Response.status(200)
                .entity("Operation successfully performed on supplied inputs.")
                .build();
    }
}
