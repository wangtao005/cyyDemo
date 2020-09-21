import buttonWithOptions from "./buttonWithOptions/buttonWithOptions.vue";
import keyBoard from './keyBoard/keyBoard.vue'
import slider from './slider/slider.vue'
import textInput from './textInput/textInput.vue'
import mouseAndMouseTrack from './mouseAndMouseTrack/mouseAndMouseTrack.vue'
export default {
  name: "answerPackage",
  data() {
    return {
      topicList: [],
      butType: {
        BUTTON_WITH_OPTIONS: 'buttonWithOptions',
        KEYBOARD: 'keyBoard',
        SLIDER: 'slider',
        TEXT_INPUT: 'textInput',
        MOUSE_AND_MOUSE_TRACK: 'mouseAndMouseTrack',
      },
      index: 0,
      answers: []
    }
  },
  props: {
    feedbackExperimentStimulate: {
      type: Array,
      default() {
        return []
      }
    }
  },
  created() {
    if (this.feedbackExperimentStimulate.length) {
      this.topicList = [...this.feedbackExperimentStimulate]
    }
  },
  methods: {
    comName(type) {
      // console.log(type)
      return this.butType[type] || ''
    },
    clickAnswer(item) {
      console.log(item)
      this.answers.push(item)
      if (this.topicList.length - 1 > this.index) {
        this.index++
      } else {
        // this.$emit('answerIndex', this.index)
        this.$emit('endOfAnswer', this.answers)
      }
    }
  },
  components: {
    buttonWithOptions,
    keyBoard,
    slider,
    textInput,
    mouseAndMouseTrack
  },
  watch: {
    feedbackExperimentStimulate: {
      handler(val) {
        this.topicList = [...val]
      },
      deep: true
    },
    index(val)  {
      this.$emit('answerIndex', val)
    }
  }
}
