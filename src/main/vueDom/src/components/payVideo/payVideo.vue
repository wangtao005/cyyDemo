<template>
  <div class="pay-video">
    <div v-if="fileType === 'video'" id="mes" class="pay-video-xg"></div>
    <div v-else-if="fileType === 'radio'" id="mesRadio" class="pay-video-xg">
      <aplayer
        :autoplay="true"
        :music="music"
        @loadedmetadata="playerReady"
        @ended="playerEnded"
        ref="audio"
      ></aplayer>
    </div>
  </div>
</template>
<script>
  // import { videoPlayer } from 'vue-video-player'
  // import 'vue-video-player/src/custom-theme.css'
  // import 'video.js/dist/video-js.css'
  import { getFileType } from "@/utils/toolScript";
  import Player from 'xgplayer'
  import Aplayer from 'vue-aplayer'
  import { urlRoot } from "@/config/imgUrlRoot";

  export default {
    name: 'playVideo',
    props: {
      url: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        player: null,
        urlRoot: urlRoot,
        fileType: 'radio',
        music: {
          title: '音频播放',
          artist: ' ',
          src: '',
          pic: ''
        }
      }
    },
    created() {
      this.fileType = getFileType(this.url)// radio
      this.music.src = `${urlRoot}${this.url}`
      let trim = setTimeout(() => {
        clearTimeout(trim)
        this.init(this.fileType)
      }, 20)
    },
    methods: {
      init(type) {
        switch (type) {
          case 'radio':
            for (let i = 0;  i < this.$refs.audio.$el.childNodes.length; i++) {
              let emt = this.$refs.audio.$el.childNodes[i]
              if (emt.tagName === 'AUDIO') {
                emt.addEventListener('ended', this.playerEnded, false)
                // 注销组件时移除ended监听
                this.$once('hook:beforeDestroy', () => {
                  emt.removeEventListener('ended', this.playerEnded, false)
                }, false)
                break;
              }
            }
            break;
          case 'video': // 视频
            this.player = new Player({
              id: 'mes',
              url: `${urlRoot}${this.url}`,
              autoplay: true
            })
            this.player.once('ready', this.playerReady)
            this.player.once('ended', this.playerEnded)
            break;
        }
      },
      playerReady(e) {
        // console.log(e)
        // this.player.play()
      },
      playerEnded() { // 视频播放结束
        this.$emit('playerEnded', {})
      },
      payerPause() {
        if (this.fileType === 'video') {
          this.player.pause()
        } else {
          this.$refs.audio.pause()
        }
      }
    },
    components: {
      Aplayer
    }
  }
</script>
<style scoped lang="scss">
  .pay-video{
    overflow: hidden;
    padding-top: 20px;
    .pay-video-xg{
      margin: 0 auto;
    }
    ::v-deep.aplayer{
      .aplayer-body{
        .aplayer-pic{
          background: #41b883;
          .aplayer-pause{
            top: 50%;
            left: 0;
            right: 0;
            bottom: 0;
            margin: -8px auto 0 auto;
          }
          .aplayer-button{

          }
        }
      }
    }
  }
</style>
