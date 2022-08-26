package com.example.iotdemo.entity;

import lombok.Data;

@Data
public class DHT11Record {
    private Integer rid;
    private double humidity;
    private double temperature;
    private double heat;
    private String date;
}
