<template>
  <div id="app">

  </div>
</template>

<script>
import Paho from '../paho-mqtt/paho-mqtt'

export default {
  name: 'MyController',
  data () {
    return {
      client: new Paho.MQTT.Client('175.178.80.39', 8083, ''),
      topic: 'esp32/test'
    }
  },
  methods: {
    onConnectedLost : function(responseObject){  
      console.log("onConnectionLost:"+responseObject.errorMessage);
    },
    onMessageArrived : function (message) {
      console.log("onMessageArrived:"+message.payloadString);
    },
    onConnect:function onConnect() {
      // Once a connection has been made, make a subscription and send a message.
      console.log("onConnect");
      this.client.subscribe(this.topic);
      // message = new Paho.MQTT.Message("Hello");
      // message.destinationName = "World";
      // client.send(message);
    },
  },
  created: function() {
    this.client.connect({
      onSuccess: this.onConnect,
      userName: 'mosquitto',
      password: 'mqttttqm'
    });
    this.client.onConnectedLost = this.onConnectedLost;
    this.client.onMessageArrived = this.onMessageArrived;
  }
}
</script>
<style scoped>
</style>