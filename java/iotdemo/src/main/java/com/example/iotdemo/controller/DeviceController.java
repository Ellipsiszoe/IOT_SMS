package com.example.iotdemo.controller;

import com.example.iotdemo.annotation.UserLoginToken;
import com.example.iotdemo.entity.Device;
import com.example.iotdemo.service.DeviceService;
import com.example.iotdemo.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/devices")
@RestController
public class DeviceController extends BaseController{
    @Autowired
    private DeviceService deviceService;

//    @UserLoginToken
    @RequestMapping("/show")
    public JsonResult<List<Device>> getDevices(HttpServletRequest httpServletRequest){
        List<Device> data = deviceService.getDevice();
        return new JsonResult<List<Device>>(OK,data);
    }
}
