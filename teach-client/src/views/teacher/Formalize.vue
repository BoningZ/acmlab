<template>
  <Navi/>
  <div class="app-container">
    <el-form :inline="true" :model="form" class="form-inline-query">
      <el-form-item label="学号">
        <el-input v-model="sid" placeholder="请输入学号"></el-input>
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="grade" placeholder="请输入年级" type="number"></el-input>
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
      <el-table-column
          label="序号"
          fixed="left"
          width="50"
          align="center"
          color="black"
      >
        <template v-slot="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column><!--第一列-->
      <el-table-column
          label="学号"
          align="center"
          color="black"
          prop="sid" /><!--courseNum-->
      <el-table-column
          label="姓名"
          align="center"
          color="black"
          prop="name" /><!--courseName-->

      <el-table-column label="个人介绍" align="center" color="black">
        <template v-slot="scope">
          <el-popover
              placement="left"
              width="200"
              trigger="click"
              :content=scope.row.introduction>
            <template v-slot:reference>
              <el-button >查看个人介绍</el-button>
            </template>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column
          sortable
          label="得分"
          align="center"
          color="black"
          prop="mark" />

      <el-table-column label="评分细节" align="center" color="black">
        <template v-slot="scope">
          <el-popover
              placement="left"
              width="200"
              trigger="click">
            <el-table :data="scope.row.detail">
              <el-table-column width="100" property="name" label="评分人" align="center"></el-table-column>
              <el-table-column width="200" sortable label="评分" prop="mark" align="center">
                <template v-slot="scope2">
                  <el-rate disabled v-model="scope2.row.mark"></el-rate>
                </template>
              </el-table-column>
              <el-table-column width="200" property="remark" label="评语" align="center"></el-table-column>
            </el-table>
            <template v-slot:reference>
              <el-button >查看评分细节</el-button>
            </template>
          </el-popover>
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center" >
        <template v-slot="scope">
         <el-button type="success" @click="doFormalize(scope.row.iid)">转正</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import {getInterviewees,formalize} from '@/service/genServ.js'
import Navi from '@/components/Navi'
export default {
  name: 'Formalize',
  components: {Navi},
  data() {
    return {
      sid:null,
      grade:null,
      dataList:[]
    }
  },
  created() {
    this.doQuery();
  },
  methods: {
    doQuery() {
      if(this.sid==="")this.sid=null;
      getInterviewees({'sid':this.sid,'grade':parseInt(this.grade)}).then(res => {
        if(res.code==='0'){this.dataList = res.data}else{
          this.$message({
            message:res.msg,
            type:'warning',
          })
        }
      })
    },
    doFormalize(iid){
      formalize({'iid':iid}).then(res=>{
        if(res.code==='0'){
          this.$message({
            message: '成功！',
            type: 'success',
          })
        }else{
          this.$message({
            message:res.msg,
            type:'warning',
          })
        }
      });
      this.doQuery();
    },
  }
}
</script>

<style scoped>
</style>
