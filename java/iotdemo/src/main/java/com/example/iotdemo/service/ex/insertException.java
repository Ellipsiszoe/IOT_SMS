package com.example.iotdemo.service.ex;

public class insertException extends ServiceException {
    public insertException() {
        super();
    }

    public insertException(String message) {
        super(message);
    }

    public insertException(String message, Throwable cause) {
        super(message, cause);
    }

    public insertException(Throwable cause) {
        super(cause);
    }

    protected insertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
