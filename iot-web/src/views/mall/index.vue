<template>
  <el-row class="home" :gutter="24">
    <el-col :span="8" style="margin-top: 20px">
      <el-card shadow="hover">
        <div class="user">
          <img class="userImg" :src="userImg" />
          <div class="userinfo">
            <p class="name" id="who">{{username}}</p>
            <p class="access" id="role">{{role}}</p>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col style="margin-top: 20px" :span="16">
      <div class="num">
        <el-card
          v-for="item in countData"
          :key="item.name"
          :body-style="{ display: 'flex', padding: 0 }"
        >
          <i
            class="icon"
            :class="'el-icon-' + item.icon"
            :style="{ backgroud: item.color }"
          ></i>
          <div class="detail">
            <p class="num">ID:{{ item.value }}</p>
            <p class="txt">Name:{{ item.name }}</p>
          </div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>
<script>
export default {
  name: "home",
  data() {
    return {
      userImg: require("../../assets/images/user.png"),
      countData: [],
      username: '3111p515',
      role: 'root'
    };
  },
  mounted() {

    let _this = this
    this.getDevice()
    this.getUser()
    // console.log(_this.countData)
  },
  methods: {
    getUser(){
      let _this = this 
      let token = window.localStorage.getItem('token');
      // console.log(token)
      this.$http({
        method: 'post',
        url: 'http://localhost:31151/user/getuser',
        headers: {'token': token}
      })
      .then(function (response) {
        console.log(response.data.data.username)
        _this.username = response.data.data.username
        _this.role = response.data.data.role
      })
      .catch(function(error){
           console.log(error);
      })
    },
    getDevice(){
      let _this = this
      this.$http
      .get("http://localhost:31151/devices/show")
      .then(function (response) {
        // console.log(response.data.data[1]);
        let devices = []
        let temp = {}
        for(var i in response.data.data){
          temp = {
            name: response.data.data[i].deviceName,
            value: response.data.data[i].deviceID,
            icon: "success",
            color: "#2ec7c9"
          }
          // console.log(temp);
          devices.push(temp)
        }
        // console.log(devices);
        _this.countData = devices
        // console.log(_this)
      })
      .catch(function (error) {
        console.log(error);
      });
    }
  }
};
</script>

