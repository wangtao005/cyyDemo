import answerTool from "@/mixins/answerTool";
export default {
  name: "textInput",
  mixins: [answerTool],
  props: {

  },
  data() {
    return {
      value: ''
    }
  },
  created() {

  },
  methods: {
    updateAnswer() {
      this.$emit('clickAnswer', {
        type: 'TEXT_INPUT',
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
