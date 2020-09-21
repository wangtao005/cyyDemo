import answerTool from "@/mixins/answerTool";
export default {
  name: "slider",
  mixins: [answerTool],
  props: {

  },
  data() {
    return {
      value: 0
    }
  },
  created() {
    this.buttonContent = JSON.parse(this.item.buttonContent)
    if (this.buttonContent.min) {
      this.value = +this.buttonContent.min
    }
  },
  methods: {
    updateAnswer() {
      this.$emit('clickAnswer', {
        type: 'SLIDER',
        data: {
          title: this.item.stimulateType === 'TEXT' ? this.item.stimulateContentName : this.item.trialName,
          answer: this.value,
          answerStartingTime: this.answerStartingTime,
          answerEndTime: this.answerEndTime,
          allAnswerTime: new Date().getTime(),
          id: this.item.id,
          trialContentType: this.item.trialContentType,
          betweenContentTime: this.betweenContentTime
        }
      })
    }
  }
}
