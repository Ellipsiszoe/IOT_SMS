package com.example.iotdemo.entity;

import lombok.Data;

@Data
public class RandomRecord {
    private Integer rid;
    private String type;
    private double value;
    private String date;
    private String time;
}
