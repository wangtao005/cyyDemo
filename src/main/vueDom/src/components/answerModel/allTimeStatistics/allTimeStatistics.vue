<template>
  <div id="all-time-statistics">
    <span>开始时间:{{dayjs(startingTime).format('YYYY-MM-DD HH:mm').split(' ')[1]}}</span>
    <span style="margin-left: 10px">用时: {{timeCostText}}</span>
  </div>
</template>

<script>
  import dayjs from 'dayjs'
  export default {
    name: "allTimeStatistics",
    data() {
      return {
        startingTime: new Date().getTime(),
        endtingTime: new Date().getTime(),
        timeCost: {

        },
        timeOut: null
      }
    },
    computed: {
      timeCostText() {
        let { day, hours, minutes, seconds } = this.timeCost
        // 向父组件更新计时状态
        // this.$emit('update:updTingTime', {
        //   startingTime: this.startingTime,
        //   endtingTime: this.endtingTime
        // })
        return `${ day ? `${day}天 ` : '' }${ hours ? `${hours}小时 ` : `` }${ minutes ? `${minutes}分钟 ` : `0 分钟` }${ seconds ? ` ${seconds} 秒` : ` 0 秒` }`
      }
    },
    created() {
      this.init()
      // 组件注销时清除定时器
      this.$once('hook:beforeDestroy', () => {
        clearTimeout(this.timeOut)
      })
    },
    methods: {
      init() {
        this.timing()
      },
      dayjs: dayjs,
      timing() {
        this.timeOut = setTimeout(() => {
          clearTimeout(this.timeOut)
          this.timing()
          this.updateTime()
        }, 1000)
      },
      updateTime() {
        this.endtingTime = new Date().getTime()
        let time = new Date().getTime() - this.startingTime
        this.formatDuring(time)
      },
      formatDuring(mss) {
        let day = parseInt(mss / (1000 *60 *60 * 24))
        let hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
        let minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60))
        let seconds = parseInt((mss % (1000 * 60)) / 1000)
        this.timeCost = {
          day,
          hours,
          minutes,
          seconds
        }
      }
    }
  }
</script>

<style scoped lang="scss">
#all-time-statistics{
  margin-bottom: 10px;
}
</style>
