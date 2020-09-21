import feedbackEditorTool from "@/mixins/feedbackEditorTool";
import {typeButtonDataDefault} from "@/utils/defaultJson";
import addRow from '@/components/addRow/addRow.vue'
export default {
  name: "listDataEditor",
  mixins: [feedbackEditorTool],
  data() {
    return {
      dataList: [
        {
          stimulateType: 'TEXT', // 刺激类别(0:文字;1:图片;2:音视频)
          stimulateContentName: '',
          trialName: '',
          stimulateContentUrl: '',
          presentDuration: 6000,  // 呈现时长
          trialContentType: 'CROSS_GAZE', // 试次间隔内容类别(0:十字注视点;1:空白屏幕)
          leastDuration: 4000, // 最少时长
          longestDuration: 6000, //最长时长
          buttonType: 'BUTTON_WITH_OPTIONS', // 按键类别(0:带选项的按钮;1:键盘;2:滑块;3:文字输入;4:鼠标与鼠标轨迹)
          buttonContent: '', // 按键内容集合
          trialPresentType: "FIXED_DURATION",
          presentType: "FIXED_DURATION",
          reactionPresentType: 'FIXED_DURATION',
          specifyTheReactionTime: 6000, // 反应时间
          reactionLeastDuration: 1000,
          reactionLongestDuration: 6000,
          correctAnswer: '', // 正确答案
          experimentalLogicType: 'REACTION_TIME', //逻辑类别 实验逻辑类别(0:REACTION_TIME(;1:LAST_ANSWER()
          experimentalLogicContent: '', // 实验内容
          positionType: 'UP_LEFT_RIGHT', // 刺激图片排列位置
          ...typeButtonDataDefault
        },
        {
          stimulateType: 'TEXT', // 刺激类别(0:文字;1:图片;2:音视频)
          stimulateContentName: '',
          trialName: '',
          stimulateContentUrl: '',
          presentDuration: 6000,  // 呈现时长
          trialContentType: 'CROSS_GAZE', // 试次间隔内容类别(0:十字注视点;1:空白屏幕)
          leastDuration: 4000, // 最少时长
          longestDuration: 6000, //最长时长
          buttonType: 'KEYBOARD', // 按键类别(0:带选项的按钮;1:键盘;2:滑块;3:文字输入;4:鼠标与鼠标轨迹)
          buttonContent: '', // 按键内容集合
          trialPresentType: "FIXED_DURATION",
          presentType: "FIXED_DURATION",
          reactionPresentType: 'FIXED_DURATION',
          specifyTheReactionTime: 6000, // 反应时间
          reactionLeastDuration: 1000,
          reactionLongestDuration: 6000,
          correctAnswer: '', // 正确答案
          experimentalLogicType: 'REACTION_TIME', //逻辑类别 实验逻辑类别(0:REACTION_TIME(;1:LAST_ANSWER()
          experimentalLogicContent: '', // 实验内容
          positionType: 'UP_LEFT_RIGHT', // 刺激图片排列位置
          ...typeButtonDataDefault
        },
        {
          stimulateType: 'TEXT', // 刺激类别(0:文字;1:图片;2:音视频)
          stimulateContentName: '',
          trialName: '',
          stimulateContentUrl: '',
          presentDuration: 6000,  // 呈现时长
          trialContentType: 'CROSS_GAZE', // 试次间隔内容类别(0:十字注视点;1:空白屏幕)
          leastDuration: 4000, // 最少时长
          longestDuration: 6000, //最长时长
          buttonType: 'SLIDER', // 按键类别(0:带选项的按钮;1:键盘;2:滑块;3:文字输入;4:鼠标与鼠标轨迹)
          buttonContent: '', // 按键内容集合
          trialPresentType: "FIXED_DURATION",
          presentType: "FIXED_DURATION",
          reactionPresentType: 'FIXED_DURATION',
          specifyTheReactionTime: 6000, // 反应时间
          reactionLeastDuration: 1000,
          reactionLongestDuration: 6000,
          correctAnswer: '', // 正确答案
          experimentalLogicType: 'REACTION_TIME', //逻辑类别 实验逻辑类别(0:REACTION_TIME(;1:LAST_ANSWER()
          experimentalLogicContent: '', // 实验内容
          positionType: 'UP_LEFT_RIGHT', // 刺激图片排列位置
          ...typeButtonDataDefault
        },
        {
          stimulateType: 'TEXT', // 刺激类别(0:文字;1:图片;2:音视频)
          stimulateContentName: '',
          trialName: '',
          stimulateContentUrl: '',
          presentDuration: 6000,  // 呈现时长
          trialContentType: 'CROSS_GAZE', // 试次间隔内容类别(0:十字注视点;1:空白屏幕)
          leastDuration: 4000, // 最少时长
          longestDuration: 6000, //最长时长
          buttonType: 'TEXT_INPUT', // 按键类别(0:带选项的按钮;1:键盘;2:滑块;3:文字输入;4:鼠标与鼠标轨迹)
          buttonContent: '', // 按键内容集合
          trialPresentType: "FIXED_DURATION",
          presentType: "FIXED_DURATION",
          reactionPresentType: 'FIXED_DURATION',
          specifyTheReactionTime: 6000, // 反应时间
          reactionLeastDuration: 1000,
          reactionLongestDuration: 6000,
          correctAnswer: '', // 正确答案
          experimentalLogicType: 'REACTION_TIME', //逻辑类别 实验逻辑类别(0:REACTION_TIME(;1:LAST_ANSWER()
          experimentalLogicContent: '', // 实验内容
          positionType: 'UP_LEFT_RIGHT', // 刺激图片排列位置
          ...typeButtonDataDefault
        },
        {
          stimulateType: 'TEXT', // 刺激类别(0:文字;1:图片;2:音视频)
          stimulateContentName: '',
          trialName: '',
          stimulateContentUrl: '',
          presentDuration: 6000,  // 呈现时长
          trialContentType: 'CROSS_GAZE', // 试次间隔内容类别(0:十字注视点;1:空白屏幕)
          leastDuration: 4000, // 最少时长
          longestDuration: 6000, //最长时长
          buttonType: 'MOUSE_AND_MOUSE_TRACK', // 按键类别(0:带选项的按钮;1:键盘;2:滑块;3:文字输入;4:鼠标与鼠标轨迹)
          buttonContent: '', // 按键内容集合
          trialPresentType: "FIXED_DURATION",
          presentType: "FIXED_DURATION",
          reactionPresentType: 'FIXED_DURATION',
          specifyTheReactionTime: 6000, // 反应时间
          reactionLeastDuration: 1000,
          reactionLongestDuration: 6000,
          correctAnswer: '', // 正确答案
          experimentalLogicType: 'REACTION_TIME', //逻辑类别 实验逻辑类别(0:REACTION_TIME(;1:LAST_ANSWER()
          experimentalLogicContent: '', // 实验内容
          positionType: 'UP_LEFT_RIGHT', // 刺激图片排列位置
          ...typeButtonDataDefault
        }
      ]
    }
  },
  created() {
    if (this.nextData.length) {
      this.dataList = this.nextData
    } else {
      this.$emit('update:next-data', this.dataList)
    }
  },
  components: {
    addRow
  }
}