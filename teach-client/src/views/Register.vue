<template>
  <body id="poster">
  <el-form class="login-container" label-position="left" label-width="0px">
  <div class="register">
    <h2>面试注册</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <el-label for="username">用户名</el-label>&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input style="width: 75%" clearable v-model="username"  name="username" placeholder="请输入用户名"></el-input>
      </div>

      <div class="form-group">
        <h2></h2>
        <el-label for="username">姓名</el-label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input style="width: 75%" clearable v-model="name"  name="name" placeholder="请输入姓名"></el-input>
      </div>

      <div class="form-group">
        <h2></h2>
        <label htmlFor="password">密码</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input style="width: 75%"  clearable placeholder="请输入密码" v-model="password" show-password></el-input>
      </div>
      <div class="form-group">
        <h2></h2>
        <label htmlFor="password2">重复密码</label>&nbsp;
        <el-input style="width: 75%"  clearable placeholder="请输入重复密码" v-model="password2" show-password></el-input>
      </div>


        <h2></h2>
        <el-label for="sid">学号</el-label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-input style="width: 75%" clearable v-model="sid"  name="sid" placeholder="例：202022300310"></el-input>

      <h2></h2>
      <el-label for="grade">年级</el-label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <el-input style="width: 75%" clearable v-model="grade"  name="grade" type="number" placeholder="例：2020"></el-input>




      <div style="margin-top: 20px" class="form-group">
        <h2></h2>
        <el-button class="btn btn-primary" type="primary" @click='handleSubmit()' style="width: 50%;background: #409EFF;border: none">注册</el-button>
      </div>
    </form>
  </div>
  </el-form>
  </body>
</template>

<script>
import {register} from '@/service/genServ.js'

export default {
  name: "Register",
  data(){
    return{
      username:'',
      password:'',
      password2:'',
      sid:'',
      name:'',
      grade:'',
    }
  },
  methods: {
    handleSubmit() {
      if(this.password!==this.password2){
        this.$message({
          message:  '口令不一致！',
          type: 'warning',
        })
      }if(!this.name||!this.sid||!this.username||!this.password||!this.grade){
        this.$message({
          message:  '所有项目均为必填项！',
          type: 'warning',
        })
      }
        else{
        register({'username':this.username,'password':this.password,'sid':this.sid,'name':this.name,'grade':parseInt(this.grade)}).then(response=>{
          if (response.code === '0') {
            this.$message({
              message:  '成功，跳转到登录页面',
              type: 'success',
            })
            this.$router.go(-1)
          } else {
            this.$message({
              message: response.msg,
              type: 'warning',
            })
          }
        })
      }
    }
  }
}
</script>

<style>
#poster{
  background:url("../assets/register.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
body{
  margin: 0px;
}
.login-container {
  border-radius: 30px;
  background-clip: padding-box;
  margin: 80px auto;
  width: 400px;
  padding: 35px 35px 50px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>