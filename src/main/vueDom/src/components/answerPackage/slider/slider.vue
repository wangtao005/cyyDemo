<template>
  <div class="package-wer key-board">
    <answerTitleModel @playerEnded="playerEnded"></answerTitleModel>
    <template v-if="isStimulateOptions">
      <div class="button-webr-out" style="display: flex;align-items: center;justify-content: center;">
        <span style="margin-right: 6px">{{buttonContent.min}}</span>
        <el-slider
          style="width: 72%;display: inline-block;vertical-align: top;margin: 0 10px;"
          v-model="value"
          :min="+buttonContent.min"
          :max="+buttonContent.max"
          :show-tooltip="true"
        >
        </el-slider>
        <span>{{buttonContent.max}}</span>
      </div>
      <div style="text-align: center">
        <p v-if="item.stimulateType === 'TEXT'" style="text-align: center">{{item.trialName}}</p>
        <el-button type="primary" @click="selectEnd()">确定</el-button>
      </div>
<!--      <div style="text-align: center" v-if="item.reactionPresentType !== 'WAITING_FOR_COMPLETION'">倒计时({{countdown}})</div>-->
    </template>
    <pup-ots-ut v-if="rely.experimentType === 'WITH_FEEDBACK' && isLogic" :answer-end-time.sync="answerEndTime" :answer="value" @nextAnts="pupAppearStartCount"></pup-ots-ut>
    <pupOutAppear v-if="appear" :item="item" ref="pupAppear" @endCountdown="updateAnswer" @betweenContentTime="setBetweenContent"></pupOutAppear>
  </div>
</template>

<script>
  import script from './script.js'
  export default script;
</script>

<style scoped>

</style>
