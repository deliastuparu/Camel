package murex.c2.digital.training.camel.codelabs1;

import murex.c2.digital.training.camel.codelabs1.exception.EmptyXmlFileException;
import murex.c2.digital.training.camel.codelabs1.exception.XmlFormatException;
import murex.c2.digital.training.camel.codelabs1.processor.XmlValidityCheckProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class CamelCodelabs1RouteBuilder extends RouteBuilder {


    public void configure() throws Exception {

//        from("file://target/test-classes/input/")
//                .routeId("MainRoute") // read files from directory target/test-classes/input/
//                .to("file://target/test-classes/output/"); //write files to directory target/test-classes/output/

        XmlValidityCheckProcessor xmlValidityCheckProcessor = new XmlValidityCheckProcessor();
        onException(EmptyXmlFileException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "the xml doesn't contain deal tag : ${body}")
                .to("file://{{camel.codelabs1.output.empty.file.folder}}");
         onException(XmlFormatException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "it is not an XML : ${body}")
                .to("file://{{camel.codelabs1.output.wrong.format.folder}}")
                .end();
        from("file:{{camel.codelabs1.input.folder}}")
            .routeId("MainRoute") // read files from directory target/test-classes/input/
            .process(xmlValidityCheckProcessor)
            .log("Body is ${body}")
            .to("file:{{camel.codelabs1.output.ok.folder}}"); //write files to directory target/test-classes/output/





    }
}