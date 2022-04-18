package murex.c2.digital.training.camel.codelabs1;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CamelCodelabs1RouteBuilderTest  extends CamelTestSupport {

    static Logger logger = LoggerFactory.getLogger(CamelCodelabs1RouteBuilderTest.class);

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new CamelCodelabs1RouteBuilder();
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {

        Properties myProperties = new Properties();

        try (final InputStream stream = this.getClass().getResourceAsStream("/camel-codelabs1-test.properties")) {
            myProperties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error loading test properties.");
        }
        return myProperties;
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testFileMove() throws Exception {

        // customize the route
        context.getRouteDefinition("MainRoute").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("file://{{camel.codelabs1.output.ok.folder}}").to("mock:ok");
            }
        });

        // fix expected results
        getMockEndpoint("mock:ok").expectedBodiesReceivedInAnyOrder("<deals></deals>");
        getMockEndpoint("mock:ok").expectedHeaderValuesReceivedInAnyOrder(Exchange.FILE_NAME, "correctXmlFile.xml");

        // once route ok and expectation set start camel context
        context.start();

        assertMockEndpointsSatisfied(6, TimeUnit.SECONDS);

        // stop the context
        context.stop();
    }
}