package CalculatorWebApi.MathProblem;

import CalculatorWebApi.*;
import CalculatorWebApi.CalculatorConfig;
import CalculatorWebApi.IntegerOperationEnum;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class CalculatorResource {
    CalculatorConfig config;

    private List<MathProblemPojo> mathProblems = new ArrayList<>();

    public CalculatorResource(CalculatorConfig config)
    {
        this.config = config;
    }

    @GET
    @Path("addition")
    public Response doAddition(@QueryParam("inputs") String[] inputs)
    {
        List<BigDecimal> bigDecimalInputs;
        try
        {
            bigDecimalInputs = BigDecimalUtils.convert(inputs);
        }
        catch (NumberFormatException e)
        {
            return Response.status(400)
                    .entity("Input is not a valid integer.")
                    .build();
        }


        mathProblems.add(new MathProblemPojo(bigDecimalInputs, IntegerOperationEnum.ADD));

        return Response.status(200)
                .entity("Operation successfully performed on supplied inputs.")
                .build();
    }

    @GET
    @Path("subtraction")
    public Response doSubtraction(@QueryParam("inputs") String[] inputs)
    {
        List<BigDecimal> bigDecimalInputs;
        try
        {
            bigDecimalInputs = BigDecimalUtils.convert(inputs);
        }
        catch (NumberFormatException e)
        {
            return Response.status(400)
                    .entity("Input is not a valid integer.")
                    .build();
        }

        mathProblems.add(new MathProblemPojo(bigDecimalInputs, IntegerOperationEnum.SUB));

        return Response.status(200)
                .entity("Operation successfully performed on supplied inputs.")
                .build();
    }

    @GET
    @Path("division")
    public Response doDivision(@QueryParam("inputs") String[] inputs)
    {
        List<BigDecimal> bigDecimalInputs;
        try
        {
            bigDecimalInputs = BigDecimalUtils.convert(inputs);
            if (bigDecimalInputs.contains(BigDecimal.ZERO))
            {
                return Response.status(400)
                        .entity("Invalid inputs. Cannot contain a zero.")
                        .build();
            }
        }
        catch (NumberFormatException e)
        {
            return Response.status(400)
                    .entity("Input is not a valid integer.")
                    .build();
        }

        mathProblems.add(new MathProblemPojo(bigDecimalInputs, IntegerOperationEnum.DIV));

        return Response.status(200)
                .entity("Operation successfully performed on supplied inputs.")
                .build();
    }

    @GET
    @Path("multiplication")
    public Response doMultiplication(@QueryParam("inputs") String[] inputs)
    {
        List<BigDecimal> bigDecimalInputs;
        try
        {
            bigDecimalInputs = BigDecimalUtils.convert(inputs);
        }
        catch (NumberFormatException e)
        {
            return Response.status(400)
                    .entity("Input is not a valid integer.")
                    .build();
        }

        mathProblems.add(new MathProblemPojo(bigDecimalInputs, IntegerOperationEnum.MULT));

        return Response.status(200)
                .entity("Operation successfully performed on supplied inputs.")
                .build();
    }

    @GET
    @Path("audit")
    public String getAuditsAsJsonString()
    {
        String mathProblemsString = MathProblemUtils.convert(mathProblems);
        return mathProblemsString;
    }
}
