<template>
  <div id="instruction">
    <h2 class="collapse-title"><span>{{experimentName}}</span></h2>
    <div id="html-centent" v-html="instruction"></div>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
#instruction{
  .collapse-title{
    text-align: center;
  }
  #html-centent{
    width: 60%;
    margin: 10px auto!important;
  }
  ::v-deep.el-collapse-item__header{
    justify-content: center;
    border-bottom: 0;
    margin-top: 30px;
    background-color: transparent;
    span{
      font-size: 20px;
    }
    .header-icon{
      font-size: 22px;
      color: red;
      transition: all 0.3s;
    }
    &.is-active{
      .header-icon{
        transform: rotate(180deg);
      }
    }
    .el-collapse-item__arrow{
      display: none;
    }
  }
  ::v-deep.el-collapse-item__wrap{
    border-bottom: 0;
    background-color: transparent;
  }
  ::v-deep#html-centent{
    margin: 0 10px;
  }
}
</style>
