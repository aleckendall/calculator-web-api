package CalculatorWebApp;

import java.math.BigDecimal;
import java.util.List;

/*
    Performs an operations on a list of integers. The result is stored as a BigDecimal to
    preserve the result of a division operation.
 */
public class MathProblem
{
    private Integer[] inputs;
    private IntegerOperation operation;
    private BigDecimal result;

    public MathProblem(Integer[] inputs, IntegerOperation operation)
    {
        this.inputs = inputs;
        this.operation = operation;
        this.result = this.calculateResult(operation, inputs);
    }

    private BigDecimal calculateResult(IntegerOperation operation, Integer[] inputs)
    {
        List<BigDecimal> bigDecimalInputs = BigDecimalExtensions.convertToBigDecimalList(inputs);

        BigDecimal result;
        BigDecimal identityElement;
        switch(operation)
        {
            case ADD:
                identityElement = new BigDecimal(0);
                result = bigDecimalInputs.stream().reduce(identityElement, BigDecimal::add);
                break;
            case SUB:
                identityElement = new BigDecimal(0);
                result = bigDecimalInputs.stream().reduce(identityElement, BigDecimal::subtract);
                break;
            case DIV:
                identityElement = new BigDecimal(1);
                result = bigDecimalInputs.stream().reduce(identityElement, BigDecimal::divide);
                break;
            case MULT:
                identityElement = new BigDecimal(1);
                result = bigDecimalInputs.stream().reduce(identityElement, BigDecimal::multiply);
                break;
            default:
                String exceptionMsg = String.format("Integer operation is not supported: %s", operation);
                throw new UnsupportedOperationException(exceptionMsg);
        }

        return result;
    }

    public Integer[] getInputs() {
        return inputs;
    }

    public IntegerOperation getOperation() {
        return operation;
    }

    public BigDecimal getResult() {
        return result;
    }
}
