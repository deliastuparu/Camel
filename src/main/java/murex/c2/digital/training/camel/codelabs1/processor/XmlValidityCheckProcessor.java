package murex.c2.digital.training.camel.codelabs1.processor;

import murex.c2.digital.training.camel.codelabs1.exception.EmptyXmlFileException;
import murex.c2.digital.training.camel.codelabs1.exception.XmlFormatException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static org.apache.camel.builder.xml.XPathBuilder.xpath;

public class XmlValidityCheckProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String body = exchange.getIn().getBody(String.class);
        try {
            String deals = xpath("/deals/deal").evaluate(exchange.getContext(), body);
            if(deals == null || deals.length() == 0)
                throw new EmptyXmlFileException("Empty Xml with body [" + body +"]");
        } catch (Exception cause) {
            throw new XmlFormatException(cause);
        }

    }
}