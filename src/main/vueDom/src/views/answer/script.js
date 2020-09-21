import topicInfo from "@/api/topicInfo";
import saveAnswer from "@/api/saveAnswer";
import instruction from '@/components/answerModel/instruction/instruction.vue'
import allTimeStatistics from "@/components/answerModel/allTimeStatistics/allTimeStatistics.vue";
import answerPackage from '@/components/answerPackage/answerPackage.vue'
import conclusion from '@/components/answerModel/conclusion/conclusion.vue'
import userInformationCollection from '@/components/userInformationCollection/userInformationCollection.vue'
import { countdown } from "@/utils/countdown";
let loadingPut = null
export default {
  name: "answer",
  data() {
    return {
      infoId: '',
      whetherFullScreen: 'FULL_SCREEN',
      instructionData: {
        instruction: '',
        experimentName: ''
      },
      conclusion: '',
      feedbackExperimentStimulate: [],
      pageStatus: 'isFullScreen', // isFullScreen: 是否全屏 instruction:指导语 userInfo: 用户信息收集 answer:答题 conclusion:结束语
      stimulateType: 'TEXT',
      rely: {
        stimulateType: 'TEXT',
        experimentType: 'NO_FEEDBACK',
        immediatelyOver: 'NO',
        yesOrNoShowFirstStimulate: 'NO',
        nodeTime: 0,
        screenColor: '#FFF'
      },
      answer: [],
      instructionDuration: 0,
      conclusionDuration: 0,
      conclusionDurationTxt: 0,
      conclusionType: 'FIXED_DURATION',
      locking: false,
      percentage: 100,
      screenColor: '#FFF',
      fontSize: '14',
      fontColor: '#000',
      yesOrNoViewProgressBar: 'NO',
      participantBasicInfo: [],
      participantBasicInfoData: {
        name: '',
        sex: '',
        age: '',
        yearsOfEducation: '',
        height: '',
        sexualOrientation: '',
        bodyWeight: ''
      },
      instructionType: 'WAITING_FOR_COMPLETION',
      docElm: document.documentElement,
      isFullscreen: false  // 是否全屏
    }
  },
  provide() {
    return {
      rely: this.rely
    }
  },
  computed: {
    timeCostText() {
      if (this.conclusionDuration) {
        return this.conclusionDuration
      } else {
        return 0
      }
    }
  },
  created() {
    this.infoId = this.$route.params.id
    loadingPut = this.$loading({
      lock: true,
      text: '页面加载中',
      background: 'rgba(0, 0, 0, 0.7)'
    })
    this.getInfo()
  },
  methods: {
    switchTypeNext() {
      if (this.participantBasicInfo.length && this.pageStatus !== 'userInfo') {
        this.pageStatus = 'userInfo'
      } else if (this.pageStatus !== 'userInfo') {
        this.startAnswer()
      }
    },
    enterGuide() {
      if (this.instructionType === 'FIXED_DURATION') {
        countdown(this.instructionDuration, 1000, (item) => {
          this.instructionDuration = item
          if (item <= 0) {
            this.switchTypeNext()
          }
        })
      }
      this.pageStatus = 'instruction'
    },
    enterFullScreen(event) { // 进入全屏
      this.$refs.oktClick.$el.addEventListener('click', () => {
        this.toFullVideo().then(res => {
          this.isFullscreen = true
          if (this.pageStatus === 'isFullScreen') {
            this.pageStatus = 'instruction'
            this.enterGuide()
          }
        }).catch(error => {
          // 全屏失败, 
          console.log(error)
        });
      }, false)
    },
    toFullVideo() {
      let docElm = document.documentElement;
      if(docElm.requestFullscreen){
        return docElm.requestFullscreen();
      }else if(docElm.webkitRequestFullScreen){
        return docElm.webkitRequestFullScreen();
      }else if(docElm.mozRequestFullScreen){
        return docElm.mozRequestFullScreen();
      }else{
        return docElm.msRequestFullscreen();
      }
    },
    cancelFullScreen(event){ // 取消全屏
      event.target.addEventListener('click', () => {
        this.cancelFullVideo().then(res => {
          this.isFullscreen = false
          console.log(res)
          // this.enterGuide()
        }).catch(error => {
          console.log(error)
        })
      }, false)
    },
    cancelFullVideo() {
      if (document.exitFullscreen) {
        return document.exitFullscreen();
      }
      else if (document.msExitFullscreen) {
        return document.msExitFullscreen();
      }
      else if (document.mozCancelFullScreen) {
        return document.mozCancelFullScreen();
      }
      else if (document.webkitCancelFullScreen) {
        return document.webkitCancelFullScreen();
      }
    },
    startAnswer() { // 开始答题
      this.pageStatus = 'answer'
      this.rely.nodeTime = this.$refs.allTimeSta.endtingTime
    },
    endFillIn(data) {
      this.participantBasicInfoData = {...data}
      this.startAnswer()
    },
    answerIndex(index) {
      this.percentage = this.GetPercent(index, this.feedbackExperimentStimulate.length)
    },
    GetPercent(num, total) {
      num = parseFloat(num);
      total = parseFloat(total);
      if (isNaN(num) || isNaN(total)) {
        return "-";
      }
      return total <= 0 ? 0 : (Math.round(num / total * 10000) / 100.00);
    },
    endOfAnswer(list) {
      this.answer = list
      this.conclusionDurationTxt = this.conclusionDuration / 1000
      this.pageStatus = 'conclusion'
      this.percentage = 100
      if (this.conclusionType === 'FIXED_DURATION') { // 结束语固定时长
        let conclusionDuration = this.conclusionDuration / 1000
        this.conclusionDurationTxt = this.conclusionDuration / 1000
        countdown(conclusionDuration, 1000, (item) => {
          this.conclusionDurationTxt = item
          if (!item) {
            this.submitAnAnswer()
          }
        })
      }
    },
    submitAnAnswer() {
      if (this.locking) {
        return
      }
      loadingPut = this.$loading({
        lock: true,
        text: '答案提交中',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      let answerContents = []
      let spentTime = 0
      this.answer.forEach(item => {
        let imtTime = item.data.answerEndTime - item.data.answerStartingTime
        // console.log(item.data.answerEndTime, item.data.answerStartingTime, this.rely.nodeTime)
        spentTime += imtTime
        answerContents.push({
          spentTime: imtTime,
          answer: JSON.stringify(item.data.answer),
          topic: item.data.title,
          fesId: item.data.id,
          // titlePresentationTime: item.data.allAnswerTime - item.data.answerStartingTime,
          titlePresentationTime: item.data.answerStartingTime - this.rely.nodeTime,
          betweenContent: item.data.trialContentType === 'CROSS_GAZE' ? '十字注视点' : '空白屏幕',
          betweenContentTime: item.data.betweenContentTime
        })
      })
      saveAnswer({
        feedbackId: this.infoId,
        spentTime: spentTime, //所用时长
        ...this.participantBasicInfoData,
        answerContents: answerContents
      }).then(res => {
        loadingPut.close()
        if (res.code === 200) {
          alert('提交成功')
          this.locking = true
        } else {
          this.$message.error('答案提交失败');
        }
      })
      
    },
    getInfo() {
      topicInfo({
        id: this.infoId
      }).then(res => {
        loadingPut.close()
        if (res.code === 200) {
          let { experimentName, whetherFullScreen, stimulateType } = res.data
          let { instruction, instructionType, instructionDuration, conclusion, conclusionType, conclusionDuration, experimentType, immediatelyOver} = res.data
          let { feedbackExperimentStimulate } = res.data
          let { screenColor, fontSize, fontColor, yesOrNoViewProgressBar, yesOrNoShowFirstStimulate, participantBasicInfo } = res.data
          document.title = experimentName
          this.whetherFullScreen = whetherFullScreen
          this.stimulateType = stimulateType
          this.conclusionDuration = conclusionDuration
          this.conclusionType = conclusionType
          this.rely.stimulateType = stimulateType
          this.rely.experimentType = experimentType
          this.rely.immediatelyOver = immediatelyOver
          this.rely.yesOrNoShowFirstStimulate = yesOrNoShowFirstStimulate
          this.rely.screenColor = screenColor
          this.instructionData = {
            instruction,
            experimentName
          }
          this.conclusion = conclusion
          this.feedbackExperimentStimulate = feedbackExperimentStimulate
          this.percentage = this.GetPercent(0, feedbackExperimentStimulate.length)
          this.screenColor = screenColor
          this.fontSize = fontSize
          this.fontColor = fontColor
          this.yesOrNoViewProgressBar = yesOrNoViewProgressBar
          this.yesOrNoShowFirstStimulate = yesOrNoShowFirstStimulate
          this.participantBasicInfo = participantBasicInfo !== '' ? participantBasicInfo.split(',') : []

          let clickEvt = document.createEvent("MouseEvents");
          clickEvt.initEvent("click", true, true);
          // this.$refs.oktClick.$el.dispatchEvent(clickEvt)
          let itn = setTimeout(() => {
            clearTimeout(itn)
            this.$refs.oktClick.$el.dispatchEvent(clickEvt)
            this.$refs.notClick.$el.dispatchEvent(clickEvt)
          }, 2000)
          this.instructionType = instructionType
          if (this.instructionType === 'FIXED_DURATION') {
            this.instructionDuration = instructionDuration / 1000
          }


        } else {
          this.$message.error('获取数据失败');
        }
      })
    },
  },
  components: {
    instruction,
    allTimeStatistics,
    answerPackage,
    conclusion,
    userInformationCollection
  },
  watch: {
    sexualOrientation(val) {
      console.log(val)
    }
  }
}
