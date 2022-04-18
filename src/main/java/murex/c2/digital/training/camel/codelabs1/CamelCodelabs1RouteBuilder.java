package murex.c2.digital.training.camel.codelabs1;

import org.apache.camel.builder.RouteBuilder;

public class CamelCodelabs1RouteBuilder extends RouteBuilder {


    public void configure() throws Exception {

//        from("file://target/test-classes/input/")
//                .routeId("MainRoute") // read files from directory target/test-classes/input/
//                .to("file://target/test-classes/output/"); //write files to directory target/test-classes/output/

        from("file:{{camel.codelabs1.input.folder}}")
                .routeId("MainRoute") // read files from directory target/test-classes/input/
                .to("file:{{camel.codelabs1.output.ok.folder}}"); //write files to directory target/test-classes/output/
    }
}