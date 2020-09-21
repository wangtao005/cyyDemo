<template>
  <div class="package-wer mouse-and-mouse-track">
    <answerTitleModel @playerEnded="playerEnded"></answerTitleModel>
    <div class="button-webr-out" style="display: flex;align-items: baseline;justify-content: center;min-height: 500px;margin-top: 0" ref="trackDom" @click="selectEnd();setAnswerData()">
      <div class="package-wer-img" style="height: calc(100vh - 350px);" v-if="item.stimulateType === 'IMG'" :class="[item.positionType, JSON.parse(item.stimulateContentUrl || '[]').length ? '' : 'only-img']">
        <img v-for="out in JSON.parse(item.stimulateContentUrl || '[]')" :style="{maxWidth: `${100 / JSON.parse(item.stimulateContentUrl || '[]').length}%`}" :src="`${urlRoot}${out.path}`">
      </div>
    </div>
    <div v-if="isStimulateOptions" style="text-align: center;margin-top: 20px">
      <p v-if="item.stimulateType === 'TEXT'" style="text-align: center">{{item.trialName}}</p>
      <el-button type="primary" @click="startMoving">开始</el-button>
<!--      <div style="text-align: center" v-if="item.reactionPresentType !== 'WAITING_FOR_COMPLETION'">倒计时({{countdown}})</div>-->
    </div>
    <pup-ots-ut v-if="rely.experimentType === 'WITH_FEEDBACK' && isLogic" :answer-end-time.sync="answerEndTime" :answer="answer" @nextAnts="pupAppearStartCount"></pup-ots-ut>
    <pupOutAppear v-if="appear" :item="item" ref="pupAppear" @betweenContentTime="setBetweenContent" @endCountdown="updateAnswer"></pupOutAppear>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped>

</style>
