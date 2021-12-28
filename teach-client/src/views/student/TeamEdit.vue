<template>
  <Navi/>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="20%" class="form-div">
      <el-form-item label="中文名">
        <el-input  v-model="form.chinese"  placeholder="例：肃正协议"  />
      </el-form-item>
      <el-form-item label="英文名">
        <el-input  v-model="form.english" placeholder="例：The Contingency"  />
      </el-form-item>
      <el-form-item label="队长">
        <el-select disabled v-model="form.captain" filterable placeholder="请选择队长">
          <el-option
              v-for="item in students"
              :key="item.xing+item.ming+'   '+item.lastName+' '+item.firstName"
              :label="item.xing+item.ming+'   '+item.lastName+' '+item.firstName"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="队员1">
        <el-select v-model="form.member1" filterable placeholder="请选择队员1">
          <el-option
              v-for="item in students"
              :key="item.xing+item.ming+'   '+item.lastName+' '+item.firstName"
              :label="item.xing+item.ming+'   '+item.lastName+' '+item.firstName"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="队员2">
        <el-select v-model="form.member2" filterable placeholder="请选择队员2">
          <el-option
              v-for="item in students"
              :key="item.xing+item.ming+'   '+item.lastName+' '+item.firstName"
              :label="item.xing+item.ming+'   '+item.lastName+' '+item.firstName"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>





      <el-form-item>
        <el-button type="primary" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script >
import {getTeamInfo,getStudentList,submitTeam, getProfile} from "@/service/genServ.js";

import Navi from '@/components/Navi'
export default {
  name: "ContestEdit",
  components: {Navi},
  data() {
    return {
      form: {
        id:null,
        chinese:"",
        english:"",
        captain:null,
        member1:null,
        member2:null,
        active:false,
      },
      students:[],
    };
  },
    created() {
    this.getStudents();
    this.form.id = this.$route.query.id;
    this.doQuery();
  },
  methods: {
    getStudents(){getStudentList().then(res=>{this.students=res.data});},
    doQuery() {
      getTeamInfo({ "id": this.form.id }).then(res => {
            if(res.code==='0') {
              this.form = res.data;
            }else{
              this.$message({
                message:res.msg,
                type:'warning'
              })
              getProfile().then(res=>{this.form.captain=res.data.id;});
            }
          }
      );
    },
    submit() {
      if(this.form.chinese&&this.form.english&&this.form.member1&&this.form.member2&&
          this.form.captain!==this.form.member1&&this.form.member1!==this.form.member2&&this.form.captain!==this.form.member2)
        submitTeam({"form": this.form}).then((res) => {
          if (res.code === '0') {
            this.$message({
              message: '提交成功！',
              type: 'success',
            })
            this.$router.go(-1);
          } else {
            this.$message({
              message:res.msg,
              type:'warning'
            })
          }
        });else this.$message({
        message:"所有项均为必填项且三名队员不能重复",
        type:'warning'
      });
    },
  },
};
</script>
<style>
.app-container{
  margin: 25px 50px 75px 0;
}
</style>
