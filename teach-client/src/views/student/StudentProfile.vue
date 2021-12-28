<template>
  <Navi/>
  <div  class="form-div">
    <el-form ref="editForm" :model="editForm" :rules="editRules" label-position="left"  status-icon style="margin-top: 15px; width: 800px; " label-width="80px">
      <br/>

      <el-row align="middle">
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="中文姓">
            <el-input v-model="xing" maxlength="20" placeholder="例：张" show-word-limit style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="中文名" >
            <el-input v-model="ming" maxlength="20" placeholder="例：博宁" show-word-limit style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="英文姓" >
            <el-input v-model="lastName" maxlength="20" placeholder="例：Zhang" show-word-limit style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="英文名" >
            <el-input v-model="firstName" maxlength="20" placeholder="例：Boning" show-word-limit style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="学号" >
            <el-input v-model="sid" placeholder="例：202022300310"  maxlength="20"  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="年级" >
            <el-input v-model="grade" placeholder="例：2020" type="number" style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="邮箱" >
            <el-input v-model="email"  placeholder="例：xiaozhang20010804@qq.com"  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>



      <el-row>
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item label="电话" >
            <el-input v-model="tel" placeholder="例：18560380627"  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item label="学院" >
            <el-input v-model="college"  placeholder="例：软件学院"  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item label="专业" >
            <el-input v-model="major"  placeholder="例：软件工程"  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item label="身份证号" >
            <el-input v-model="idCard"  placeholder="例：370303200108040019"  style="width: 500px;background-color: #f4f4f5"/>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="性别" >
            <el-select v-model="sex" placeholder="请选择性别" style="width: 500px;background-color: #f4f4f5">
              <el-option label="女" :value="false" :key="sex===false"></el-option>
              <el-option label="男" :value="true" :key="sex===true"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item label="T恤尺寸" >
            <el-select v-model="TShirt" placeholder="请选择尺寸" style="width: 500px;background-color: #f4f4f5">
              <el-option label="XXXS" value="XXXS"></el-option>
              <el-option label="XXS" value="XXS"></el-option>
              <el-option label="XS" value="XS"></el-option>
              <el-option label="S" value="S"></el-option>
              <el-option label="M" value="M"></el-option>
              <el-option label="L" value="L"></el-option>
              <el-option label="XL" value="XL"></el-option>
              <el-option label="XXL" value="XXL"></el-option>
              <el-option label="XXXL" value="XXXL"></el-option>
              <el-option label="XXXXL" value="XXXXL"></el-option>
            </el-select>
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

      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="10">
          <el-form-item  >
            <el-switch
                style="display: block"
                v-model="provided"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="提供给赞助商"
                inactive-text="不提供给赞助商">
            </el-switch>

          </el-form-item>
        </el-col>
      </el-row>



      <el-row >
        <el-col :span="4">
          &nbsp;
        </el-col>
        <el-col :span="10">
          <el-form-item align="center">
            <el-button type="primary" @click="submit">提交</el-button>
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
  name: "StudentProfile",
  components:{Navi},
  data(){
    return{
      icon:"",
      TShirt:"",
      college:"",
      firstName:"",
      lastName:"",
      xing:"",
      ming:"",
      sex:true,
      sid:"",
      email:"",
      tel:"",
      grade:null,
      major:"",
      provided:false,
      introduction:"",
      idCard:"",
    }
  },
  created() {
    this.doQuery();
  },
  methods:{
    doQuery(){
      getProfile().then(res=>{
        this.icon=res.data.icon;
        this.TShirt=res.data.TShirt;
        this.college=res.data.college;
        this.firstName=res.data.firstName;
        this.lastName=res.data.lastName;
        this.xing=res.data.xing;
        this.ming=res.data.ming;
        this.sex=res.data.sex;
        this.sid=res.data.sid;
        this.email=res.data.email;
        this.tel=res.data.tel;
        this.grade=res.data.grade;
        this.major=res.data.major;
        this.introduction=res.data.introduction;
        this.provided=res.data.provided;
        this.idCard=res.data.idCard;
      })
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
    submit(){
      if(!this.isEmail(this.email)){
        this.$message({
          message:  '邮件格式不正确',
          type: 'warning',
        })
      }else if(!this.isTel(this.tel)){
        this.$message({
          message:  '电话格式不正确（中国大陆手机号码，不带区号）',
          type: 'warning',
        })
      }else if(!this.isIdCard(this.idCard)){
        this.$message({
          message:  '身份证格式错误，请检查',
          type: 'warning',
        })
      }else submitProfile({"TShirt":this.TShirt,
                          "sid":this.sid,
                          "xing":this.xing,
                          "ming":this.ming,
                          "firstName":this.firstName,
                          "lastName":this.lastName,
                          "college":this.college,
                          "email":this.email,
                          "tel":this.tel,
                          "introduction":this.introduction,
                          "provided":this.provided,
                          "sex":this.sex,
                          "idCard":this.idCard,
                          "grade":parseInt(this.grade),
                          "major":this.major}).then(res=>{
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
      })
    },
    isEmail(str){
      var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
      return reg.test(str);
    },
    isTel(str){
      return /^1[3456789]\d{9}$/.test(str);
    },
    isIdCard(str){
      return /^([1-6][1-9]|50)\d{8}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(str)||
          /^([1-6][1-9]|50)\d{4}\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}$/.test(str);
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

