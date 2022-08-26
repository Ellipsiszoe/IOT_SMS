package com.example.iotdemo.mapper;

import com.example.iotdemo.entity.Device;
import com.example.iotdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DeviceMapper {
    //获取所有的设备
    List<Device> findAllDevice();
}
