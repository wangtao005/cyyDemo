<template>
  <div id="no-feedback">
    <div class="centent-webkit">
      <div class="form-centent-webkit">
        <startInstruction :next-data.sync="homeForm" :disabled="locking" :experimentType="experimentType"></startInstruction>
        <div class="home-table-webkit" style="margin-top: 10px">
          <div style="margin-top: 20px">
            是否显示进度条
            <el-radio-group v-model="yesOrNoViewProgressBar" :disabled="locking">
              <el-radio label="NO" border>关闭</el-radio>
              <el-radio label="YES" border>开启</el-radio>
            </el-radio-group>
          </div>
          <div style="display: flex;align-items: center;margin: 10px 0">
            <span style="margin-right: 10px">选择屏幕颜色</span>
            <el-color-picker style="vertical-align: top" :disabled="locking" v-model="screenColor"></el-color-picker>
          </div>
          <div style="display: flex;align-items: center;margin: 10px 0">
            <span style="margin-right: 10px">选择实验刺激（文字）的颜色</span>
            <el-color-picker style="vertical-align: top" :disabled="locking" v-model="fontColor"></el-color-picker>
          </div>
          <div style="display: flex;align-items: center;margin: 10px 0">
            <span style="margin-right: 10px">选择实验刺激（文字）的大小</span>
            <el-select v-model="fontSize" :disabled="locking" placeholder="请选择">
              <el-option
                v-for="item in fontSizeData"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <span style="margin-left: 10px" :style="{fontSize: `${fontSize}px`}">{{`${fontSize}px`}}</span>
          </div>
          <div style="display: flex;align-items: center;margin: 20px 0 30px 0">
            <span style="margin-right: 10px">请选择要记录的被试基本信息</span>
            <el-checkbox-group :disabled="locking" v-model="participantBasicInfo">
              <el-checkbox label="name">姓名</el-checkbox>
              <el-checkbox label="sex">性别</el-checkbox>
              <el-checkbox label="age">年龄</el-checkbox>
              <el-checkbox label="yearsOfEducation">受教育年限</el-checkbox>
              <el-checkbox label="sexualOrientation">性取向</el-checkbox>
              <el-checkbox label="height">身高</el-checkbox>
              <el-checkbox label="bodyWeight">体重</el-checkbox>
            </el-checkbox-group>
          </div>
          <div>
            是否随机呈现
            <el-radio-group v-model="yesOrNoRandom" :disabled="locking">
              <el-radio label="NO" border>不随机</el-radio>
              <el-radio label="YES" border>随机</el-radio>
            </el-radio-group>
          </div>
          <div style="display: flex;align-items: center;margin: 13px 0">
            实验刺激呈现完之后再出现选项
            <el-radio-group style="margin-left: 10px" v-model="yesOrNoShowFirstStimulate" :disabled="locking">
              <el-radio label="NO" border>否</el-radio>
              <el-radio label="YES" border>是</el-radio>
            </el-radio-group>
          </div>

          <h2 class="home-form-title">作您的实验刺激，实验参数表</h2>
          <div>
<!--&lt;!&ndash;            刺激类型&ndash;&gt;-->
<!--            <el-radio-group v-model="stimulateType" :disabled="locking">-->
<!--              <el-radio label="TEXT" border>文字</el-radio>-->
<!--              <el-radio label="IMG" border>图片</el-radio>-->
<!--              <el-radio label="AUDIO_AND_VIDEO" border>音视频</el-radio>-->
<!--            </el-radio-group>-->
            <el-button type="warning" v-if="!infoId" style="margin-bottom: 10px;width: 100%;" @click="dowDownloadExcel">下载模板（若您需要批量设置）</el-button>
            <el-button type="primary" v-if="!infoId" style="margin-bottom: 10px;width: 100%;margin-left: 0;" @click="$refs.uploadExcel.click()">导入您制作好的模板</el-button>
          </div>
          <div class="ont-tabs-webkit" style="margin-top: 10px">
<!--            <textEditor v-if="stimulateType === 'TEXT'" ref="TEXT" :next-data.sync="textData" :disabled="locking" :dels.sync="childIds" :experiment-type="experimentType"></textEditor>-->
<!--            <imgEditor v-if="stimulateType === 'IMG'" ref="IMG" :next-data.sync="imgData" :disabled="locking" :dels.sync="childIds" :experiment-type="experimentType"></imgEditor>-->
<!--            <audioVideoEditor v-if="stimulateType === 'AUDIO_AND_VIDEO'" ref="AUDIO_AND_VIDEO" :next-data.sync="audioVideoData" :dels.sync="childIds" :disabled="locking" :experiment-type="experimentType"></audioVideoEditor>-->
            <listDataEditor ref="listDataEditor" :next-data.sync="listDataEditorData" :dels.sync="childIds" :experiment-type="experimentType" :disabled="locking"></listDataEditor>
          </div>
          <div style="display: flex;align-items: center;margin: 10px 0">
            反应设置
            <el-radio-group style="margin-left: 10px" v-model="immediatelyOver" :disabled="locking">
              <el-radio label="NO" border>按键后，实验刺激直至该试次到达设定时间消失</el-radio>
              <el-radio label="YES" border>一旦按键，实验刺激立即消失，该试次结束</el-radio>
            </el-radio-group>
          </div>
        </div>

        <endInstruction :next-data.sync="endInstruction" :disabled="locking"></endInstruction>
        <div style="text-align: right;margin-top: 10px">
          <el-button type="primary" @click="updateAjax" v-if="!locking" :disabled="locking">保存</el-button>
        </div>
      </div>
    </div>
    <div style="height: 10px"></div>
    <input style="display: none" type="file" ref="uploadExcel" @change="uploadExcel" accept=".xls,.xlsx"></input>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>
