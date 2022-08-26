<template>
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
            prop="humidity"
            label="湿度">
        </el-table-column>
        <el-table-column
            prop="temperature"
            label="温度">
        </el-table-column>
        <el-table-column
            prop="heat"
            label="热量">
        </el-table-column>
        <el-table-column
            prop="date"
            label="日期"
            width="180">
        </el-table-column>
    </el-table>
  </div>

</template>

<script>
export default {
  data() {
    return {
      tableData: []
    };
  },
  mounted(){
      this.getRandomRecord()
  },
  methods: {
      getRandomRecord(){
          let _this = this
          this.$http.get("http://localhost:31151/records/dht11record")
          .then(function (response) {
              console.log(response.data);
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
};
</script>