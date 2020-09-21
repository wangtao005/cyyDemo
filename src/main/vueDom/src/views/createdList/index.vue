<template>
  <div id="download-list">
    <el-tabs type="border-card" v-model="feedbackType" @tab-click="switchUts" style="height: 100%">
      <el-tab-pane label="无反馈实验" name="noFeedback">
        <noFeedbackList @examine-feedback="examineFeedback" @edit-feedback="editFeedback"></noFeedbackList>
      </el-tab-pane>
      <el-tab-pane label="有反馈实验" name="withFeedback">
        <withFeedbackList @examine-feedback="examineFeedbackWith" @edit-feedback="editFeedbackWith"></withFeedbackList>
      </el-tab-pane>
    </el-tabs>
    <router-view></router-view>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>
