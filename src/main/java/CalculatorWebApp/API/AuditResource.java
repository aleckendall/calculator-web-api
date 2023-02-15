package CalculatorWebApp.API;

import CalculatorWebApp.MathProblemConverter;
import CalculatorWebApp.MathProblemPojo;
import CalculatorWebApp.MathProblemStorageSingleton;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/audit")
public class AuditResource
{
    CalculatorConfig config;

    @Inject
    private MathProblemStorageSingleton mathProblemStorageSingleton;

    public AuditResource(CalculatorConfig config)
    {
        this.config = config;
    }

    @GET
    @Produces("application/json")
    public Response doGetAsJson()
    {
        List<MathProblemPojo> processedMathProblems = mathProblemStorageSingleton.getMathProblems();
        String processedMathProblemsString = MathProblemConverter.convert(processedMathProblems);

        return Response.ok(processedMathProblemsString)
                .build();
    }
}
