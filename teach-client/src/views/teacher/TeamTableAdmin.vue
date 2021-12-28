<template>
  <Navi/>
  <div class="app-container">
    <el-form :inline="true" :model="form" class="form-inline-query">
      <el-form-item label="队伍名" >
        <el-input v-model="form.name" placeholder="中英文均可" />
      </el-form-item>
      <el-form-item label="年级" >
        <el-input v-model="form.grade" type="number" placeholder="包含某年级队员的队伍" />
      </el-form-item>

      <el-form-item>
        <el-button class="commButton" size="mini" @click="doQuery()">查询</el-button>
      </el-form-item>
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



    </el-table>
  </div>
</template>
<script>
import {getTeamList} from '@/service/genServ.js'
import Navi from '@/components/Navi'
export default {
  name: 'TeamTableAdmin',
  components: {Navi},
  data() {
    return {
      form: {
        name:null,
        grade:null,
      },
      dataList:[]
    }
  },
  created() {
    this.doQuery();
  },
  methods: {
    doQuery() {
      if(this.form.name==="")this.form.name=null;
      getTeamList({'grade':parseInt(this.form.grade), 'name':this.form.name}).then((res) => {
        this.dataList = res.data;
        for(let j=0;j<this.dataList.length;j++)
          this.dataList[j].member=[this.dataList[j].captain,this.dataList[j].member1,this.dataList[j].member2];
      });
    },
  }
}
</script>

<style >
</style>
