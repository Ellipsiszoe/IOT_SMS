#include <WiFi.h>
#include <PubSubClient.h>
#include <DHT.h>
#include <ArduinoJson.h>

#define DHTPIN 16 // 定义16#针脚为DHTPIN

#define DHTTYPE DHT11 // 如果是DHT 11 去掉注释
//#define DHTTYPE DHT22   // DHT22 (AM2302), AM2321均采用此行代码
//#define DHTTYPE DHT21 // 如果是DHT21 (AM2301)去掉注释

// WiFi
const char *ssid = "Honor Magic 2"; // Enter your WiFi name
const char *password = "877877760";  // Enter WiFi password

// MQTT Broker
const char *mqtt_broker = "175.178.80.39";
const char *topic = "esp32/test";
const char *mqtt_username = "admin";
const char *mqtt_password = "public";
const int mqtt_port = 1883;

WiFiClient espClient;
PubSubClient client(espClient);
DHT dht(DHTPIN, DHTTYPE);

void setup() {
 // Set software serial baud to 115200;
 Serial.begin(115200);
 Serial.println("***DHT传感器***测试!");
 dht.begin();
 // connecting to a WiFi network
 WiFi.begin(ssid, password);
 while (WiFi.status() != WL_CONNECTED) {
     delay(500);
     Serial.println("Connecting to WiFi..");
 }
 Serial.println("Connected to the WiFi network");
 //connecting to a mqtt broker
 client.setServer(mqtt_broker, mqtt_port);
 client.setCallback(callback);
 while (!client.connected()) {
     String client_id = "esp32-client-";
     client_id += String(WiFi.macAddress());
     Serial.printf("The client %s connects to the public mqtt broker\n", client_id.c_str());
     if (client.connect(client_id.c_str(), mqtt_username, mqtt_password)) {
         Serial.println("Public emqx mqtt broker connected");
     } else {
         Serial.print("failed with state ");
         Serial.print(client.state());
         delay(2000);
     }
 }
 // publish and subscribe
 client.publish(topic, "Hi EMQX I'm ESP32 ^^");
 client.subscribe(topic);
}

void callback(char *topic, byte *payload, unsigned int length) {
 Serial.print("Message arrived in topic: ");
 Serial.println(topic);
 Serial.print("Message:");
 for (int i = 0; i < length; i++) {
     Serial.print((char) payload[i]);
 }
 Serial.println();
 Serial.println("-----------------------");
}

void loop() {
   client.loop();
   delay(2000); // 设置间隔时间为2000毫秒
   float h = dht.readHumidity();
   // 读取温度或湿度大约需要250毫秒
   float t = dht.readTemperature();
   // 将温度读取为摄氏温度(默认值)
   float f = dht.readTemperature(true);
   // 读取温度为华氏温度 (Fahrenheit = true)
   // 检查是否有读取失败 (重试).
   if (isnan(h) || isnan(t) || isnan(f)) {
      Serial.println("没有从DHT传感器上获取数据!");
      return;
   }
   // 计算华氏温度 (默认)
   float hif = dht.computeHeatIndex(f, h);
   // 计算摄氏温度 (Fahreheit = false)
   float hic = dht.computeHeatIndex(t, h, false);
   Serial.print ("湿度: ");
   Serial.print (h);
   Serial.print (" %\t");
   Serial.print ("温度: ");
   Serial.print (t);
   Serial.print (" *C ");
   Serial.print (f);
   Serial.print (" *F\t");
   Serial.print ("热量指数: ");
   Serial.print (hic);
   Serial.print (" *C ");
   Serial.print (hif);
   Serial.println (" *F");
   StaticJsonDocument<200> json;
   json["humidity"] = h;
   json["temperature"] = t;
   json["heat"] = hif;
   char buffer[200];
   serializeJson(json, buffer);
   Serial.println(buffer);
   client.publish(topic, buffer);
}
