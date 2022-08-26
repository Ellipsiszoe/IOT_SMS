<template>
  <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column
      label="用户名"
      width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.username }}</span>
      </template>
    </el-table-column>
    <el-table-column
      label="权限"
      width="180">
      <template slot-scope="scope">
        <el-tag size="medium">{{ scope.row.role }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleReset(scope.$index, scope.row)">重置密码</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    data() {
      return {
        tableData: []
      }
    },
    mounted(){
        this.getUser()
    },
    methods: {
        handleEdit(index, row) {
            console.log(index, row);
        },
        handleReset(index, row) {
            console.log(index, row);
        },
        getUser(){
            let _this = this
            this.$http.get("http://localhost:31151/user/show")
            .then(function (response) {
                // console.log(response.data.data[1]);
                for(var i in response.data.data){
                    let user = {
                        username: response.data.data[i].username,
                        role: response.data.data[i].role
                    }
                    _this.tableData.push(user)
                }
                console.log(_this.tableData);
            })
            .catch(function (error) {
                console.log(error);
            });
        }   
    }
  }
</script>