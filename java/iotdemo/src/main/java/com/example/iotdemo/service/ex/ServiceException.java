package com.example.iotdemo.service.ex;

public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    //  频繁
    public ServiceException(String message) {
        super(message);
    }

    //  频繁
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
