<template>
  <div class="pup-ots-ut pup-out-appear" style="z-index: 10000;" :style="{backgroundColor: rely.screenColor}">
    <span v-if="type === 'TEXT'">{{text}}</span>
    <template v-else-if="type === 'IMG'">
      <img v-for="out in url" :style="{maxWidth: `${100 / url.length}%`}" :src="`${urlRoot}${out.path}`" style="height: auto;margin: 30px;" />
    </template>
    <div class="package-wer-video" style="width: 40%;max-width: 690px;" v-if="type === 'AUDIO_AND_VIDEO'">
      <payVideo :url="url" @playerEnded="playerEnded" ref="payVideo"></payVideo>
    </div>
  </div>
</template>

<script>
  import {urlRoot} from "@/config/imgUrlRoot";
  import payVideo from "@/components/payVideo/payVideo";
  function isArray (str) {
    return Object.prototype.toString.call(str) === "[object Array]"
  }
  export default {
    name: "pupOtsUt",
    inject: ['nextLogic', 'rely'],
    props: {
      answerEndTime: {
        type: Number,
        default: 0
      },
      answer: {
        type: [String, Number],
        default: ''
      }
    },
    components: {
      payVideo
    },
    data() {
      return {
        urlRoot: urlRoot,
        content: {},
        caTime: 0,
        url: '',
        text: '',
        type: ''
      }
    },
    created() {
      this.caTime = this.answerEndTime - this.nextLogic.answerStartingTime
      this.content = JSON.parse(this.nextLogic.experimentalLogicContent)
      // console.log(222, this.rely)
      // console.log(2224, this.answer)
      if (this.nextLogic.experimentalLogicType === 'REACTION_TIME') { // 根据反映时间反馈
        let expression = ''
        if (!this.content.value) {
          expression = `${this.caTime}<${this.content.timeCost}`
        } else {
          expression = `${this.caTime}>${this.content.timeCost}`
        }
        if (eval(expression)) {
          this.type = this.content.type
          if (this.content.type === 'TEXT') {
            this.text = this.content.text
          } else if (this.content.type === 'IMG')  {
            this.url = !isArray(this.content.imgUrl) ? JSON.parse(this.content.imgUrl) : this.content.imgUrl
          } else {
            this.url = this.content.audioAndVideoUrl
          }
        } else {
          this.type = this.content.type2
          if (this.content.type2 === 'TEXT') {
            this.text = this.content.text2
          } else if (this.content.type2 === 'IMG') {
            this.url = !isArray(this.content.imgUrl2) ? JSON.parse(this.content.imgUrl2) : this.content.imgUrl2
          } else {
            this.url = this.content.audioAndVideoUrl2
          }
        }
      }
      if (this.nextLogic.experimentalLogicType === 'LAST_ANSWER') { // 根据回答反馈
        if (this.content.answer == this.answer) {
          this.type = this.content.type
          if (this.content.type === 'TEXT') {
            this.text = this.content.text
          } else if (this.content.type === 'IMG')  {
            this.url = !isArray(this.content.imgUrl) ? JSON.parse(this.content.imgUrl) : this.content.imgUrl
          } else {
            this.url = this.content.audioAndVideoUrl
          }
        } else {
          this.type = this.content.type2
          if (this.content.type2 === 'TEXT') {
            this.text = this.content.text2
          } else if (this.content.type2 === 'IMG') {
            this.url = !isArray(this.content.imgUrl2) ? JSON.parse(this.content.imgUrl2) : this.content.imgUrl2
          }
        }
      }
      if (this.type !== 'AUDIO_AND_VIDEO') {
        let outTime = setTimeout(() => {
          clearTimeout(outTime)
          this.$emit('nextAnts')
        }, 3000)
      }else {
        this.$emit('nextAnts')
      }
    },
    methods: {
      playerEnded() {
        this.$emit('nextAnts')
      }
    }
  }
</script>

<style scoped lang="scss">
.pup-ots-ut{

}
</style>