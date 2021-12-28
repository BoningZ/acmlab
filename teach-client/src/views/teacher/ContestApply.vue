<template>
  <Navi/>
  <div class="app-container">
    <el-form :inline="true">
      <el-form-item><el-button type="primary" round @click="doCheck()">选中前{{this.quota}}名</el-button></el-form-item>
      <el-form-item><el-button type="success" round @click="doSubmit()">报名</el-button></el-form-item>
    </el-form>
    <el-table  class="table-content"
               :data="dataList"
               border
               style="width: 100%;"
               size="mini"
    >
      <el-table-column type="expand">
        <template #default="props">
          <el-table  :data="props.row.member" border style="width: 100%;" size="mini">
            <el-table-column width="60" label="中文姓"  align="center" color="black"  prop="xing" />
            <el-table-column width="60" label="中文名"  align="center" color="black"  prop="ming" />
            <el-table-column width="60" label="英文姓"  align="center" color="black"  prop="lastName" />
            <el-table-column width="90" label="英文名"  align="center" color="black"  prop="firstName" />
            <el-table-column label="邮箱"  align="center" color="black"  prop="email" />
            <el-table-column width="100" label="手机"  align="center" color="black"  prop="tel" />
            <el-table-column label="学院"  align="center" color="black"  prop="college" />
            <el-table-column label="专业"  align="center" color="black"  prop="major" />
            <el-table-column width="105" label="学号"  align="center" color="black"  prop="sid" />
            <el-table-column width="50" label="年级"  align="center" color="black"  prop="grade" />
            <el-table-column width="150" label="身份证号"  align="center" color="black"  prop="idCard" />
            <el-table-column width="60" label="T恤"  align="center" color="black"  prop="TShirt" />
            <el-table-column width="70" label="对外提供"  align="center" color="black"  prop="provided" />
            <el-table-column width="70" label="性别为男"  align="center" color="black"  prop="sex" />
          </el-table>
        </template>
      </el-table-column>

      <el-table-column width="250" label="报名" align="center" color="black">
        <template v-slot="scope" >
          <input type="checkbox" :value="scope.row.id"   v-model="checked">
        </template>
      </el-table-column>

      <el-table-column label="排名"  fixed="left" width="50"  align="center"  color="black">
        <template v-slot="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>

      <el-table-column
          label="中文名"
          align="center"
          color="black"
          prop="chinese" />
      <el-table-column
          label="英文名"
          align="center"
          color="black"
          prop="english" />



    </el-table>
  </div>
</template>
<script>
import {createProcess, getAppliedTeamList, getContestInfo} from '@/service/genServ.js'
import { ElMessage } from 'element-plus'
import Navi from '@/components/Navi'
export default {
  name: 'RankingTable',
  components: {Navi},
  data() {
    return {
      id:null,
      season:null,
      countInSeason:null,
      quota:null,
      dataList:[],
      checked:[],
    }
  },
  created() {
    this.id=this.$route.query.id;
    this.doGetInfo();
  },
  methods: {
    doGetInfo(){
      getContestInfo({'id':parseInt(this.id)}).then(res=>{
        this.season=res.data.season; this.countInSeason=res.data.rankInSeason; this.quota=res.data.quota;
        getAppliedTeamList({'season':parseInt(this.season),'countInSeason':parseInt(this.countInSeason)}).then((res) => {
          console.log(this.countInSeason)
          this.dataList = res.data;
          for(let j=0;j<this.dataList.length;j++)
            this.dataList[j].member=[this.dataList[j].captain,this.dataList[j].member1,this.dataList[j].member2];
        });
      })
    },
    doSubmit(){
      if(this.checked.length>this.quota){ElMessage.error("最多可选择"+this.quota+"支队伍参赛！");return;}
      createProcess({"checked":this.checked,"id":parseInt(this.id)}).then(res=>{
        if(res.code==="0"){ElMessage.success("报名成功！");this.$router.go(-1);}
        else ElMessage.error(res.msg);
      })

    },
    doCheck(){
      for(let i=0;i<this.quota;i++)
        this.checked[i]=this.dataList[i].id;
    }
  }
}
</script>

<style >
</style>
