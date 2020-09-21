<template>
  <div id="user-information-collection" style="width: 50%;margin: 0 auto;">
    <div style="height: 10px"></div>
    <div class="diff-dom" v-if="participantBasicInfo.indexOf('name') > -1">
      <span class="title-span">姓名</span>
      <el-input
          placeholder="请输入您的姓名"
          size="medium"
          v-model.trim="userInformationCollectionData.name">
      </el-input>
    </div>
    <div class="diff-dom" v-if="participantBasicInfo.indexOf('age') > -1">
      <span class="title-span">年龄</span>
      <el-input
          placeholder="请输入您的年龄"
          size="medium"
          onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
          v-model.trim="userInformationCollectionData.age">
      </el-input>
    </div>
    <div class="diff-dom" v-if="participantBasicInfo.indexOf('sex') > -1">
      <span class="title-span">性别</span>
      <el-select v-model="userInformationCollectionData.sex" placeholder="请选择" style="width: 100%">
        <el-option value="男"></el-option>
        <el-option value="女"></el-option>
        <el-option value="无性别"></el-option>
      </el-select>
<!--      <el-input-->
<!--          placeholder="请输入您的性别"-->
<!--          size="medium"-->
<!--          onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"-->
<!--          v-model.trim="userInformationCollectionData.age">-->
<!--      </el-input>-->
    </div>
    <div class="diff-dom" v-if="participantBasicInfo.indexOf('yearsOfEducation') > -1">
      <span class="title-span">受教育年限</span>
      <el-input
          placeholder="请输入您的受教育年限或者学历"
          size="medium"
          v-model.trim="userInformationCollectionData.yearsOfEducation">
      </el-input>
    </div>

    <div class="diff-dom" v-if="participantBasicInfo.indexOf('sexualOrientation') > -1">
      <span class="title-span"><span>请选择一下选项来表明自己的性取向</span><span style="display: inherit;color: #757575;" :style="{fontSize: `${fontSize - 2}px`}">(完全保密，仅用于研究，请放心填写.)</span></span>
      <el-select v-model="userInformationCollectionData.sexualOrientation" placeholder="请选择" style="width: 100%">
        <el-option value="完全异性恋"></el-option>
        <el-option value="主要为异性恋，只是偶有同性恋行为"></el-option>
        <el-option value="主要为异性恋，但也有同性恋行为"></el-option>
        <el-option value="异性恋与同性恋倾向相同"></el-option>
        <el-option value="主要为同性恋爱，但也有异性恋行为"></el-option>
        <el-option value="主要为同性恋爱，只偶尔有异性恋行为"></el-option>
        <el-option value="完全同性恋"></el-option>
        <el-option value="无性恋"></el-option>
      </el-select>
    </div>
    <div class="diff-dom" v-if="participantBasicInfo.indexOf('height') > -1">
      <span class="title-span">身高</span>
      <el-input
          placeholder="请输入您的身高(cm)"
          size="medium"
          onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
          v-model.trim="userInformationCollectionData.height">
      </el-input>
    </div>
    <div class="diff-dom" v-if="participantBasicInfo.indexOf('bodyWeight') > -1">
      <span class="title-span">体重</span>
      <el-input
          placeholder="请输入您的体重(kg)"
          size="medium"
          onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
          v-model.trim="userInformationCollectionData.bodyWeight">
      </el-input>
    </div>
    <div style="text-align: center">
      <el-button @click="emitUpdate" type="primary">填写完成</el-button>
    </div>
  </div>
</template>

<script>
import script from './script.js'
export default script
</script>

<style scoped lang="scss">
#user-information-collection{
  .diff-dom{
    display: flex;
    align-items: center;
    margin: 10px 0;
    .title-span{
      min-width: 260px;
      // white-space: nowrap;
      padding-left: 10px;
      span{
        width: 100%;
        display: inline-block;
      }
    }
  }
}
</style>