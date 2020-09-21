<template>
  <div id="examine">
    <noFeedback :info-id="infoId" :locking-out="true" :experiment-type-pop="'WITH_FEEDBACK'"></noFeedback>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>
