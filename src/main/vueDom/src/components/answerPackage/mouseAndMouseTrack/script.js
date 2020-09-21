import answerTool from "@/mixins/answerTool";
export default {
  name: "mouseAndMouseTrack",
  mixins: [answerTool],
  props: {

  },
  data() {
    return {
      value: '',
      isMove: false,
      pageX: 0,
      pageY: 0,
      itrmTim: null,
      tracks: [],
      isTracks: true,
      isClick: false
    }
  },
  created() {
    this.buttonContent = JSON.parse(this.item.buttonContent)
    console.log(this.buttonContent)
    let trackLot = setTimeout(() => {
      clearTimeout(trackLot)
      // 监听鼠标移动
      document.addEventListener('mousemove', this.mousemoveEvent, false)
      // 鼠标移入图像
      this.$refs.trackDom.addEventListener('mouseover', this.mouseoEvent, false)
      // 鼠标移出图像
      this.$refs.trackDom.addEventListener('mouseout', this.mouseoEvent, false)
    }, 20)
    this.$once('hook:beforeDestroy', () => {
      document.removeEventListener('mousemove', this.mousemoveEvent, false)
      this.$refs.trackDom.removeEventListener('mouseover', this.mouseoEvent, false)
      this.$refs.trackDom.removeEventListener('mouseout', this.mouseoEvent, false)
    })
  },
  methods: {
    startMoving() {
      this.isClick = true
      this.initTimeout()
    },
    mouseoEvent({type}) {
      this.isMove = type === 'mouseover'
    },
    mousemoveEvent({pageX, pageY}) {
      this.pageX = pageX
      this.pageY = pageY
    },
    initTimeout() {
      this.itrmTim = setTimeout(() => {
        clearTimeout(this.itrmTim)
        let endArr = this.tracks.slice(-1)[0]
        if ((endArr && endArr.x !== this.pageX && endArr.y !==  this.pageY) || !endArr) {
          this.tracks.push({
            x: this.pageX,
            y: this.pageY,
            time: new Date().getTime()
          })
        }
        if (this.isTracks) {
          this.initTimeout()
        }
      }, 100)
    },
    setAnswerData() {
      if (!this.isClick) {
        return
      }
      clearTimeout(this.itrmTim)
      this.isTracks = false
    },

    updateAnswer() {

      this.$emit('clickAnswer', {
        type: 'MOUSE_AND_MOUSE_TRACK',
        data: {
          title: this.item.stimulateType === 'TEXT' ? this.item.stimulateContentName : this.item.trialName,
          answer: this.tracks,
          answerStartingTime: this.answerStartingTime,
          // answerStartingTime: this.setrTime,
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
