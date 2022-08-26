<template>
  <div>
    <div class="block">
      <span class="demonstration">时间：</span>
      <el-date-picker
        v-model="value1"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期">
      </el-date-picker>
      <el-button @click="getRandomRecord">确定</el-button>
    </div>
    <div>
        <el-table
            :data="tableData"
            border
            style="width: 100%">
            <el-table-column
                prop="rid"
                label="ID">
            </el-table-column>
            <el-table-column
                prop="date"
                label="日期"
                width="180">
            </el-table-column>
            <el-table-column
                prop="time"
                label="时间"
                width="180">
            </el-table-column>
            <el-table-column
                prop="type"
                label="类型">
            </el-table-column>
            <el-table-column
                prop="value"
                label="值">
            </el-table-column>
        </el-table>
    </div>
  </div>

</template>

<script>
import moment from 'moment'
  export default {
    data() {
      return {
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          }
        },
        value1: '',
        value2: '',
        tableData: []
      };
    },
    methods: {
        getRandomRecord(){
            let _this = this
            if (this.value1.length === 2){
                let date1 = new Date(this.value1[0])
                let date2 = new Date(this.value1[1])
                // console.log(moment(date1).format("YYYY-MM-DD"))
                // console.log(moment(date2).format("YYYY-MM-DD"))
                this.$http.get("http://localhost:31151/records/randomrecord",{
                    params: {
                        date1: moment(date1).format("YYYY-MM-DD"),
                        date2: moment(date2).format("YYYY-MM-DD"),
                    }
                })
                .then(function (response) {
                    // console.log(response.data.data[1]);
                    for(var i in response.data.data){
                        _this.tableData.push(response.data.data[i])
                    }
                    console.log(_this.tableData);
                })
                .catch(function (error) {
                    console.log(error);
                });
                } 
        }
    }
  };
</script>