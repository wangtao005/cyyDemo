<template>
  <div id="end-instruction">
    <div class="home-table-webkit" style="margin-top: 10px">
      <h2 class="home-form-title" style="text-align: left">结束语</h2>
      <editor :default-content.sync="homeForm.conclusion" :disabled="disabled" />
      <div style="margin-top: 10px">
        结束语呈现时长
        <el-radio-group v-model="homeForm.conclusionType" :disabled="disabled">
          <el-radio label="FIXED_DURATION" border>固定</el-radio>
          <el-radio label="WAITING_FOR_COMPLETION" border>等待被用户看完</el-radio>
        </el-radio-group>
      </div>
      <div v-show="homeForm.conclusionType === 'FIXED_DURATION'" style="margin-top: 10px">
        时长(毫秒)
        <el-input v-model="homeForm.conclusionDuration" type="number" :disabled="disabled" placeholder="请输入" style="width: 50%" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"></el-input>
      </div>
    </div>
  </div>
</template>

<script>
  import script from './endInstruction.js'
  export default script
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>
