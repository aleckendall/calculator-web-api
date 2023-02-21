package CalculatorWebApi;

import io.confluent.rest.RestConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class CalculatorConfig extends RestConfig {
    private static ConfigDef config;

    static {
        config = baseConfigDef();
    }

    public CalculatorConfig() {
        super(config);
    }

    public CalculatorConfig(Map<?, ?> props) {
        super(config, props);
    }
}
