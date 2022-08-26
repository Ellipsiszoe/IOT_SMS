package com.example.iotdemo.service;

import com.example.iotdemo.entity.DHT11Record;
import com.example.iotdemo.entity.DayRecord;
import com.example.iotdemo.entity.RandomRecord;
import com.example.iotdemo.entity.WeekRecord;
import com.example.iotdemo.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordMapper recordMapper;

    public List<DayRecord> getDayData(String type, String date) {
        List<DayRecord> dayRecords = recordMapper.find24hData(type,date);
        return dayRecords;
    }

    public List<WeekRecord> getWeekData(String type, String date) {
        List<WeekRecord> weekRecords = recordMapper.findWeekData(type,date);
        return weekRecords;
    }

    public List<RandomRecord> getRecord(String date1, String date2){
        List<RandomRecord> records = recordMapper.findRecordByDate(date1,date2);
        return records;
    }

    public List<DHT11Record> getDHT11Record(){
        List<DHT11Record> records = recordMapper.findAllDHT11Record();
        return records;
    }

    public Integer saveDHT11Record(Double humidity, Double temperature, Double heat){
        DHT11Record record = new DHT11Record();
        record.setHumidity(humidity);
        record.setTemperature(temperature);
        record.setHeat(heat);
        Date date = new Date();
        long times = date.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(date);
        record.setDate(datetime);
        return recordMapper.insertDHT11Record(record);
    }

}
