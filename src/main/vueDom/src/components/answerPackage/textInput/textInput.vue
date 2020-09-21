<template>
  <div class="package-wer text-input-so">
    <answerTitleModel @playerEnded="playerEnded"></answerTitleModel>
    <template v-if="isStimulateOptions">
      <div class="button-webr-out" style="display: flex;align-items: center;justify-content: center;">
        <el-input v-model="value" placeholder="请输入内容"></el-input>
      </div>
      <div style="text-align: center;margin-top: 10px">
        <p v-if="item.stimulateType === 'TEXT'" style="text-align: center">{{item.trialName}}</p>
        <el-button type="primary" @click="selectEnd">确定</el-button>
      </div>
<!--      <div style="text-align: center" v-if="item.reactionPresentType !== 'WAITING_FOR_COMPLETION'">倒计时({{countdown}})</div>-->
    </template>
    <pup-ots-ut v-if="rely.experimentType === 'WITH_FEEDBACK' && isLogic" :answer-end-time.sync="answerEndTime" :answer="value" @nextAnts="pupAppearStartCount"></pup-ots-ut>
    <pupOutAppear v-if="appear" :item="item" ref="pupAppear" @endCountdown="updateAnswer" @betweenContentTime="setBetweenContent"></pupOutAppear>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped>

</style>
