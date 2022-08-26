<template>
    <el-form
        :model="form"
        status-icon
        :rules="rules"
        label-width="100px"
        class="login-container"
    >
        <h3 class="login_title">系统登陆</h3>
        <el-form-item
            label="用户名"
            label-width="80px"
            prop="username"
            class="username"
        >
            <el-input
                type="input"
                v-model="form.username"
                auto-complete="off"
                placeholder="请输入帐号"
            >
            </el-input>
        </el-form-item>
        <el-form-item
            label="密码"
            label-width="80px"
            prop="password"
            class="password"
        >
            <el-input
                type="password"
                v-model="form.password"
                auto-complete="off"
                placeholder="请输入密码"
            >
            </el-input>
        </el-form-item>
        <el-form-item class="login_submit">
            <el-button type="primary" @click="login" class="login_submit">登录</el-button>
        </el-form-item>
    </el-form>
</template>
<script>
export default {
    name: "login",
    data(){
        return{
            form: {
                username: '',
                password: ''
            },
            rules:{
                username:[
                    {
                        required: true,
                        message: "请输入用户名",
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        message: "用户名长度不能小于3位",
                        trigger: 'blur'
                    }
                ],
                password:[
                    {
                        required: true,
                        message: "请输入密码",
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    methods: {
        login(){
            let _this = this
            let params = new URLSearchParams()
            params.append('username',_this.form.username)
            params.append('password',_this.form.password)
            this.$http({
                method: 'post',
                url: 'http://localhost:31151/user/login', 
                data:params
            })
            .then(function (response) {
                if(response.data.state === 200){
                    const token = response.data.token
                    console.log(response)
                    // _this.$store.commit('setToken', token)
                    window.localStorage.setItem('token', token);
                    _this.$router.push({name: 'home'})
                }else{
                    alert(response.data.message)
                }
                
            })
            .catch(function (error) {
                console.log(error);
            });
            
        }
    }
}
</script>
<style lang="less" scoped>
.login-container{
    border-radius: 15px;
    background-clip: padding-box;
    margin: 100px auto;
    width: 350px;
    padding: 35px 35px 35px 35px;
    background-color: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac0c6;
}
.login_title{
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
}
.login_submit{
    margin: 10px auto 0px auto;
}
</style>
