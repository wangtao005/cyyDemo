<template>
  <div id="start-instruction">
    <el-form ref="homeForm" class="home-form" v-model="homeForm" label-width="140px">
<!--      <h2 class="home-form-title" style="text-align: center" v-if="experimentType === 'NO_FEEDBACK'">一分钟了解无反馈实验</h2>-->
<!--      <h2 class="home-form-title" style="text-align: center" v-else>一分钟了解带反馈实验</h2>-->
      <div style="height: 20px"></div>
      <el-form-item label="实验名称" class="aout">
        <div style="padding-right: 10px">
          <el-input v-model="homeForm.experimentName" :disabled="disabled" style="margin-right: 10px"></el-input>
        </div>
      </el-form-item>
<!--      <el-form-item label="屏幕选项">-->
<!--        <el-radio-group v-model="homeForm.whetherFullScreen" :disabled="disabled">-->
<!--          <el-radio label="FULL_SCREEN" border>全屏</el-radio>-->
<!--          <el-radio label="NOT_FULL_SCREEN" border>非全屏</el-radio>-->
<!--        </el-radio-group>-->
<!--      </el-form-item>-->
      <label style="margin: 0 0 10px 10px;display: block;">指导语(请在下方输入您的实验指导语，或上传视频或者音频)</label>
      <editor :default-content.sync="homeForm.instruction" :disabled="disabled" style="margin: 0 10px;" />
      <el-form-item label="指导语呈现时长" style="margin-top: 10px">
        <el-radio-group v-model="homeForm.instructionType" :disabled="disabled">
          <el-radio label="FIXED_DURATION" border>固定</el-radio>
          <el-radio label="WAITING_FOR_COMPLETION" border>等待被试看完</el-radio>
        </el-radio-group>
      </el-form-item>
      <div v-show="homeForm.instructionType === 'FIXED_DURATION'">
        <el-form-item label="时长(毫秒)" style="margin-top: 10px">
          <el-input v-model="homeForm.instructionDuration" type="number" :disabled="disabled"  placeholder="请输入" style="width: 50%" onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"></el-input>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>
