package nuts.lib.commom.configurer;

public class VerificationException extends RuntimeException{

    public VerificationException(String message) {
        super(message);
    }

    public VerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
