import { isEmptyObject } from "@/utils/toolScript";
import editor from "@/components/Editor/editor";

export default {
  name: "startInstruction",
  data() {
    return {
      homeForm: {
        experimentName: '', // 实验名称
        whetherFullScreen: 'NOT_FULL_SCREEN', // 是否全屏(0:全屏;1:非全屏)
        instructionType: 'FIXED_DURATION', // 指导语类别(0:固定时长(毫秒);1:随机时间,时间段;2:等待用户)
        instruction: '', // 指导语
        instructionDuration: 6000 // 指导语时长
      }
    }
  },
  props: {
    nextData: {
      type: Object,
      default() {
        return {}
      }
    },
    disabled: {
      type: Boolean,
      default: false
    },
    experimentType: {
      type: String,
      default: 'NO_FEEDBACK'
    }
  },
  created() {
    if (isEmptyObject(this.nextData)) {
      this.homeForm = JSON.parse(JSON.stringify(this.nextData))
    } else {
      this.$emit('update:next-data', this.homeForm)
    }
  },
  components: {
    editor
  },
  watch: {
    homeForm: {
      handler(val) {
        this.$emit('update:next-data', val)
      },
      deep: true
    },
    nextData: {
      handler(val) {
        if (isEmptyObject(val)) {
          this.homeForm = val
        }
      },
      deep: true
    }
  }
}
