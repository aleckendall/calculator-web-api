package CalculatorWebApp.MathProblem;

import CalculatorWebApp.IntegerOperationEnum;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MathProblemPojoDeserializer extends StdDeserializer<MathProblemPojo> {
    public MathProblemPojoDeserializer() {
        this(null);
    }

    public MathProblemPojoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MathProblemPojo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        BigDecimal result = new BigDecimal(node.get("result").asText());

        List<BigDecimal> inputs = new ArrayList<>();
        final JsonNode inputNodes = node.get("inputs");
        for (final JsonNode inputNode: inputNodes) {
            inputs.add(new BigDecimal(inputNode.asText()));
        }

        IntegerOperationEnum operation = IntegerOperationEnum.valueOf(node.get("operation").asText());

        return new MathProblemPojo(inputs, operation, result);
    }
}

