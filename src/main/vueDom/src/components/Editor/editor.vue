<template>
  <div class="editor-webkit">
    <div class="onToLoading">
      <div v-loading="loading" ref="editorDom"></div>
    </div>
  </div>
</template>

<script>
  import script from "./script.js";
  export default script
</script>

<style scoped lang="scss">
@import "src/components/Editor/style";
</style>
