package com.example.framework.appium.exception;

public class AppiumException extends RuntimeException {

    public AppiumException(Exception e) {
        super(e);
    }
    public AppiumException(String message) {
        super(message);
    }
    public AppiumException(Throwable cause) {
        super(cause);
    }
}
