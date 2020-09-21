export default {
  name: "instruction",
  data() {
    return {
      activeNames: ['1']
    }
  },
  props: {
    instruction: {
      type: String,
      default: ''
    },
    experimentName: {
      type: String,
      default: ''
    }
  }
}
