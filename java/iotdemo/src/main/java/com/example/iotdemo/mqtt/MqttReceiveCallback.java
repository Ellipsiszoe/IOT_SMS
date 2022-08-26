package com.example.iotdemo.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.iotdemo.service.RecordService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttReceiveCallback implements MqttCallback, IMqttMessageListener {
    @Autowired
    private MqttConfig client;

    @Autowired
    private RecordService recordService;

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message)  {
        System.out.println("Client 接收消息主题 : " + topic);
        System.out.println("Client 接收消息Qos : " + message.getQos());
        System.out.println("Client 接收消息内容 : " + new String(message.getPayload()));
        parseMsg(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    private void parseMsg(MqttMessage msg) {
        JSONObject json = (JSONObject) JSON.parse(msg.toString());
        double humidity = Double.parseDouble(json.getString("humidity"));
        double temperature = Double.parseDouble(json.getString("temperature"));
        double heat = Double.parseDouble(json.getString("heat"));
        System.out.printf("%f %f %f\n", humidity, temperature, heat);
        recordService.saveDHT11Record(humidity, temperature, heat);
    }
}
