<template>
  <div class="with-feedback">
    <noFeedback :experiment-type-pop="'WITH_FEEDBACK'"></noFeedback>
  </div>
</template>

<script>
  import noFeedback from '@/views/noFeedback/index.vue'
  export default {
    name: "withFeedback",
    components: {
      noFeedback
    }
  }
</script>

<style scoped lang="scss">
.with-feedback{
  position: absolute;
  top: 2px;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
  background-color: #FFF;
  z-index: 1000;
  overflow: hidden;
  overflow-y: auto
}
</style>