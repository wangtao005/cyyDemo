import { isEmptyObject } from "@/utils/toolScript";
import editor from "@/components/Editor/editor";
export default {
  name: "endInstruction",
  data() {
    return {
      homeForm: {
        conclusion: '', // 结束语
        conclusionType: 'FIXED_DURATION', //0:固定时长(毫秒);1:随机时间,时间段;2:等待用户
        conclusionDuration: 6000
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
