<template>
  <Navi/>
  <div class="app-container">
    <el-form :inline="true" :model="form" class="form-inline-query">
      <el-form-item label="赛季" >
        <el-input v-model="form.season" type="number" placeholder="例：2020" />
      </el-form-item>
      <el-form-item label="比赛类型">
        <el-select clearable v-model="form.type" placeholder="请选择比赛类型">
          <el-option
              v-for="item in types"
              :key="item.name"
              :label="item.name"
              :value="item.name"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button class="commButton" size="mini" @click="doQuery()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button class="commButton" size="mini" @click="doAdd()">添加</el-button>
      </el-form-item>
    </el-form>
    <el-table  class="table-content"
               :data="dataList"
               border
               style="width: 100%;"
               size="mini"
    >
      <el-table-column label="序号"  fixed="left" width="50"  align="center"  color="black">
        <template v-slot="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>

      <el-table-column
          width="70"
          label="赛季"
          align="center"
          color="black"
          sortable
          prop="season" />
      <el-table-column
          width="100"
          label="比赛类型"
          align="center"
          color="black"
          sortable
          prop="contestType" />
      <el-table-column
          width="250"
          label="地址"
          align="center"
          color="black"
          sortable
          prop="addr"/>

      <el-table-column width="250" label="举办时间" align="center" color="black" sortable>
        <template v-slot="scope" >
          <el-date-picker size="mini"  disabled v-model="scope.row.contestDate" type="date" ></el-date-picker>
        </template>
      </el-table-column>

      <el-table-column  label="注册时间" align="center" color="black" sortable>
        <template v-slot="scope" >
          <el-date-picker
              disabled
              size="mini"
              v-model="scope.row.reg"
              type="datetimerange"
              range-separator="至">
          </el-date-picker>
        </template>
      </el-table-column>

      <el-table-column width="250" label="操作" align="center" color="black">
        <template v-slot="scope" >
          <div>
            <el-button type="warning" size="mini" @click="doEdit(scope.row.id)" >编辑</el-button>
            <el-button v-show="!scope.row.registered" type="primary" size="mini" @click="doInput(scope.row.id)">报名</el-button>
          </div>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>
<script>
import {getContestList,getContestTypes} from '@/service/genServ.js'
import Navi from '@/components/Navi'
export default {
  name: 'ContestTable',
  components: {Navi
  },
  data() {
    return {
      form: {
        season:null,
        type:null,
      },
      dataList:[],
      types:[]
    }
  },
  created() {
    this.getTypes();
    this.doQuery();
  },
  methods: {
    getTypes(){getContestTypes().then(res=>{this.types=res.data});},
    doQuery() {
      if(this.form.season==="")this.form.season=null; if(this.form.type==="")this.form.type=null;
      getContestList({'season':parseInt(this.form.season), 'type':this.form.type}).then((res) => {
        this.dataList = res.data;
        for(let j=0;j<this.dataList.length;j++)this.dataList[j].reg=[this.dataList[j].startReg,this.dataList[j].endReg];
      });
    },
    doEdit(id) {
      this.$router.push({ path: 'ContestEdit', query:{'id':id}})
    },
    doInput(id){
      this.$router.push({path:'ContestApply',query: { 'id':id}})
    },
    doAdd() {
      this.$router.push({ path: 'ContestEdit'})
    },
  }
}
</script>

<style >
</style>
