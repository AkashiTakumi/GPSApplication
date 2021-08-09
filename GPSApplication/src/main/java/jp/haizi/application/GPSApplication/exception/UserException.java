package jp.haizi.application.GPSApplication.exception;

public class UserException extends RuntimeException{
    public static final int EXISTED_USER = 101;
    int code;

    public UserException(int code, String message) {
        super(message);
        this.code = code;
    }
}
