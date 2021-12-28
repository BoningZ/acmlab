<template>
  <Navi/>
  <div class="app-container">
    <el-button class="commButton" size="mini" @click="doAdd()">创建队伍</el-button>
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

      <el-table-column label="序号"  fixed="left" width="50"  align="center"  color="black">
        <template v-slot="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>

      <el-table-column
          label="中文名"
          align="center"
          color="black"
          sortable
          prop="chinese" />
      <el-table-column
          label="英文名"
          align="center"
          color="black"
          sortable
          prop="english" />


      <el-table-column  width="250" label="参与本赛季排位" align="center" color="black">
        <template v-slot="scope" >
          <div @click="beforeChange(scope.row.id)">
          <el-switch
              v-model="scope.row.active"
              :loading="this.loading"
              active-color="#13ce66"
              inactive-color="#ff4949"
              inline-prompt
              active-text="参与"
              inactive-text="不参与">
          </el-switch></div>
        </template>
      </el-table-column>

      <el-table-column width="300" label="操作" align="center" color="black">
        <template v-slot="scope" >
          <el-form :inline="true">
            <el-form-item><el-button type="warning" size="mini" @click="doEdit(scope.row.id)" >编辑</el-button></el-form-item>
            <el-form-item><el-button type="primary" plain size="mini" @click="doApply(scope.row.id)" >志愿填报</el-button></el-form-item>
          </el-form>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>
<script>
import {getAccess, getTeamList} from '@/service/genServ.js'
import Navi from '@/components/Navi'
import {ElMessage} from "element-plus";
export default {
  name: 'TeamTable',
  components: {Navi},
  data() {
    return {
      dataList:[],
      loading:false
    }
  },
  created() {
    this.doQuery();
  },
  methods: {
    doQuery() {
      getTeamList().then((res) => {
        this.dataList = res.data;
        for(let j=0;j<this.dataList.length;j++)
          this.dataList[j].member=[this.dataList[j].captain,this.dataList[j].member1,this.dataList[j].member2];
      });
    },
    beforeChange(id){
      if(this.loading||id===null||id===undefined)return;
      this.loading = true;
      getAccess({'id':parseInt(id)}).then(res=>{
        if(res.code==="0"){ElMessage.success("成功！");this.loading=false;}
        else{ElMessage.error(res.msg);this.loading=false;this.doQuery();}
      })
    },
    doEdit(id) {
      this.$router.push({ path: 'TeamEdit', query: { 'id':id }})
    },
    doAdd() {
      this.$router.push({ path: 'TeamEdit'})
    },
    doApply(id){
      this.$router.push({path:'ApplicationEdit',query:{'id':id}})
    }
  }
}
</script>

<style >
</style>
