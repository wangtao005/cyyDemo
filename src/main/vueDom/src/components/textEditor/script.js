import addRow from "@/components/addRow/addRow.vue";
import { typeButtonDataDefault } from '@/utils/defaultJson.js'
import feedbackEditorTool from "@/mixins/feedbackEditorTool";
export default {
  name: "textEditor",
  mixins: [feedbackEditorTool],
  data() {
    return {
      loading: false,
      dataList: [
        {
          stimulateContentName: '',
          trialName: '',
          presentDuration: 6000,  // 呈现时长
          trialContentType: 'CROSS_GAZE', // 试次间隔内容类别(0:十字注视点;1:空白屏幕)
          leastDuration: 1000, // 最少时长
          longestDuration: 2000, //最长时长
          buttonType: 'BUTTON_WITH_OPTIONS', // 按键类别(0:带选项的按钮;1:键盘;2:滑块;3:文字输入;4:鼠标与鼠标轨迹)
          buttonContent: '', // 按键内容集合
          trialPresentType: "FIXED_DURATION",
          presentType: "FIXED_DURATION",
          specifyTheReactionTime: 6000, // 反应时间
          correctAnswer: '', // 正确答案
          experimentalLogicType: 'REACTION_TIME', //逻辑类别 实验逻辑类别(0:REACTION_TIME(;1:LAST_ANSWER()
          experimentalLogicContent: '', // 实验内容
          positionType: 'UP_LEFT_RIGHT', // 刺激图片排列位置
          ...typeButtonDataDefault
        }
      ]
    }
  },
  props: {

  },
  created() {
    if (this.nextData.length) {
      this.dataList = this.nextData
    } else {
      this.$emit('update:next-data', this.dataList)
    }
  },
  methods: {

  },
  components: {
    addRow
  },
  watch: {

  }
}
