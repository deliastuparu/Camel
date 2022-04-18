package murex.c2.digital.training.camel.codelabs1;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelCodelabs1 {

    public static void main(String[] args)  throws Exception {

        CamelContext context = new DefaultCamelContext();
        try {
            // load configuration file
            PropertiesComponent pc = new PropertiesComponent();
            pc.setLocation("classpath:camel-codelabs1.properties");
            context.addComponent("properties", pc);
            // add route
            context.addRoutes(new CamelCodelabs1RouteBuilder());

            context.start();

            // wait for 60 sec for files to be processed
            Thread.sleep(60000);

        } finally {
            context.stop();
        }
    }
}