<template>
  <div id="answer" :style="{backgroundColor: screenColor, fontSize: `${fontSize}px`, color: `${fontColor}!important`}">
<!--    <div class="cot-all-wer" :class="whetherFullScreen === 'FULL_SCREEN' ? 'full-screen-wer' : 'not-full-screen-wer'">-->
    <div class="cot-all-wer" :class="whetherFullScreen === 'FULL_SCREEN' ? 'full-screen-wer' : 'full-screen-wer'">
<!--      <el-button type="primary" style="position: absolute;right: 40px;" v-if="!isFullscreen" @click="enterFullScreen($event)" ref="oktClick">全屏</el-button>-->
      <el-button type="primary" style="position: absolute;right: 40px;" v-show="isFullscreen" @click="cancelFullScreen($event)" ref="notClick">取消全屏</el-button>

      <allTimeStatistics style="display: none" ref="allTimeSta"></allTimeStatistics>
      <el-progress style="width: 30%;margin: 0 auto;" v-if="yesOrNoViewProgressBar === 'YES'" :text-inside="true" :stroke-width="24" :stroke-linecap="'butt'" :percentage="percentage" status="success"></el-progress>
      <div class="answer-article" :class="whetherFullScreen === 'FULL_SCREEN' ? 'full-screen' : 'not-full-screen'">
        <div v-if="pageStatus === 'isFullScreen'" style="overflow: hidden">
          <h2 style="text-align: center;margin-top: 70px">请选择是否全屏</h2>
          <div style="text-align: center">
            <el-button type="primary" @click="enterFullScreen($event)" ref="oktClick">进入全屏</el-button>
            <el-button type="primary" @click="enterGuide">不进入全屏</el-button>
          </div>
        </div>
        <template v-if="pageStatus === 'instruction'">
          <!-- 指导语 -->
          <instruction :instruction="instructionData.instruction" :experiment-name="instructionData.experimentName"></instruction>
          <!-- END指导语 -->
          <!-- 用户信息收集 -->
<!--          <div class="user-information-collection">-->

<!--          </div>-->
          <!-- END用户信息收集 -->
          <div class="button-webr" style="text-align: center">
            <el-button type="primary" @click.native="switchTypeNext">我已阅读完毕并准备开始试验<span v-if="instructionDuration">({{instructionDuration}})</span></el-button>
          </div>
        </template>

        <!-- 用户信息收集 -->
        <userInformationCollection v-if="pageStatus === 'userInfo'" :participant-basic-info="participantBasicInfo" :font-size="+fontSize" @endFillIn="endFillIn"></userInformationCollection>
        <!-- END用户信息收集 -->

        <!-- 答题组件 -->
        <template v-if="pageStatus === 'answer'">
          <answerPackage style="height: calc(100vh - 184px);display: flex;align-items: center;" :feedback-experiment-stimulate="feedbackExperimentStimulate" @endOfAnswer="endOfAnswer" @answerIndex="answerIndex"></answerPackage>
        </template>
        <!-- END答题组件 -->

        <!-- 结束语 -->
        <template v-if="pageStatus === 'conclusion'">
          <conclusion :conclusion="conclusion"></conclusion>
          <div class="button-webr" style="text-align: center">
            <el-button type="primary" @click.native="submitAnAnswer" :disabled="locking">提交答案<span v-if="conclusionType === 'FIXED_DURATION'">({{conclusionDurationTxt}})</span></el-button>
          </div>
        </template>
        <!-- END结束语 -->
      </div>
    </div>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>
