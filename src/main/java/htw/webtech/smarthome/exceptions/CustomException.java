package htw.webtech.smarthome.exceptions;

public class CustomException extends IllegalArgumentException{
    public CustomException(String message) {
        super(message);
    }
}
