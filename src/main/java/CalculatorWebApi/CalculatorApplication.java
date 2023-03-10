package CalculatorWebApi;

import CalculatorWebApi.MathProblem.CalculatorResource;
import io.confluent.rest.Application;
import io.confluent.rest.RestConfig;
import io.confluent.rest.RestConfigException;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.util.security.Constraint;

import javax.ws.rs.core.Configurable;
import java.util.TreeMap;

public class CalculatorApplication extends Application<CalculatorConfig>
{
    public CalculatorApplication(CalculatorConfig config)
    {
        super(config);
    }

    @Override
    public void setupResources(Configurable<?> config, CalculatorConfig appConfig) {
        config.register(new CalculatorResource(appConfig));
        config.register(new OpenApiResource());
    }

    @Override
    protected LoginAuthenticator createAuthenticator()
    {
        String method = this.config.getString("authentication.method");
        if(method.equalsIgnoreCase(RestConfig.AUTHENTICATION_METHOD_BASIC))
            return new DummyBasicAuthenticator();
        throw new UnsupportedOperationException("Not allowed !");
    }

    @Override
    protected ConstraintMapping createGlobalAuthConstraint() {
        Constraint constraint = new Constraint();
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"admin"}); // Note: decorating resource with @PermitAll had no effect

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setConstraint(constraint);
        mapping.setMethod("*");
        mapping.setPathSpec("/calculator/audit");
        return mapping;
    }

    public static void main(String[] args) {
        try {
            TreeMap<String,String> settings = new TreeMap<>();

            // Configure Listener
            final String httpUri = "http://localhost:8080";
            settings.put(RestConfig.LISTENERS_CONFIG, httpUri);

            // Enable AuthN
            settings.put(RestConfig.AUTHENTICATION_METHOD_CONFIG, RestConfig.AUTHENTICATION_METHOD_BASIC);

            CalculatorConfig config = new CalculatorConfig(settings);
            CalculatorApplication app = new CalculatorApplication(config);
            app.start();
            app.join();
        } catch (RestConfigException e) {
            System.exit(1);
        } catch (Exception e) {
        }
    }
}
