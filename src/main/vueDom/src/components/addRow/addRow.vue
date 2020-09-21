<template>
  <div id="add-text" :v-loading="loading">
    <el-form ref="homeForm" class="home-form" v-model="rowDataOts" label-width="130px" label-position="left">
      <el-form-item label="刺激类型">
        <el-radio-group v-model="rowDataOts.stimulateType" :disabled="disabled">
          <el-radio label="TEXT" border>文字</el-radio>
          <el-radio label="IMG" border>图片</el-radio>
          <el-radio label="AUDIO_AND_VIDEO" border>音视频</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="rowDataOts.stimulateType !== 'TEXT'" :label="rowDataOts.stimulateType === 'IMG' ? '实验刺激-图片' : '实验刺激-音视频'" style="line-height: 1;">
        <template v-if="rowDataOts.stimulateType === 'IMG'" >
          <updateImg :urls.sync="IMG_StimulateContentUrl" :disabled="disabled"></updateImg>
        </template>
        <template v-if="rowDataOts.stimulateType === 'AUDIO_AND_VIDEO'">
          <updateAudioAndVideo :link-url.sync="AUDIO_AND_VIDEO_StimulateContentUrl" :disabled="disabled"></updateAudioAndVideo>
        </template>
      </el-form-item>
      <el-form-item v-if="rowDataOts.stimulateType === 'IMG'" label="图片排列方式">
        <el-select v-model="rowDataOts.positionType" placeholder="请选择" :disabled="disabled">
          <el-option label="左上角和右上角" value="UP_LEFT_RIGHT"></el-option>
          <el-option label="横排" value="CENTER_HORIZONTAL"></el-option>
          <el-option label="竖排" value="CENTER_VERTICAL"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item v-if="rowDataOts.stimulateType === 'TEXT'" label="实验刺激">
        <el-input
          placeholder="请输入"
          :disabled="disabled"
          size="medium"
          v-model.trim="rowDataOts.stimulateContentName">
        </el-input>
      </el-form-item>

      <el-form-item label="试次说明">
        <el-input
          placeholder="请输入"
          :disabled="disabled"
          size="medium"
          v-model.trim="rowDataOts.trialName">
        </el-input>
      </el-form-item>


      <el-form-item label="反应时间类别">
        <el-radio-group v-model="rowDataOts.reactionPresentType" :disabled="disabled">
          <el-radio label="FIXED_DURATION" border>固定</el-radio>
          <el-radio label="PERIOD" border>不固定时长</el-radio>
          <el-radio label="WAITING_FOR_COMPLETION" border>等待被试</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="反应时间-固定" v-if="rowDataOts.reactionPresentType === 'FIXED_DURATION'">
        <el-input
          placeholder="题目反应时间(毫秒)"
          type="number"
          :disabled="disabled"
          size="medium"
          :precision="0"
          onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
          v-model.trim="rowDataOts.specifyTheReactionTime">
        </el-input>
      </el-form-item>
      <el-form-item label="反应时间-随机" v-if="rowDataOts.reactionPresentType === 'PERIOD'">
        最小
        <el-input
          placeholder="请输入最小呈现时长(毫秒)"
          type="number"
          style="width: 40%"
          :disabled="disabled"
          size="medium"
          onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
          v-model.trim="rowDataOts.reactionLeastDuration">
        </el-input> - 最大
        <el-input
          placeholder="请输入最大呈现时长(毫秒)"
          style="width: 40%"
          type="number"
          size="medium"
          :disabled="disabled"
          v-model.trim="rowDataOts.reactionLongestDuration">
        </el-input>
      </el-form-item>

      <el-form-item label="试次之间呈现内容">
        <el-radio-group v-model="rowDataOts.trialContentType" :disabled="disabled">
          <el-radio label="CROSS_GAZE" border>十字注视点</el-radio>
          <el-radio label="BLANK_SCREEN" border>空白屏幕</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="呈现时长类别">
        <el-radio-group v-model="rowDataOts.presentType" :disabled="disabled">
          <el-radio label="FIXED_DURATION" border>固定</el-radio>
          <el-radio label="PERIOD" border>不固定时长</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="呈现-固定时长" v-if="rowDataOts.presentType === 'FIXED_DURATION'">
        <el-input
          placeholder="请输入呈现时长(毫秒)"
          type="number"
          :disabled="disabled"
          size="medium"
          onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
          v-model.trim="rowDataOts.presentDuration">
        </el-input>
      </el-form-item>

      <el-form-item label="呈现-不固定时长" v-if="rowDataOts.presentType === 'PERIOD'">
        最小
        <el-input
          placeholder="请输入最小呈现时长(毫秒)"
          type="number"
          style="width: 40%"
          :disabled="disabled"
          size="medium"
          onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
          v-model.trim="rowDataOts.leastDuration">
        </el-input> - 最大
        <el-input
          placeholder="请输入最大呈现时长(毫秒)"
          style="width: 40%"
          type="number"
          size="medium"
          :disabled="disabled"
          v-model.trim="rowDataOts.longestDuration">
        </el-input>
      </el-form-item>

      <el-form-item label="设置按键类型">
        <el-radio-group v-model="rowDataOts.buttonType" :disabled="disabled" @change="buttonTypeSwatch">
          <el-radio size="mini" label="BUTTON_WITH_OPTIONS">带选项的按钮</el-radio>
          <el-radio size="mini" label="KEYBOARD">键盘</el-radio>
          <el-radio size="mini" label="SLIDER">滑块</el-radio>
          <el-radio size="mini" label="TEXT_INPUT">文字输入</el-radio>
          <el-radio size="mini" label="MOUSE_AND_MOUSE_TRACK">鼠标与鼠标轨迹</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="设置带选项的按钮" v-if="rowDataOts.buttonType === 'BUTTON_WITH_OPTIONS'">
        <div class="webkit-list-wk">
          <el-table
            :data="rowDataOts.buttonContentButton"
            :header-cell-style="{background:'#eef1f6',color:'#606266', padding: 0}"
            border
          >
            <el-table-column
              type="index"
              align="center"
              label="序号"
              width="80">
            </el-table-column>
            <el-table-column prop="name" label="请输入文字" header-align="center" align="left" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-input
                  size="mini"
                  placeholder="请输入"
                  :disabled="disabled"
                  v-model.trim="scope.row.name">
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="130" align="center">
              <template slot-scope="scope">
                <el-button-group style="margin-left: 10px">
                  <el-button type="primary" size="mini" icon="el-icon-plus" :disabled="disabled" @click="addRow(scope.$index, rowDataOts.buttonContentButton)"></el-button>
                  <el-button type="primary" size="mini" icon="el-icon-minus" :disabled="disabled" @click="deleteRow(scope.$index, rowDataOts.buttonContentButton)"></el-button>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form-item>

      <el-form-item label="设置键盘" v-if="rowDataOts.buttonType === 'KEYBOARD'">
        <div class="webkit-list-wk">
          <el-table
            :data="rowDataOts.buttonContentKeyUp"
            :header-cell-style="{background:'#eef1f6',color:'#606266', padding: 0}"
            border
          >
            <el-table-column
              type="index"
              align="center"
              label="序号"
              width="80">
            </el-table-column>
            <el-table-column prop="keyUp" label="键盘名称" header-align="center" align="left" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-input
                  size="mini"
                  placeholder="请输入键盘名称"
                  :disabled="disabled"
                  v-model.trim="scope.row.keyUp">
                </el-input>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="代表意义" header-align="center" align="left" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-input
                  size="mini"
                  placeholder="请输入"
                  :disabled="disabled"
                  v-model.trim="scope.row.name">
                </el-input>
              </template>
            </el-table-column>


            <el-table-column label="操作" width="130" align="center">
              <template slot-scope="scope">
                <el-button-group style="margin-left: 10px">
                  <el-button type="primary" size="mini" icon="el-icon-plus" :disabled="disabled" @click="addRow(scope.$index, rowDataOts.buttonContentKeyUp)"></el-button>
                  <el-button type="primary" size="mini" icon="el-icon-minus" :disabled="disabled" @click="deleteRow(scope.$index, rowDataOts.buttonContentKeyUp)"></el-button>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form-item>

      <el-form-item label="滑块" v-if="rowDataOts.buttonType === 'SLIDER'">
        {{rowDataOts.buttonContentSlider.min}}
        <el-slider
          style="width: 72%;display: inline-block;vertical-align: top;margin: 0 10px;"
          v-model="buttonContentSliderValue"
          :min="0"
          :max="100"
          :show-tooltip="true"
          :disabled="disabled"
         >
        </el-slider>
        {{rowDataOts.buttonContentSlider.max}}
        <div style="clear:both;width: 84%">
          <span>最小
            <el-input-number
              v-model="rowDataOts.buttonContentSlider.min"
              :min="rowDataOts.buttonContentSlider.min"
              :max="rowDataOts.buttonContentSlider.max"
              label="最小"
              size="mini"
              :disabled="disabled"
            ></el-input-number>
          </span>
          <span style="float: right;">最大
            <el-input-number
              v-model="rowDataOts.buttonContentSlider.max"
              :min="rowDataOts.buttonContentSlider.min"
              :max="rowDataOts.buttonContentSlider.max"
              label="最大"
              size="mini"
              :disabled="disabled"
            ></el-input-number>
          </span>
        </div>
      </el-form-item>

<!--      <el-form-item label="鼠标与鼠标轨迹" v-if="rowDataOts.buttonType === 'MOUSE_AND_MOUSE_TRACK_22'">-->
<!--        <div class="img-outs" v-show="dialogImageUrl" :style="{backgroundImage: `url(${urlRoot}${dialogImageUrl})` }">-->
<!--          <div class="hover-ot" v-if="!disabled">-->
<!--            <span class="item-preview" @click="handlePictureCardPreview(dialogImageUrl)">-->
<!--              <i class="el-icon-zoom-in" style="font-size: 20px;color: #FFF;"></i>-->
<!--            </span>-->
<!--            <span class="item-preview" @click="handleRemove">-->
<!--              <i class="el-icon-delete" style="font-size: 20px;color: #FFF;"></i>-->
<!--            </span>-->
<!--          </div>-->
<!--        </div>-->
<!--        <el-upload-->
<!--          v-show="!dialogImageUrl && !disabled"-->
<!--          class="upload-add-txt"-->
<!--          :action="`${rootPath}file/fileUpload`"-->
<!--          :headers="{-->
<!--            Authorization: getToken(),-->
<!--          }"-->
<!--          :http-request="uploadFile"-->
<!--          :auto-upload="true"-->
<!--          list-type="picture-card"-->
<!--          :limit="1"-->
<!--          accept="image/jpg,image/jpeg,image/png,image/gif,image/bmp"-->
<!--        >-->
<!--          <i class="el-icon-plus"></i>-->
<!--        </el-upload>-->
<!--      </el-form-item>-->

      <template v-if="experimentType === 'WITH_FEEDBACK'">`
        <el-form-item label="正确答案" v-if="rowDataOts.buttonType !== 'MOUSE_AND_MOUSE_TRACK'">
          <el-input
              placeholder="请输入"
              :disabled="disabled"
              size="medium"
              :precision="0"
              v-model.trim="rowDataOts.correctAnswer">
          </el-input>
        </el-form-item>
        <el-form-item label="设置实验逻辑" v-if="experimentType === 'WITH_FEEDBACK'">
          <el-radio-group v-model="rowDataOts.experimentalLogicType" :disabled="disabled">
            <el-radio label="REACTION_TIME" border>根据反应时长来反馈</el-radio>
            <el-radio label="LAST_ANSWER" v-if="rowDataOts.buttonType !== 'MOUSE_AND_MOUSE_TRACK'" border>根据被试试次的回答来给反馈</el-radio>
          </el-radio-group>
        </el-form-item>
        <div class="dit-text-out" v-if="rowDataOts.experimentalLogicType === 'REACTION_TIME'">
          <el-form-item label="如果反应时长">
            <div>
              <span class="but-icon-type" :class="{'active-type': REACTION_TIME.value === false}" @click="butIconSwitch(false)"><i class="el-icon-arrow-left"></i></span>
              <span class="but-icon-type" :class="{'active-type': REACTION_TIME.value === true}" @click="butIconSwitch(true)"><i class="el-icon-arrow-right"></i></span>
              <el-input
                placeholder="反应时长(毫秒)"
                type="number"
                :disabled="disabled"
                size="medium"
                onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"
                v-model.trim="REACTION_TIME.timeCost"
                style="width: 278px;vertical-align: top;margin-top: 16px;"
              >
              </el-input>
            </div>
            <el-radio-group v-model="REACTION_TIME.type" :disabled="disabled">
              <el-radio label="TEXT" border>文字</el-radio>
              <el-radio label="IMG" border>图片</el-radio>
              <el-radio label="AUDIO_AND_VIDEO" border>音视频</el-radio>
            </el-radio-group>
            <div style="margin-top: 10px"></div>
            <el-input
              placeholder="请输入"
              :disabled="disabled"
              size="medium"
              v-if="REACTION_TIME.type === 'TEXT'"
              v-model.trim="REACTION_TIME.text"
            >
            </el-input>
            <template v-if="REACTION_TIME.type === 'IMG'">
              <updateImg :urls.sync="REACTION_TIME.imgUrl" :disabled="disabled"></updateImg>
            </template>
            <template v-if="REACTION_TIME.type === 'AUDIO_AND_VIDEO'">
              <updateAudioAndVideo :link-url.sync="REACTION_TIME.audioAndVideoUrl" :disabled="disabled"></updateAudioAndVideo>
            </template>
          </el-form-item>
          <div style="border-top: 1px solid #dddddd;margin: 10px 0;height: 1px"></div>
          <el-form-item label="否则">
            <el-radio-group v-model="REACTION_TIME.type2" :disabled="disabled">
              <el-radio label="TEXT" border>文字</el-radio>
              <el-radio label="IMG" border>图片</el-radio>
              <el-radio label="AUDIO_AND_VIDEO" border>音视频</el-radio>
            </el-radio-group>
            <div style="margin-top: 10px"></div>
            <el-input
              placeholder="请输入"
              :disabled="disabled"
              size="medium"
              v-if="REACTION_TIME.type2 === 'TEXT'"
              v-model.trim="REACTION_TIME.text2"
            >
            </el-input>
            <template v-if="REACTION_TIME.type2 === 'IMG'">
              <updateImg :urls.sync="REACTION_TIME.imgUrl2" :disabled="disabled"></updateImg>
            </template>
            <template v-if="REACTION_TIME.type2 === 'AUDIO_AND_VIDEO'">
              <updateAudioAndVideo :link-url.sync="REACTION_TIME.audioAndVideoUrl2" :disabled="disabled"></updateAudioAndVideo>
            </template>
          </el-form-item>
        </div>
        <div class="dit-text-out" v-if="rowDataOts.experimentalLogicType === 'LAST_ANSWER'">
          <div>
            <span style="font-weight: bold;">如果本次被试试次的回答=</span>
            <el-input
              placeholder="请输入"
              :disabled="disabled"
              size="medium"
              v-model.trim="LAST_ANSWER.answer"
              style="width: 240px;margin-left: 10px"
            >
            </el-input>
          </div>
          <div style="padding-left: 130px;margin-top: 10px;">
            <el-radio-group v-model="LAST_ANSWER.type" :disabled="disabled">
              <el-radio label="TEXT" border>文字</el-radio>
              <el-radio label="IMG" border>图片</el-radio>
              <el-radio label="AUDIO_AND_VIDEO" border>音视频</el-radio>
            </el-radio-group>
            <div style="margin-top: 10px"></div>
            <el-input
              placeholder="请输入"
              :disabled="disabled"
              size="medium"
              v-if="LAST_ANSWER.type === 'TEXT'"
              v-model.trim="LAST_ANSWER.text"
            >
            </el-input>
          </div>
          <template v-if="LAST_ANSWER.type === 'IMG'">
            <updateImg :urls.sync="LAST_ANSWER.imgUrl" :disabled="disabled"></updateImg>
          </template>
          <template v-if="LAST_ANSWER.type === 'AUDIO_AND_VIDEO'">
            <updateAudioAndVideo :link-url.sync="LAST_ANSWER.audioAndVideoUrl" :disabled="disabled"></updateAudioAndVideo>
          </template>
          <div style="border-top: 1px solid #dddddd;margin: 10px 0;height: 1px"></div>
          <el-form-item label="否则">
            <el-radio-group v-model="LAST_ANSWER.type2" :disabled="disabled">
              <el-radio label="TEXT" border>文字</el-radio>
              <el-radio label="IMG" border>图片</el-radio>
              <el-radio label="AUDIO_AND_VIDEO" border>音视频</el-radio>
            </el-radio-group>
            <div style="margin-top: 10px"></div>
            <el-input
              placeholder="请输入"
              :disabled="disabled"
              size="medium"
              v-if="LAST_ANSWER.type2 === 'TEXT'"
              v-model.trim="LAST_ANSWER.text2"
            >
            </el-input>
            <template v-if="LAST_ANSWER.type2 === 'IMG'">
              <updateImg :urls.sync="LAST_ANSWER.imgUrl2" :disabled="disabled"></updateImg>
            </template>
            <template v-if="LAST_ANSWER.type2 === 'AUDIO_AND_VIDEO'">
              <updateAudioAndVideo :link-url.sync="LAST_ANSWER.audioAndVideoUrl2" :disabled="disabled"></updateAudioAndVideo>
            </template>
          </el-form-item>
        </div>
      </template>
    </el-form>
    <div class="dialog-footer" style="text-align: right;margin-top: 10px">
      <el-button size="medium" @click="$emit('close-change')">取 消</el-button>
      <el-button size="medium" type="primary" @click="rowDataChange">确 定</el-button>
    </div>

    <el-dialog :visible.sync="dialogVisible2" :modal-append-to-body="false">
      <img width="100%" :src="dialogImageUrlPreview" alt="">
    </el-dialog>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>
