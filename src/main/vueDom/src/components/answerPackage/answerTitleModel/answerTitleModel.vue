<template>
  <div>
    <h2 class="package-wer-title" v-if="nextTitle.item.stimulateType === 'TEXT'">{{nextTitle.item.stimulateContentName}}</h2>
    <h2 class="package-wer-title" v-else>{{nextTitle.item.trialName}}</h2>
    <div class="package-wer-img" v-if="nextTitle.item.stimulateType === 'IMG' && nextTitle.item.buttonType !== 'MOUSE_AND_MOUSE_TRACK'" :class="[nextTitle.item.positionType, JSON.parse(nextTitle.item.stimulateContentUrl || '[]').length ? '' : 'only-img']">
      <img v-for="out in JSON.parse(nextTitle.item.stimulateContentUrl || '[]')" :style="{maxWidth: `${100 / JSON.parse(nextTitle.item.stimulateContentUrl || '[]').length}%`}" :src="`${urlRoot}${out.path}`">
    </div>
    <div class="package-wer-video" v-if="nextTitle.item.stimulateType === 'AUDIO_AND_VIDEO'">
      <payVideo :url="nextTitle.item.stimulateContentUrl" @playerEnded="playerEnded" ref="payVideo"></payVideo>
    </div>
  </div>
</template>

<script>
import { urlRoot } from "@/config/imgUrlRoot";
import payVideo from "@/components/payVideo/payVideo";
export default {
  name: "answerTitleModel",
  inject: ['nextTitle'],
  data() {
    return {
      urlRoot: urlRoot
    }
  },
  components: {
    payVideo
  },
  created() {
    console.log(this.nextTitle.item)
  },
  methods: {
    playerEnded() {
      this.$emit('playerEnded')
    }
  }
}
</script>

<style scoped lang="scss">

</style>