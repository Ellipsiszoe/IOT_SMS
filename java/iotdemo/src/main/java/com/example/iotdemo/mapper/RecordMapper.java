package com.example.iotdemo.mapper;

import com.example.iotdemo.entity.DHT11Record;
import com.example.iotdemo.entity.DayRecord;
import com.example.iotdemo.entity.RandomRecord;
import com.example.iotdemo.entity.WeekRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
@Mapper
public interface RecordMapper {

    List<RandomRecord> findRecordByDate(@Param("date1") String date1, @Param("date2") String date2);

    List<DHT11Record> findAllDHT11Record();

    List<WeekRecord> findWeekData(@Param("type") String type, @Param("date") String date);

    List<DayRecord> find24hData(@Param("type") String type, @Param("date") String date);

    Integer insertRandomRecord(RandomRecord record);

    Integer insertDHT11Record(DHT11Record record);
}
