import answerTool from "@/mixins/answerTool";

export default {
  name: "keyBoard",
  mixins: [answerTool],
  props: {

  },
  data() {
    return {

    }
  },
  created() {
    this.buttonContent = JSON.parse(this.item.buttonContent)
    window.addEventListener('keyup', this.chengKeyUp, false)
    window.addEventListener('keydown', this.chengKeyDown, false)
    // 注销组件是移除键盘监听事件
    this.$once('hook:beforeDestroy', () => {
      window.removeEventListener('keyup', this.chengKeyUp, false)
      window.removeEventListener('keydown', this.chengKeyDown, false)
    }, false)
  },
  methods: {
    chengKeyDown(e) {
      let keyDom = this.$refs[`${e.key}Key`]
      if (keyDom) {
        this.enterKey = e.key
        if (!keyDom[0].classList.contains('hover-but-clst')) {
          keyDom[0].classList.add('hover-but-clst')
        }
      } else {
        this.enterKey = ''
      }
    },
    chengKeyUp(e) {
      if (this.enterKey) {
        let keyDom = this.$refs[`${this.enterKey}Key`]
        keyDom[0].classList.remove('hover-but-clst')
        this.selectEnd()
        this.setAnswerData(keyDom[0].getAttribute('data-id'))
      }
    },
    setAnswerData(index) {
      this.indexNext = index
      this.answer = this.buttonContent[index] ? this.buttonContent[index].keyUp : ''
      console.log(this.answer)
    },
    updateAnswer() {
      let keyUp = this.buttonContent[this.indexNext] ? this.buttonContent[this.indexNext].keyUp : ''
      let name = this.buttonContent[this.indexNext] ? this.buttonContent[this.indexNext].name : ''
      this.$emit('clickAnswer', {
        type: 'KEYBOARD',
        data: {
          title: this.item.stimulateType === 'TEXT' ? this.item.stimulateContentName : this.item.trialName,
          answer: `${keyUp},${name}`,
          answerStartingTime: this.answerStartingTime,
          answerEndTime: this.answerEndTime,
          id: this.item.id,
          allAnswerTime: new Date().getTime(),
          trialContentType: this.item.trialContentType,
          betweenContentTime: this.betweenContentTime
        }
      })
    }
  }
}
