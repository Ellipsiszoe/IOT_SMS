package com.example.iotdemo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.iotdemo.entity.RandomRecord;
import com.example.iotdemo.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class StaticScheduleTask {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private RecordService recordService;

    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() throws ParseException {
        Random random = new Random();
        int type = random.nextInt(3);//
        String stype = "";
        switch (type) {
            case 2:
                stype = "humidity";
                break;
            case 1:
                stype = "light";
                break;
            default:
                stype = "temperature";
        }
        double value = random.nextDouble() * 40; // 生成[0,40.0]区间的小数
//        recordService.saveRandomRecord(stype, value);
        RandomRecord record = new RandomRecord();
        record.setType(stype);
        record.setValue(value);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = "2022-06-01 00:00:00";
        String end = "2022-06-11 00:00:00";
        Date startDate = ft.parse(start);
        Date endDate = ft.parse(end);
        long startMillis = startDate.getTime();
        long endMillis = endDate.getTime();
        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
        Date date = new Date(randomMillis);
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter1.format(date);
        String timeString = formatter2.format(date);
        record.setDate(dateString);
        record.setTime(timeString);
        System.out.println(record.getValue() + " " + record.getType() + " " + record.getDate() + " " + record.getTime());
        recordMapper.insertRandomRecord(record);
    }
}
