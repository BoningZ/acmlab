<template>
  <Navi />
  <div class="app-container">
    <el-form :inline="true" :model="form" class="form-inline-query">
      <el-form-item label="赛季" >
        <el-input v-model="form.season" type="number" placeholder="例：2020" />
      </el-form-item>
      <el-form-item>
        <el-button class="commButton" size="mini" @click="doQuery()">查询</el-button>
      </el-form-item>
    </el-form>

    <el-button type="success" round @click="doSubmit()">提交</el-button>

    <el-table  class="table-content"
               :data="dataList"
               border
               style="width: 100%;"
               size="mini"
               v-loading="loading"
    >
      <el-table-column label="序号"  fixed="left" width="50"  align="center"  color="black">
        <template v-slot="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>

      <el-table-column  label="排在之前填报此志愿的队伍" align="center" color="black">
        <template v-slot="scope" >
          <p v-for="item in scope.row.front" v-bind:key="item">{{item}}</p>
        </template>
      </el-table-column>

      <el-table-column width="250" label="填报" align="center" color="black">
        <template v-slot="scope" >
          <input type="checkbox" :id="scope.row.rankInSeason" :value="scope.row.rankInSeason"   v-model="applications">
          <label :for="scope.row.rankInSeason">{{ scope.row.contestType+' '+scope.row.season+' '+scope.row.addr }}</label>
          <br>
          <label>{{'名额：'+scope.row.quota}}</label>
        </template>
      </el-table-column>

      <el-table-column width="250" label="注册截止时间" align="center" color="black" sortable="endReg">
        <template v-slot="scope" >
          <el-date-picker size="mini"  disabled v-model="scope.row.endReg" type="datetime" ></el-date-picker>
        </template>
      </el-table-column>

      <el-table-column  label="排在之后填报此志愿的队伍" align="center" color="black">
        <template v-slot="scope" >
          <p v-for="item in scope.row.back" v-bind:key="item">{{item}}</p>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>
<script>
import {getApplication, getSeasonContestList, submitApplication} from '@/service/genServ.js'
import Navi from '@/components/Navi'
export default {
  name: 'ApplicationEdit',
  components: {Navi},
  data() {
    return {
      loading:true,
      dataList:[],
      applications:[],
      id:null,
      form:{season:2020,},
    }
  },
  created() {
    this.id = this.$route.query.id;
    this.doQuery();
  },
  methods: {
    doQuery() {
      this.loading=true;
      getSeasonContestList({'id':parseInt(this.id),'season':parseInt(this.form.season)}).then((res) => {this.dataList = res.data;});
      getApplication({'id':parseInt(this.id),'season':parseInt(this.form.season)}).then((res) => {
        if(res.code==='0')this.applications = res.data;
        else{this.$message({message:res.msg,type:'warning',});this.$router.go(-1);}
      });
      this.loading=false;
    },
    doSubmit() {
      submitApplication({'applications':this.applications,'id':parseInt(this.id),'season':parseInt(this.form.season)}).then(res=>{
        if(res.code==='0')
          this.$message({message: '成功！',type: 'success',})
        else this.$message({message:res.msg,type:'warning',})
      })
    },
  }
}
</script>

<style >
</style>
