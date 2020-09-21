import pupOutAppear from "@/components/answerPackage/pupOutAppear/pupOutAppear.vue";
import payVideo from "@/components/payVideo/payVideo";
import pupOtsUt from "@/components/answerPackage/pupOtsUt/pupOtsUt";
import answerTitleModel from "@/components/answerPackage/answerTitleModel/answerTitleModel";
import {LimitRandom} from "@/utils/toolScript";
import { countdown } from "@/utils/countdown";
import {urlRoot} from "@/config/imgUrlRoot";
export default {
  inject: ['rely'],
  components: {
    pupOutAppear,
    payVideo,
    pupOtsUt,
    answerTitleModel
  },
  data() {
    return {
      urlRoot: urlRoot,
      buttonContent: [],
      answerStartingTime: new Date().getTime(),
      appear: false,
      timeTie: null,
      timeNumber: 0,
      sources: [],
      countdown: 0,
      isCountdown: true,
      isLogic: false,
      answerEndTime: 0,
      indexNext: null,
      isStimulateOptions: false,
      answer: '',
      reactionPresentType: 'FIXED_DURATION', // 反应时间类别
      betweenContentTime: 0,
      setrTime: 0
    }
  },
  provide() {
    return {
      nextLogic: {
        answerStartingTime: this.answerStartingTime,
        experimentalLogicType: this.item.experimentalLogicType,
        experimentalLogicContent: this.item.experimentalLogicContent
      },
      nextTitle: {
        item: this.item,
        index: this.index
      }
    }
  },
  props: {
    item: {
      type: Object,
      default() {
        return {};
      }
    },
    index: {
      type: Number,
      default: 0
    }
  },
  created() {
    console.log('rely', this.rely)
    // if (this.index === 0) {
    //   this.answerStartingTime = this.rely.nodeTime
    // }
    this.reactionPresentType = this.item.reactionPresentType
    if (this.rely.yesOrNoShowFirstStimulate === 'NO') {
      this.isStimulateOptions = true
      // 设置答题时间
      this.initFeedback()
    } else {
      let itoTime = setTimeout(() => {
        clearTimeout(itoTime)
        this.isStimulateOptions = true
        // 设置答题时间
        this.initFeedback()
      }, 3000)
    }
  },
  methods: {
    initFeedback() { // 设置答题时间
      if (this.reactionPresentType === 'FIXED_DURATION') { // 反应时间-固定
        this.countdown = (+this.item.specifyTheReactionTime || 6000) / 1000
      } else if (this.reactionPresentType === 'PERIOD') {
        this.countdown = LimitRandom(+this.item.reactionLeastDuration || 1000, +this.item.reactionLongestDuration || 6000) / 1000
      } else if (this.reactionPresentType === 'WAITING_FOR_COMPLETION') {
        return
      }
      if (this.countdown === 0) {
        this.isExperimentType()
        return
      }
      countdown(this.countdown, 1000, (item) => {
        this.countdown = item
        if (item <= 0 && this.isCountdown) { // 倒计时结束
          this.isExperimentType()
        }
      })
    },
    selectEnd() {
      this.answerEndTime = new Date().getTime()
      if (this.$refs.payVideo) { // 停止音视频播放
        this.$refs.payVideo.payerPause()
      }
      if (this.rely.immediatelyOver === 'NO') { // 按键后，实验刺激直至直至该试次到达设定时间消失
        if (this.item.reactionPresentType === 'WAITING_FOR_COMPLETION') {
          this.isExperimentType()
        } else {
          this.isStimulateOptions = false
          console.log(1)
        }
        // if (this.rely.experimentType === 'WITH_FEEDBACK') {
        //   this.isCountdown = false // 阻止试次倒计时
        //   this.isLogic = true
        // }
      } else {
        this.isCountdown = false // 阻止试次倒计时
        // let out = setTimeout(() => {
        //   clearTimeout(out)
        //   this.updateAnswer()
        // }, 20)
        if (this.item.reactionPresentType === 'WAITING_FOR_COMPLETION') {
          this.isExperimentType()
        } else {
          console.log(this.item)
          this.isStimulateOptions = false
          this.isExperimentType()
        }
      }
    },
    isExperimentType() {
      this.answerEndTime = new Date().getTime()
      if (this.rely.experimentType === 'WITH_FEEDBACK') { // 带反馈实验
        this.isLogic = true
      } else {
        this.pupAppearStartCount()
      }
    },
    pupAppearStartCount() { // 试次呈现开始倒计时
      this.appear = true
      this.isCountdown = false // 阻止试次倒计时
      this.betweenContentTime = new Date().getTime() - this.rely.nodeTime
      setTimeout(() => {
        this.$refs.pupAppear.startCountdown()
      }, 20)
    },
    setBetweenContent(item) {
      // this.betweenContentTime = item
      // this.betweenContentTime = new Date().getTime()
    },
    playerEnded() { //音视频播放结束回调

    }
  },
  watch: {

  }
}
