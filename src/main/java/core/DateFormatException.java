package core;

/**
 * Created by Alex on 2014-05-14.
 */
public class DateFormatException extends Exception {
    private String message;

    public DateFormatException(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}
