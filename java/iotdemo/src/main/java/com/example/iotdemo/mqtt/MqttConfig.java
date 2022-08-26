package com.example.iotdemo.mqtt;

import com.example.iotdemo.service.ex.MyMqttException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MqttConfig {
    public static MqttClient mqttClient = null;
    private static MemoryPersistence memoryPersistence = null;
    private static MqttConnectOptions mqttConnectOptions = null;

    @Autowired
    private MqttReceiveCallback mqttReceiveCallback;

    @Value("${spring.mqtt.url}")
    private String serverURI;

    @Value("${spring.mqtt.clientId}")
    private String clientId;

    @Value("${spring.mqtt.username}")
    private String username;


    @Value("${spring.mqtt.password}")
    private String password;

    // 运行自启动
    @PostConstruct
    public void init() throws MqttException {
        //初始化连接设置对象
        mqttConnectOptions = new MqttConnectOptions();
        //初始化MqttClient
        if (null != mqttConnectOptions) {
            //true可以安全地使用内存持久性作为客户端断开连接时清除的所有状态
            mqttConnectOptions.setCleanSession(true);
            //设置连接超时
            mqttConnectOptions.setConnectionTimeout(10);

            //设置账号密码
            mqttConnectOptions.setUserName(username);
            mqttConnectOptions.setPassword(password.toCharArray());

            //设置持久化方式
            memoryPersistence = new MemoryPersistence();
            if (null != memoryPersistence && null != clientId) {
                try {
                    mqttClient = new MqttClient(serverURI, clientId, memoryPersistence);
                } catch (MqttException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {

            }
        } else {
            // 可以改成抛出异常
            throw new MyMqttException("mqttConnectOptions对象为空");
        }

//        System.out.println(mqttClient.isConnected());
        //设置连接和回调
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                //创建回调函数对象
                //MqttRecieveCallback mqttReceriveCallback = new MqttRecieveCallback();
                //客户端添加回调函数
                // mqttClient.setCallback(mqttReceriveCallback);
                //创建连接
                try {
//                    System.out.println("创建连接");
                    mqttClient.connect(mqttConnectOptions);
                } catch (MqttException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            throw new MyMqttException("mqttClient为空");
        }

//        System.out.println(mqttClient.isConnected());

        if (mqttClient.isConnected()) {
            try {
                //添加回调方法1
                mqttClient.subscribeWithResponse("esp32/test", 0, (IMqttMessageListener) mqttReceiveCallback);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭连接
    @PreDestroy
    public void closeConnect() {
        //关闭存储方式
        if (null != memoryPersistence) {
            try {
                memoryPersistence.close();
            } catch (MqttPersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            throw new MyMqttException("memoryPersistence is null");
        }

        //关闭连接
        if (null != mqttClient) {
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                    mqttClient.close();
                } catch (MqttException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                throw new MyMqttException("mqttClient is not connect");
            }
        } else {
            throw new MyMqttException("mqttClient is null");
        }
    }

    //    发布消息
    public void publishMessage(String pubTopic, String message, int qos) throws MqttException {
        if (null != mqttClient && mqttClient.isConnected()) {
            System.out.println("发布消息" + mqttClient.isConnected());
            System.out.println("id:" + mqttClient.getClientId());
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(message.getBytes());
            MqttTopic topic = mqttClient.getTopic(pubTopic);

            if (null != topic) {
                try {
                    MqttDeliveryToken publish = topic.publish(mqttMessage);
                    if (!publish.isComplete()) {
                        System.out.println("消息发布成功");
                    }
                } catch (MqttException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            reConnect();
        }
    }

    //重新连接
    public void reConnect() throws MqttException {
        if (null != mqttClient) {
            if (!mqttClient.isConnected()) {
                if (null != mqttConnectOptions) {
                    try {
                        mqttClient.connect(mqttConnectOptions);
                    } catch (MqttException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    throw new MyMqttException("mqttConnectOptions is null");
                }
            } else {
                throw new MyMqttException("mqttClient is null or connect");
            }
        } else {
            init();
        }
    }

    //订阅主题
    public void subTopic(String topic) {
        if (null != mqttClient && mqttClient.isConnected()) {
            try {
                mqttClient.subscribe(topic, 1);
            } catch (MqttException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            throw new MyMqttException("mqttClient is error");
        }
    }

    //清空主题
    public void cleanTopic(String topic) {
        if (null != mqttClient && !mqttClient.isConnected()) {
            try {
                mqttClient.unsubscribe(topic);
            } catch (MqttException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            throw new MyMqttException("mqttClient is error");
        }
    }

}
