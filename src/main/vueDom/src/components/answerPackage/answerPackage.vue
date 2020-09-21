<template>
  <div id="answer-package">
    <template v-for="(item, key) in topicList">
      <component :is="comName(item.buttonType)" v-if="index === key" :item="item" :index="key" :key="item.id" @clickAnswer="clickAnswer"></component>
    </template>
  </div>
</template>

<script>
  import script from "./script.js";
  export default script
</script>

<style scoped lang="scss">
#answer-package{
  // min-height: 600px;
}
</style>
