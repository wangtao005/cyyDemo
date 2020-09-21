<template>
  <div class="package-wer key-board">
    <answerTitleModel @playerEnded="playerEnded"></answerTitleModel>
    <div class="button-webr-out" v-if="buttonContent.length && isStimulateOptions">
      <template v-for="(item, index) in buttonContent">
        <div class="fan-but" :ref="`${item.keyUp}Key`" :data-id="index">{{item.keyUp}}:{{item.name}}</div>
      </template>
<!--      <div style="text-align: center" v-if="item.reactionPresentType !== 'WAITING_FOR_COMPLETION'">倒计时({{countdown}})</div>-->
      <p v-if="item.stimulateType === 'TEXT'" style="text-align: center">{{item.trialName}}</p>
    </div>
    <pup-ots-ut v-if="rely.experimentType === 'WITH_FEEDBACK' && isLogic" :answer-end-time.sync="answerEndTime" :answer="answer" @nextAnts="pupAppearStartCount"></pup-ots-ut>
    <pupOutAppear v-if="appear" :item="item" ref="pupAppear" @endCountdown="updateAnswer" @betweenContentTime="setBetweenContent"></pupOutAppear>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
.fan-but{
  display: inline-block;
  line-height: 1;
  white-space: nowrap;
  background: #fff;
  border: 1px solid #dcdfe6;
  color: #606266;
  -webkit-appearance: none;
  text-align: center;
  box-sizing: border-box;
  outline: none;
  margin: 0;
  transition: .1s;
  font-weight: 500;
  -moz-user-select: none;
  -webkit-user-select: none;
  -ms-user-select: none;
  padding: 12px 20px;
  font-size: 14px;
  border-radius: 4px;
  &.hover-but-clst{
    background-color: red;
    color: #FFF;
  }
}
</style>
