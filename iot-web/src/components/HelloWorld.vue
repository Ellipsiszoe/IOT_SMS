<template>
  <div id="main" style="width: 100%;height:600px;" ref="main">
  </div>
</template>
 
<script>
import * as echarts from "echarts";
// import axios from "axios";
import Paho from '../paho-mqtt/paho-mqtt'
export default {
  name: 'homePage',
  data() {
    return{
      client: new Paho.MQTT.Client('175.178.80.39', 8083, ''),
      topic: 'esp32/test',
      time: (function () { // 立即执行函数
        let now = new Date();  // 获得当前的时间
        let res = []; // 存放时间的数组
        let len = 5; // 要存几个时间？
        while (len--) {
          res.unshift(now.toLocaleTimeString().replace(/^\D*/, '')); // 转换时间，大家可以打印出来看一下
          now = new Date(+now - 2000) // 延迟几秒存储一次？
        }
        return res;
      })(),
      humidity: [0, 0, 0, 0, 0],
      temperature: [0, 0, 0, 0, 0],
      heat: [0, 0, 0, 0, 0]
    }
  },
  created: function() {
    this.client.connect({
      onSuccess: this.onConnect,
      userName: 'mosquitto',
      password: 'mqttttqm'
    })
    this.client.onConnectedLost = this.onConnectedLost
    this.client.onMessageArrived = this.onMessageArrived
  },
  mounted() {
    this.test()
  },
  methods: {
    test() {
      // 官方示例 var myChart = echarts.init(document.getElementById('main'));  
      const myChart = echarts.init(this.$refs.main); // 我们可以这样写
      // const time = (function () { // 立即执行函数
      //   let now = new Date();  // 获得当前的时间
      //   let res = []; // 存放时间的数组
      //   let len = 5; // 要存几个时间？
      //   while (len--) {
      //     res.unshift(now.toLocaleTimeString().replace(/^\D*/, '')); // 转换时间，大家可以打印出来看一下
      //     now = new Date(+now - 2000) // 延迟几秒存储一次？
      //   }
      //   return res;
      // })();
      // const dataOne = (function () { // 5个随机数，大家可随意定义
      //   let res = [];
      //   let len = 5;
      //   while (len--) {
      //     res.push(Math.round(Math.random() * 1000));
      //   }
      //   return res;
      // })();
      // const dataTwo = (function () { // 5个随机数
      //   let res = [];
      //   let len = 5;
      //   while (len--) {
      //     res.push(Math.round(Math.random() * 1000));
      //   }
      //   return res;
      // })();
      //配置项
      console.log(this.humidity)
      let options = {
        title: {
          text: '传感器实时折线图',
          textStyle: {
            color: 'black'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#283b56'
            }
          }
        },
        legend: {},
        xAxis: {
          type: 'category',
          data: this.time, // 把时间组成的数组接过来，放在x轴上
          boundaryGap: true
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.humidity,
            type: 'line',
            name: '湿度',
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            },
            markLine: {
              data: [{ type: 'average', name: '平均值' }]
            }
          },
          {
            data: this.temperature,
            name: '温度',
            type: 'line',
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            },
            markLine: {
              data: [{ type: 'average', name: '平均值' }]
            }
          },
          {
            data: this.heat,
            name: '热量',
            type: 'line',
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            },
            markLine: {
              data: [{ type: 'average', name: '平均值' }]
            }
          }
        ]
      }
      // 按照指定的周期（以毫秒计）来调用函数或计算表达式
      // setInterval(function () {
      //   let nowTime = new Date().toLocaleTimeString().replace(/^\D*/, '');
      //   time.shift()
      //   time.push(nowTime)
      //   dataOne.shift()
      //   dataOne.push(Math.round(Math.random() * 1000))
      //   dataTwo.shift()
      //   dataTwo.push(Math.round(Math.random() * 1000))
      //   // console.log(dataOne)
      //   //很多朋友可能要接后端接口,把数据替换下来既可以了
      //   // axios.get('你的url').then(res => {
      //   //   console.log(res)
      //   // })
      //   myChart.setOption({
      //     xAxis: [
      //       {
      //         data: time
      //       }
      //     ],
      //     series: [
      //       {
      //         data: dataOne
      //       },
      //       {
      //         data: dataTwo
      //       }
      //     ]
      //   })
      // }, 2000)
      myChart.setOption(options)
    },
    onConnectedLost : function(responseObject){  
      console.log("onConnectionLost:"+responseObject.errorMessage)
    },
    onMessageArrived : function (message) {
      console.log("onMessageArrived:"+message.payloadString)
      let json = String(message.payloadString)
      console.log(json)
      let obj = JSON.parse(json)
      console.log(obj.humidity, obj.temperature, obj.heat)
      const myChart = echarts.init(this.$refs.main);
      this.humidity.shift()
      this.humidity.push(Number(obj.humidity).toFixed(2))
      this.temperature.shift()
      this.temperature.push(Number(obj.temperature).toFixed(2))
      this.heat.shift()
      this.heat.push(Number(obj.heat).toFixed(2))
      console.log(this.temperature)
      let nowTime = new Date().toLocaleTimeString().replace(/^\D*/, '');
      this.time.shift()
      this.time.push(nowTime)
      myChart.setOption({
        xAxis: [
          {
            data: this.time
          }
        ],
        series: [
          {
            data: this.humidity
          },
          {
            data: this.temperature
          },
          {
            data: this.heat
          }
        ]
      })
    },
    onConnect:function onConnect() {
      // Once a connection has been made, make a subscription and send a message.
      console.log("onConnect")
      this.client.subscribe(this.topic)
      // message = new Paho.MQTT.Message("Hello")
      // message.destinationName = "World"
      // client.send(message)
    },
  }
}
</script>
<style scoped lang="scss">
</style>