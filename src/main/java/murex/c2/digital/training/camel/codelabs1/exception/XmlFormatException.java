package murex.c2.digital.training.camel.codelabs1.exception;

public class XmlFormatException extends RuntimeException {

    public XmlFormatException(Throwable cause) {
        super(cause);
    }

    public XmlFormatException(String message) {
        super(message);
    }

    public XmlFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}