<template xmlns:border="http://www.w3.org/1999/xhtml">
  <Navi/>



  <div  class="form-div">
    <el-form ref="editForm" :model="editForm" :rules="editRules" label-position="left" label-width="100px" status-icon style="margin-top: 30px;">
      <br/>



      <el-row align="middle">
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="姓名" >
            <el-input v-model="name" :disabled="true" maxlength="20" show-word-limit style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="学号" >
            <el-input v-model="sid" :disabled="true"  maxlength="20"  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="年级" >
            <el-input v-model="grade" :disabled="true"  type="number" style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="邮箱" >
            <el-input v-model="email"   style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>



      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="个人介绍" >
            <el-input type="textarea" :rows="5" v-model="introduction" maxlength="120" show-word-limit  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item align="center">
            <el-button type="primary" @click="submit" >提交</el-button>
          </el-form-item>
        </el-col>
      </el-row>


    </el-form>
  </div>

  <div class="icon">
    <label >上传照片</label>
    <el-upload
        class="avatar-uploader"
        action="/api/teach/submitIcon"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        status-icon style="margin-top: 50px;"
    >
      <img v-if="icon" :src="icon"   class="avatar"   alt=""/>
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </div>
</template>

<script>
import {getProfile,submitProfile} from "@/service/genServ";
import Navi from '@/components/Navi'
export default {
  name: "IntervieweeProfile",
  components:{Navi},
  data(){
    return{
      sid:"",
      name:"",
      introduction:"",
      grade:"",
      icon:"",
      email:"",
    }
  },
  created() {
    this.doQuery();

  },
  methods:{
    doQuery(){
      getProfile().then(res=>{
        this.sid=res.data.sid;
        this.name=res.data.name;
        this.email=res.data.email;
        this.grade=res.data.grade;
        this.introduction=res.data.introduction;
        this.icon=res.data.icon;
      })
    },
    submit(){
      if(!this.isEmail(this.email)){
        this.$message({
          message:  '邮件格式不正确',
          type: 'warning',
        })
      }else{
      submitProfile({"sid":this.sid,"name":this.name,"email":this.email,"introduction":this.introduction,"grade":parseInt(this.grade)}).then(res=>{
        if(res.code==='0'){
          this.$message({
            message:  '成功',
            type: 'success',
          })
        }else{
          this.$message({
            message:  res.msg,
            type: 'warning',
          })
        }
      })}
    },
    handleAvatarSuccess(res, file) {
      this.icon = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'||file.type==='image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    isEmail(str){
      var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
      return reg.test(str);
    },
  }
}
</script>

<style>
.icon{
  width: 200px;height: 260px;
  border:1px solid #000;
  float:left;
  margin: 50px 35px 35px 200px;
}
.form-div{
  float:left;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

