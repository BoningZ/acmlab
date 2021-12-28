<template>
  <Navi />
  <div class="app-container">
    <el-form :inline="true" :model="form" class="form-inline-query">
      <el-form-item label="赛季" >
        <el-input v-model="form.season" type="number" placeholder="例：2020" />
      </el-form-item>
      <el-form-item>
        <el-button class="commButton" size="mini" @click="doQuery(form.season)">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-show="access" class="commButton" size="mini" @click="doAdd(form.season)">录入新排位</el-button>
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
          <el-table  :data="props.row.ranks" border style="width: 100%;" size="mini">
            <el-table-column label="排名"  fixed="left" width="50"  align="center"  color="black">
              <template v-slot="scope">{{ scope.$index+1 }}</template>
            </el-table-column>
            <el-table-column label="队伍名"  align="center" color="black"  prop="name" />
            <el-table-column label="过题数"  align="center" color="black"  prop="score" />
            <el-table-column label="通过题目"  align="center" color="black"  prop="acs" />
            <el-table-column label="罚时"  align="center" color="black"  prop="penalty" />
          </el-table>
        </template>
      </el-table-column>

      <el-table-column label="序号"  fixed="left" width="50"  align="center"  color="black">
        <template v-slot="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>

      <el-table-column
          label="赛季"
          align="center"
          color="black"
          prop="season" />
      <el-table-column
          label="场次"
          align="center"
          color="black"
          sortable
          prop="countInSeason" />

      <el-table-column  width="250" label="操作" align="center" color="black">
        <template v-slot="scope" >
          <div><el-button v-show="access" type="warning" size="mini" @click="doEdit(scope.row.season,scope.row.countInSeason)">编辑</el-button></div>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>
<script>
import {getProfile, getRankingList} from '@/service/genServ.js'
import Navi from '@/components/Navi'
export default {
  name: 'RankingTableAdmin',
  components: {Navi},
  data() {
    return {
      access:false,
      form:{season:2020},
      dataList:[]
    }
  },
  created() {
    this.doQuery();
  },
  methods: {
    doQuery() {
      getProfile().then(res=>{this.access=res.data.access;});
      if(this.form.season)
      getRankingList({'season':parseInt(this.form.season)}).then((res) => {
        this.dataList = res.data;
      });
    },
    doEdit(season,countInSeason) {
      this.$router.push({ path: 'RankingEdit', query: { 'season':season,'countInSeason':countInSeason }})
    },
    doAdd(season) {
      if(this.form.season===null||this.form.season===""){alert("请输入要录入的赛季！");return;}
      this.$router.push({ path: 'RankingEdit',query:{'season':season}})
    },
  }
}
</script>

<style >
</style>
