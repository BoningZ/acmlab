<template>
  <Navi/>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="30%" class="form-div">
      <el-form-item label="比赛类型">
        <el-select v-model="form.contestType" placeholder="请选择比赛类型">
          <el-option
              v-for="item in types"
              :key="item.name"
              :label="item.name"
              :value="item.name"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="赛季">
        <el-input  v-model="form.season" type="number" placeholder="例：2020"  />
      </el-form-item>
      <el-form-item label="地点">
        <el-input  v-model="form.addr" placeholder="例：济南-齐鲁工业大学"  />
      </el-form-item>
      <el-form-item label="名额">
        <el-input  v-model="form.quota"  type="number" placeholder="例：2"  />
      </el-form-item>
      <el-form-item label="QQ群">
        <el-input  v-model="form.qq" placeholder="请输入比赛qq群"  />
      </el-form-item>
      <el-form-item label="特殊说明">
        <el-input type="textarea" :rows="5" maxlength="120" show-word-limit v-model="form.demand" placeholder="请输入特殊说明"  />
      </el-form-item>
      <el-form-item label="比赛日期">
        <el-date-picker  v-model="form.contestDate" type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="注册时间">
        <el-date-picker
            v-model="form.reg"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {getContestInfo, submitContest, getContestTypes} from "@/service/genServ.js";
import Navi from '@/components/Navi'
export default {
  name: "ContestEdit",
  components: {Navi},
  data() {
    return {
      form: {
        id:null,
        demand:"",
        qq:"",
        quota:null,
        type:"ICPC",
        addr:"",
        season:null,
        contestDate:null,
        startReg:null,
        endReg:null,
        reg:null,
      },
      types:[],
    };
  },
  created() {
    this.getTypes();
    this.form.id = this.$route.query.id;
    this.doQuery();
  },
  methods: {
    getTypes(){getContestTypes().then(res=>{this.types=res.data});},
    doQuery() {
      getContestInfo({ "id": this.form.id }).then(
          (res) => {
            if(res.code==='0') {
              this.form = res.data;
              this.form.reg=[this.form.startReg,this.form.endReg];
            }else{
              this.$message({
                message:res.msg,
                type:'warning'
              })
            }
          }
      );
    },
    submit() {
      this.form.contestDate=new Date(this.form.contestDate).toJSON();
      this.form.startReg=new Date(this.form.reg[0]).toJSON(); this.form.endReg=new Date(this.form.reg[1]).toJSON();
      this.form.quota=parseInt(this.form.quota); this.form.season=parseInt(this.form.season);
      if(this.form.quota&&this.form.season&&this.form.contestDate&&this.form.addr)
        submitContest({"form": this.form}).then((res) => {
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
        message:"名额、赛季、时间为必填项！",
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
