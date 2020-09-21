<template>
  <div class="conclusion">
    <h2>结束语</h2>
    <div id="html-centent" v-html="conclusion"></div>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
.conclusion{
  overflow: hidden;
  margin: 0 10px;
  #html-centent{
  }
}

</style>
