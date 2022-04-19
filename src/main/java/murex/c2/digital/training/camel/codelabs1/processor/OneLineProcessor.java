package murex.c2.digital.training.camel.codelabs1.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class OneLineProcessor  implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        body = body.replaceAll("\\r|\\n| ", "");
        exchange.getIn().setBody(body);
    }
}