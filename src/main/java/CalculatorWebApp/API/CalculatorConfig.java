package CalculatorWebApp.API;

import io.confluent.rest.RestConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class CalculatorConfig extends RestConfig {
    private static ConfigDef config;

    public static final String GREETING_CONFIG = "Calculator App";
    private static final String GREETING_CONFIG_DOC = "Calculator template for responses.";
    private static final String GREETING_CONFIG_DEFAULT = "Hello, %s!";

    static {
        config = baseConfigDef()
                .define(GREETING_CONFIG,
                        ConfigDef.Type.STRING,
                        GREETING_CONFIG_DEFAULT,
                        ConfigDef.Importance.HIGH,
                        GREETING_CONFIG_DOC);
    }

    public CalculatorConfig(ConfigDef definition) {
        super(definition);
    }

    public CalculatorConfig(Map<?, ?> props) {
        super(config, props);
    }
}
