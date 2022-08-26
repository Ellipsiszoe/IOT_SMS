package com.example.iotdemo.service.ex;

public class MyMqttException extends ServiceException{
    public MyMqttException() {
        super();
    }

    public MyMqttException(String message) {
        super(message);
    }

    public MyMqttException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyMqttException(Throwable cause) {
        super(cause);
    }

    protected MyMqttException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
