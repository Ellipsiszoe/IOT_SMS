package com.example.iotdemo.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class WeekRecord {
    private Date date;
    private Double avg;
}
