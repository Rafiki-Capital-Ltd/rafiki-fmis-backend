package ke.co.rafiki.fmis.exceptions;

public class InternalServerException extends Exception{
    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    public InternalServerException() {
        super();
    }
}
