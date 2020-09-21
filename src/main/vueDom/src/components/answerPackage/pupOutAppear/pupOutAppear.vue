<template>
  <div class="pup-out-appear" style="z-index: 10000;" :style="{backgroundColor: rely.screenColor}">
    <i class="el-icon-plus" v-if="item.trialContentType === 'CROSS_GAZE'"></i>
  </div>
</template>

<script>
  import {LimitRandom} from "@/utils/toolScript";
  import { countdown } from "@/utils/countdown";

  export default {
    name: "pupOutAppear",
    inject: ['rely'],
    data() {
      return {
        timeNumber: 6000
      }
    },
    props: {
      item: {
        type: Object,
        default() {
          return {}
        }
      }
    },
    methods: {
      startCountdown() { // 开始倒计时
        if (this.item.presentType === 'FIXED_DURATION') { // 固定
          this.timeNumber = (+this.item.presentDuration || 6000) / 1000
        } else if (this.item.presentType === 'PERIOD') { // 不固定
          this.timeNumber = LimitRandom(+this.item.leastDuration || 1000, +this.item.longestDuration || 2000) / 1000
        }
        this.$emit('betweenContentTime', this.timeNumber * 1000)
        countdown(this.timeNumber, 1000, (item) => {
          this.timeNumber = item
          if (item <= 0) { // 倒计时结束
            this.$emit('endCountdown')
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
