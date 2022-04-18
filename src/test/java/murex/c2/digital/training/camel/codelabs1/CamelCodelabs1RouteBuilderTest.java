package murex.c2.digital.training.camel.codelabs1;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
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

    public String getFileContent (String fileName) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(fileName));
        String str = "";
        while(sc.hasNextLine()){
            str = str + sc.nextLine() + (sc.hasNextLine()?System.lineSeparator():"");
        }
        return str;
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
        getMockEndpoint("mock:ok").expectedBodiesReceivedInAnyOrder("<deals><deal><id>2</id></deal></deals>");
        getMockEndpoint("mock:ok").expectedHeaderValuesReceivedInAnyOrder(Exchange.FILE_NAME, "correctXmlFile.xml");

        // once route ok and expectation set start camel context
        context.start();

        // send test file
        ProducerTemplate template = context.createProducerTemplate();
        template.sendBodyAndHeader("file://{{camel.codelabs1.input.folder}}" , this.getFileContent("target/test-classes/input/correctXmlFile.xml"), Exchange.FILE_NAME, "correctXmlFile.xml");

        assertMockEndpointsSatisfied(6, TimeUnit.SECONDS);

        // stop the context
        context.stop();
    }


    @Test
    public void testEmptyFile() throws Exception {

        // customize the route
        context.getRouteDefinition("MainRoute").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("file://{{camel.codelabs1.output.empty.file.folder}}").to("mock:empty");
            }
        });

        // fix expected results
        getMockEndpoint("mock:empty").expectedHeaderValuesReceivedInAnyOrder(Exchange.FILE_NAME, "emptyXmlFile.xml");

        // once route ok and expectation set start camel context
        context.start();

        // send test file
        ProducerTemplate template = context.createProducerTemplate();
        template.sendBodyAndHeader("file://{{camel.codelabs1.input.folder}}" , this.getFileContent("target/test-classes/input/emptyXmlFile.xml"), Exchange.FILE_NAME, "emptyXmlFile.xml");

        assertMockEndpointsSatisfied(6, TimeUnit.SECONDS);

        // stop the context
        context.stop();
    }

    @Test
    public void testIncorrectFile() throws Exception {

        // customize the route
        context.getRouteDefinition("MainRoute").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("file://{{camel.codelabs1.output.wrong.format.folder}}").to("mock:format");
            }
        });

        // fix expected results
        getMockEndpoint("mock:format").expectedHeaderValuesReceivedInAnyOrder(Exchange.FILE_NAME, "incorrectXmlFile.xml");

        // once route ok and expectation set start camel context
        context.start();

        // send test file
        ProducerTemplate template = context.createProducerTemplate();
        template.sendBodyAndHeader("file://{{camel.codelabs1.input.folder}}" , this.getFileContent("target/test-classes/input/incorrectXmlFile.xml"), Exchange.FILE_NAME, "incorrectXmlFile.xml");

        assertMockEndpointsSatisfied(6, TimeUnit.SECONDS);

        // stop the context
        context.stop();
    }
}