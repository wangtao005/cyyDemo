import answerTool from "@/mixins/answerTool";

export default {
  name: "buttonWithOptions",
  mixins: [answerTool],
  props: {

  },
  data() {
    return {

    }
  },
  created() {
    this.buttonContent = JSON.parse(this.item.buttonContent)
  },
  methods: {
    setAnswerData(index) {
      this.indexNext = index
      this.answer = this.buttonContent[this.indexNext] ? this.buttonContent[this.indexNext].name : ''
      console.log(this.answer)
    },
    updateAnswer() {
      this.$emit('clickAnswer', {
        type: 'BUTTON_WITH_OPTIONS',
        data: {
          title:  this.item.stimulateType === 'TEXT' ? this.item.stimulateContentName : this.item.trialName,
          answer: this.answer,
          answerStartingTime: this.answerStartingTime,
          answerEndTime: this.answerEndTime,
          allAnswerTime: new Date().getTime(),
          id: this.item.id,
          trialContentType: this.item.trialContentType,
          betweenContentTime: this.betweenContentTime
        }
      })
    }
  },
  components: {
    // pupOutAppear
  }
}
