package com.example.iotdemo.service;

import com.example.iotdemo.entity.Device;
import com.example.iotdemo.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    public List<Device> getDevice(){
        List<Device> devices  = deviceMapper.findAllDevice();
        return  devices;
    }
}
