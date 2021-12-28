<template>
  <Navi />
  <el-card v-for="m in this.dataList" v-bind:key="m" class="box-card">
    <template #header>
      <div class="card-header">
        <span>{{m.contest}}</span>
        <el-button disabled class="button"  type="info">{{m.tName}}</el-button>
      </div>
    </template>
    <el-steps :active="m.active" align-center class="steps">
      <el-step title="教练注册" icon="el-icon-key"></el-step>
      <el-step title="缴费" icon="el-icon-s-finance"></el-step>
      <el-step title="特殊要求" icon="el-icon-warning-outline"></el-step>
      <el-step title="交通 酒店" icon="el-icon-s-ticket"></el-step>
      <el-step title="获奖" icon="el-icon-trophy"></el-step>
      <el-step title="感想与照片 证书 发票" icon="el-icon-document-add"></el-step>
      <el-step title="等待报销" icon="el-icon-s-check"></el-step>
      <el-step title="完成" icon="el-icon-finished"></el-step>
    </el-steps>
    <el-form :inline="true" class="form-inline-query">
      <el-form-item label="比赛日期"><el-date-picker size="mini"  disabled v-model="m.contestDate" type="date" ></el-date-picker></el-form-item>
      <el-form-item label="比赛QQ群"> <el-input size="mini" disabled v-model="m.qq"></el-input></el-form-item>
    </el-form>

    <el-button type="success" :disabled="access" v-show="m.active===0" align-center @click="doReg(m.id)">注册完毕</el-button>

    <div v-show="m.active>=1&&m.active<=3&&access" style="margin-bottom: 15px;"><el-descriptions  class="margin-top" title="教练及报销信息" :column="3"  border>
      <el-descriptions-item><template #label><i class="el-icon-user"></i>教练姓名</template>鹿旭东</el-descriptions-item>
      <el-descriptions-item><template #label><i class="el-icon-message"></i>邮箱</template>dongxul@sdu.edu.cn</el-descriptions-item>
      <el-descriptions-item><template #label><i class="el-icon-s-custom"></i>T恤尺码</template>XXL</el-descriptions-item>
      <el-descriptions-item><template #label><i class="el-icon-school"></i>户名</template>山东大学</el-descriptions-item>
      <el-descriptions-item><template #label><i class="el-icon-info"></i>纳税人识别号</template>12100000495570303U</el-descriptions-item>
      <el-descriptions-item><template #label><i class="el-icon-location"></i>地址</template>山东省济南市历城区山大南路27号</el-descriptions-item>
      <el-descriptions-item><template #label><i class="el-icon-phone"></i>电话 </template>0531-88364534</el-descriptions-item>
      <el-descriptions-item><template #label><i class="el-icon-coin"></i>开户银行 </template>中国银行济南历城支行</el-descriptions-item>
      <el-descriptions-item><template #label><i class="el-icon-bank-card"></i>账号</template>244206255768</el-descriptions-item>
    </el-descriptions></div>

    <el-button type="success" :disabled="!access" v-show="m.active===1" align-center @click="doPay(m.id)">缴费完毕</el-button>

    <p v-show="m.active===2"><el-date-picker  size="mini"  disabled v-model="m.endReg" type="datetime" ></el-date-picker></p>
    <el-result v-show="m.active===2" align-center icon="warning" title="请在以上时间之前完成以下要求" :sub-title="m.demand">
      <template #extra><el-button :disabled="!access" type="success" size="medium" @click="doDemand(m.id)">已完成</el-button></template>
    </el-result>

    <el-form :inline="true" v-show="m.active>=3&&m.active<=4" class="form-inline-query">
      <el-form-item label="往返交通"><el-input size="mini"  :disabled="!access||m.active===4" v-model="m.transport" placeholder="例：G1 G2" ></el-input></el-form-item>
      <el-form-item label="酒店地址"> <el-input size="mini" :disabled="!access||m.active===4" v-model="m.hotel" placeholder="例：阿联酋迪拜行政区迪拜 Jumeirah Beach Rd"></el-input></el-form-item>
      <el-form-item v-show="m.active===3"><el-button type="primary" :disabled="!access" size="mini" @click="doHotelAndTrans(m.id,m.transport,m.hotel)">提交</el-button></el-form-item>
    </el-form>

    <el-form :inline="true" v-show="m.active>=4" class="form-inline-query">
      <el-form-item label="所获奖项">
        <el-select :disabled="!access||m.active>=5" clearable v-model="m.award" placeholder="请选择所获奖项">
          <el-option
              v-for="item in awards"
              :key="item.name"
              :label="item.name"
              :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item v-show="m.active===4"><el-button type="primary" :disabled="!access" size="mini" @click="doAward(m.id,m.award)">提交</el-button></el-form-item>
    </el-form>

    <el-form :inline="true" v-show="m.active===5" class="form-inline-query">
      <el-form-item label="感想与照片">
      <el-upload :disabled="!access"
                 class="upload-demo"
                 drag
                 action="/api/process/submitF">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将压缩包拖到此处，或<em>点击上传</em></div>
      </el-upload>

    </el-form-item>

      <el-form-item  label="证书">
        <el-upload :disabled="!access"
            class="upload-demo"
            drag
            action="/api/process/submitC">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将压缩包拖到此处，或<em>点击上传</em></div>
        </el-upload>
      </el-form-item>

      <el-form-item label="发票">
        <el-upload :disabled="!access"
            class="upload-demo"
            drag
            action="/api/process/submitR">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将压缩包拖到此处，或<em>点击上传</em></div>
        </el-upload>
      </el-form-item>

      <el-form-item v-show="m.active===5"><el-button type="primary" :disabled="!access"  @click="doFCR(m.id)">提交</el-button></el-form-item>
    </el-form>

    <el-form :inline="true" v-show="m.active>=6" class="form-inline-query">
      <el-form-item ><el-button type="primary"  size="mini" @click="doDownload(m.feelings)">感想下载</el-button></el-form-item>
      <el-form-item ><el-button type="primary"  size="mini" @click="doDownload(m.certificate)">证书下载</el-button></el-form-item>
      <el-form-item ><el-button type="primary"  size="mini" @click="doDownload(m.receipt)">发票下载</el-button></el-form-item>
    </el-form>
    <el-button type="success"  v-show="m.active===6&&!access" align-center @click="doReimburse(m.id)">报销完毕</el-button>

  </el-card>

</template>

<script >
import Navi from "@/components/Navi";
import {getProcessList, getProfile, pushProcess,refreshHT,refreshA,refreshFCR} from "@/service/genServ";
import {ElMessage} from "element-plus";

export default {
  name: "ProcessTrace",
  components: {Navi},
  data() {
    return {
      access:true,
      dataList:[],
      awards:[{name:'金',value:1},{name:'银',value:2},{name:'铜',value:3},{name:'铁',value:4}],
    }
  },
  created() {
    this.doQuery();
  },
  methods: {
    doQuery(){
      getProcessList().then(res=>{this.dataList=res.data;});
      getProfile().then(res=>{this.access=!res.data.access;});
    },
    doReg(id){
      pushProcess({'id':id,'op':'register'}).then(res=>{
        if(res.code==="0"){ElMessage.success("成功！");}
        else ElMessage.error(res.msg);
        getProcessList().then(res=>{this.dataList=res.data;});});
    },
    doDemand(id){
      pushProcess({'id':id,'op':'demand'}).then(res=>{
        if(res.code==="0"){ElMessage.success("成功！");}
        else ElMessage.error(res.msg);
        getProcessList().then(res=>{this.dataList=res.data;});});
    },
    doPay(id){
      pushProcess({'id':id,'op':'pay'}).then(res=>{
        if(res.code==="0"){ElMessage.success("成功！");}
        else ElMessage.error(res.msg);
        getProcessList().then(res=>{this.dataList=res.data;});});
    },
    doReimburse(id){
      pushProcess({'id':id,'op':'reimburse'}).then(res=>{
        if(res.code==="0"){ElMessage.success("成功！");}
        else ElMessage.error(res.msg);
        getProcessList().then(res=>{this.dataList=res.data;});});
    },
    doHotelAndTrans(id,trans,hotel){
      refreshHT({'id':id,'hotel':hotel,'trans':trans}).then(res=>{
        if(res.code==="0"){ElMessage.success("成功！");}
        else ElMessage.error(res.msg);
        getProcessList().then(res=>{this.dataList=res.data;});});
    },
    doAward(id,award){
      refreshA({'id':id,'award':award}).then(res=>{
        if(res.code==="0"){ElMessage.success("成功！");}
        else ElMessage.error(res.msg);
        getProcessList().then(res=>{this.dataList=res.data;});});
    },
    doFCR(id){
      refreshFCR({'id':id}).then(res=>{
        if(res.code==="0"){ElMessage.success("成功！");}
        else ElMessage.error(res.msg);
        getProcessList().then(res=>{this.dataList=res.data;});});
    },
    doDownload(url){window.open(url);},


  }
}
</script>

<style>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.box-card {
  margin-top: 13px;
  margin-left: auto;
  margin-right: auto;
  width: 90%;
}
.steps{
  margin-top: 10px;
  margin-bottom: 30px;
}
</style>