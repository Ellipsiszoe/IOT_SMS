package com.example.iotdemo.controller;

import com.example.iotdemo.annotation.UserLoginToken;
import com.example.iotdemo.entity.DHT11Record;
import com.example.iotdemo.entity.DayRecord;
import com.example.iotdemo.entity.RandomRecord;
import com.example.iotdemo.entity.WeekRecord;
import com.example.iotdemo.service.RecordService;
import com.example.iotdemo.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController extends BaseController{
    @Autowired
    private RecordService recordService;
//    @UserLoginToken
    @RequestMapping("/day")
    public JsonResult<List<DayRecord>> getDayData(String type, String date){
//        System.out.println(type);
//        System.out.println(date);
        List<DayRecord> data = recordService.getDayData(type,date);
        return new JsonResult<List<DayRecord>>(OK,data);
    }

//    @UserLoginToken
    @RequestMapping("/week")
    public JsonResult<List<WeekRecord>> getWeekData(String type, String date){
        List<WeekRecord> data = recordService.getWeekData(type,date);
        return new JsonResult<List<WeekRecord>>(OK,data);
    }

//    @UserLoginToken
    @RequestMapping("/randomrecord")
    public JsonResult<List<RandomRecord>> getrRandomRecord(String date1, String date2){
        List<RandomRecord> data = recordService.getRecord(date1,date2);
        return new JsonResult<List<RandomRecord>>(OK,data);
    }

//    @UserLoginToken
    @RequestMapping("/dht11record")
    public JsonResult<List<DHT11Record>> getdDHT11Record(){
        List<DHT11Record> data = recordService.getDHT11Record();
        return new JsonResult<List<DHT11Record>>(OK,data);
    }
}