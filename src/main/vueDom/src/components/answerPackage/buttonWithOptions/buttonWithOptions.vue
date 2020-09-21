<template>
  <div class="package-wer button-with-options">
    <answerTitleModel @playerEnded="playerEnded"></answerTitleModel>
    <template v-if="isStimulateOptions">
      <div class="button-webr-out" v-if="buttonContent.length">
        <template v-for="(item, index) in buttonContent">
          <el-button type="primary" @click="selectEnd();setAnswerData(index)">{{item.name}}</el-button>
        </template>
      </div>
      <p v-if="item.stimulateType === 'TEXT'" style="text-align: center">{{item.trialName}}</p>
<!--      <div style="text-align: center" v-if="item.reactionPresentType !== 'WAITING_FOR_COMPLETION'">倒计时({{countdown}})</div>-->
    </template>
    <pup-ots-ut v-if="rely.experimentType === 'WITH_FEEDBACK' && isLogic" :answer-end-time.sync="answerEndTime" :answer="answer" @nextAnts="pupAppearStartCount"></pup-ots-ut>
    <pupOutAppear v-if="appear" :item="item" ref="pupAppear" @endCountdown="updateAnswer" @betweenContentTime="setBetweenContent"></pupOutAppear>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped>

</style>
