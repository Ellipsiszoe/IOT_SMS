<template>
    <div>
        <div>
            <el-select v-model="value1" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.label">
                </el-option>
            </el-select>
            <span class="demonstration">默认</span>
            <el-date-picker
              v-model="value2"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
            <el-button @click="selectChanged">确定</el-button>
        </div>
        <div id="week" style="width: 100%;height:600px;" ref="week"></div>
    </div>
</template>
<script>
import * as echarts from "echarts"
import moment from 'moment'
export default {
    name: "day",
    data(){
        return{
            time:[],
            average:[],
            options: [
                {
                    value: '选项1',
                    label: 'humidity'
                },
                {
                    value: '选项2',
                    label: 'temperature'
                },
                {
                    value: '选项3',
                    label: 'light'
                }
            ],
            value1: '',
            value2: ''
        }
    },
    mounted(){
        this.echarInit()
    },
    methods: {
        echarInit(){
            const myChart = echarts.init(this.$refs.week)
            let options = {
                title: {
                  text: '七天每天平均数据',
                  textStyle: {
                    color: 'black'
                  }
                },
                tooltip: {
                },
                legend: {
                    data: this.value1
                },
                xAxis: {
                  data: this.time, // 把时间组成的数组接过来，放在x轴上
                },
                yAxis: {
                },
                series: [
                  {
                    data: this.average,
                    type: 'bar',
                    name: '平均值'
                  },
                ]
            }
            myChart.setOption(options)
        },
        selectChanged(){
            let _this = this
            // console.log(_this.value1)
            // console.log(_this.value2)
            if (_this.value1 && _this.value2){
                let date = new Date(this.value2)
                // console.log(moment(date1).format("YYYY-MM-DD"))
                // console.log(moment(date2).format("YYYY-MM-DD"))
                this.$http.get("http://localhost:31151/records/week",{
                    params: {
                        type: _this.value1,
                        date: moment(date).format("YYYY-MM-DD"),
                    }
                })
                .then(function (response) {
                    console.log(response.data);
                    _this.time = []
                    _this.average = []
                    for(var i in response.data.data){
                        _this.time.push(response.data.data[i].date)
                        _this.average.push(response.data.data[i].avg)
                    }
                    // console.log(_this.average);
                    const myChart = echarts.init(_this.$refs.week);
                    myChart.setOption({
                        xAxis: [
                          {
                            data: _this.time
                          }
                        ],
                        series: [
                          {
                            data: _this.average
                          }
                        ]
                    })
                })
                .catch(function (error) {
                    console.log(error);
                });
            } 
        }
    }
}
</script>
