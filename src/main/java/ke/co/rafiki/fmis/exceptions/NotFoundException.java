package ke.co.rafiki.fmis.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException() {
        super();
    }
}
