package murex.c2.digital.training.camel.codelabs1.exception;

public class EmptyXmlFileException  extends RuntimeException {

    public EmptyXmlFileException(Throwable cause) {
        super(cause);
    }

    public EmptyXmlFileException(String message) {
        super(message);
    }

    public EmptyXmlFileException(String message, Throwable cause) {
        super(message, cause);
    }
}