package htw.webtech.smarthome.exceptions;

public class AuthenticationFailException extends IllegalArgumentException{
    public AuthenticationFailException(String message) {
        super(message);
    }
}
