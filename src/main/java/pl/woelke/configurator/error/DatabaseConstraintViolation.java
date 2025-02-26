package pl.woelke.configurator.error;

public class DatabaseConstraintViolation extends Exception {

    public DatabaseConstraintViolation(String message) {
        super(message);
    }

    public DatabaseConstraintViolation(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConstraintViolation(Throwable cause) {
        super(cause);
    }

}
