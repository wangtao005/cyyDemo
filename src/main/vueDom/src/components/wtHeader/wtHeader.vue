<template>
  <div class="wt-header">
    <div class="cont-web">
      <h2>{{title}}</h2>
      <span>{{dost}}</span>
    </div>
  </div>
</template>

<script>
  export default {
    name: "wtHeader",
    props: {
      title: {
        type: String,
        default: ''
      },
      dost: {
        type: String,
        default: ''
      }
    }
  }
</script>

<style scoped lang="scss">
.wt-header{
  height: 60px;
  background-color: #3c3c3c;
  overflow: hidden;
  .cont-web{
    width: 900px;
    margin: 0 auto;
    line-height: 1;
    font-size: 0;
    h2{
      color: #FFF;
      font-size: 20px;
      line-height: 1;
      margin: 10px 0 0 0;
    }
    span{
      line-height: 1;
      font-size: 16px;
      color: #DDD;
      margin-top: 4px;
      display: inline-block;
    }
  }
}
</style>