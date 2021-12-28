<template>
  <Navi/>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="20%" class="form-div">
      <el-form-item label="赛季">
        <el-input disabled v-model="form.season"  placeholder="例：2020"  />
      </el-form-item>
      <el-form-item label="场次">
        <el-input  disabled v-model="form.countInSeason" placeholder="例：1"  />
      </el-form-item>

      <el-form-item label="详细信息">
          <el-table  :data="form.ranks" border style="width: 100%;" size="mini">
            <el-table-column label="队伍名"  align="center" color="black"  prop="name" />
            <el-table-column width="100" label="罚时（分钟）"><template v-slot="scope" ><el-input  v-model="scope.row.penalty" size="mini" type="number" placeholder="例：1"  /></template></el-table-column>
            <el-table-column width="50" label="A" ><template v-slot="scope" ><input type="checkbox"  value="A" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="B" ><template v-slot="scope" ><input type="checkbox"  value="B" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="C" ><template v-slot="scope" ><input type="checkbox"  value="C" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="D" ><template v-slot="scope" ><input type="checkbox"  value="D" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="E" ><template v-slot="scope" ><input type="checkbox"  value="E" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="F" ><template v-slot="scope" ><input type="checkbox"  value="F" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="G" ><template v-slot="scope" ><input type="checkbox"  value="G" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="H" ><template v-slot="scope" ><input type="checkbox"  value="H" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="I" ><template v-slot="scope" ><input type="checkbox"  value="I" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="J" ><template v-slot="scope" ><input type="checkbox"  value="J" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="K" ><template v-slot="scope" ><input type="checkbox"  value="K" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="L" ><template v-slot="scope" ><input type="checkbox"  value="L" v-model="scope.row.acs"></template></el-table-column>
            <el-table-column width="50" label="M" ><template v-slot="scope" ><input type="checkbox"  value="M" v-model="scope.row.acs"></template></el-table-column>
          </el-table>
      </el-form-item>



      <el-form-item>
        <el-button type="primary" @click="submit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {getRankingInfo,submitRanking} from "@/service/genServ.js";
import Navi from '@/components/Navi'
export default {
  name: "RankingEdit",
  components: {Navi},
  data() {
    return {
      form: {
        season:null,
        countInSeason:null,
        ranks:[]
      }
    };
  },
  created() {
    this.form.season = this.$route.query.season;
    this.form.countInSeason=this.$route.query.countInSeason;
    this.doQuery();
  },
  methods: {
    doQuery() {
      getRankingInfo({ "season":this.form.season,"countInSeason":this.form.countInSeason }).then(res => {
            if(res.code==='0') {
              this.form = res.data;
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
      if(this.form.season&&this.form.countInSeason)
        submitRanking({"form": this.form}).then((res) => {
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
        message:"赛季与场次为必填",
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
