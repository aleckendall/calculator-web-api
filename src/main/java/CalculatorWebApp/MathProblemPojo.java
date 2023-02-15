package CalculatorWebApp;

import java.math.BigDecimal;
import java.util.List;

/*
    Performs an operations on a list of integers. The result is stored as a BigDecimal to
    preserve the result of a division operation.
 */
public class MathProblemPojo
{
    private final List<BigDecimal> inputs;
    private final IntegerOperationEnum operation;
    private BigDecimal result;

    public MathProblemPojo(List<BigDecimal> inputs, IntegerOperationEnum operation)
    {
        this.inputs = inputs;
        this.operation = operation;
        this.result = this.calculateResult(operation, inputs);
    }

    private BigDecimal calculateResult(IntegerOperationEnum operation, List<BigDecimal> inputs)
    {
        BigDecimal result;
        BigDecimal identityElement;
        switch(operation)
        {
            case ADD:
                identityElement = new BigDecimal(0);
                result = inputs.stream().reduce(identityElement, BigDecimal::add);
                break;
            case SUB:
                identityElement = new BigDecimal(0);
                result = inputs.stream().reduce(identityElement, BigDecimal::subtract);
                break;
            case DIV:
                identityElement = new BigDecimal(1);
                result = inputs.stream().reduce(identityElement, BigDecimal::divide);
                break;
            case MULT:
                identityElement = new BigDecimal(1);
                result = inputs.stream().reduce(identityElement, BigDecimal::multiply);
                break;
            default:
                String exceptionMsg = String.format("BigDecimal operation is not supported: %s", operation);
                throw new UnsupportedOperationException(exceptionMsg);
        }

        return result;
    }

    public List<BigDecimal> getInputs() {
        return inputs;
    }

    public IntegerOperationEnum getOperation() {
        return operation;
    }

    public BigDecimal getResult() {
        return result;
    }
}
