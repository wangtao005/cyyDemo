<template>
  <div id="list-data-editor">
    <el-table
        :data="dataList"
        v-loading="loading"
        :header-cell-style="{background:'#eef1f6',color:'#606266'}"
        border
    >
      <el-table-column
          type="index"
          align="center"
          label="序号"
          width="80">
      </el-table-column>
      <el-table-column prop="stimulateContentName" label="刺激类型" header-align="center" align="center" width="180" show-overflow-tooltip>
        <template slot-scope="scope">{{getStimulateTypeName(scope.row.stimulateType)}}</template>
      </el-table-column>
      <el-table-column prop="stimulateContentName" label="实验刺激" header-align="center" align="left" width="180" show-overflow-tooltip>
        <template slot-scope="scope">
          <template v-if="scope.row.stimulateType === 'TEXT'">{{scope.row.stimulateContentName}}</template>
          <template v-else-if="scope.row.stimulateType === 'IMG'">
            <template v-for="item in JSON.parse(scope.row.stimulateContentUrl || '[]')">{{item.path}},</template>
          </template>
          <template v-else-if="scope.row.stimulateType === 'AUDIO_AND_VIDEO'">{{scope.row.stimulateContentUrl}}</template>
        </template>
      </el-table-column>
      <el-table-column prop="trialName" label="实验名称" header-align="center" align="center" show-overflow-tooltip min-width="180"></el-table-column>
      <el-table-column prop="presentDuration" label="呈现时长" header-align="center" align="center" width="180" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.presentType === 'FIXED_DURATION'">{{scope.row.presentDuration}}</span>
          <span v-else>{{scope.row.leastDuration}}~{{scope.row.longestDuration}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="specifyTheReactionTime" label="设置规定的反应时间" header-align="center" align="center" width="180" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.reactionPresentType === 'FIXED_DURATION'">{{scope.row.specifyTheReactionTime}}</span>
          <span v-else-if="scope.row.reactionPresentType === 'PERIOD'">{{scope.row.reactionLeastDuration}}~{{scope.row.reactionLongestDuration}}</span>
          <span v-else>~</span>
        </template>
      </el-table-column>
      <template v-if="experimentType === 'WITH_FEEDBACK'">
        <el-table-column prop="correctAnswer" label="设置正确答案" header-align="center" align="center" width="180" show-overflow-tooltip></el-table-column>
      </template>
      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" style="vertical-align: top;margin-right: 6px" @click="rowClick(scope.$index, dataList)">{{disabled ? '查看' : '编辑'}}</el-button>
          <el-button-group>
            <el-button type="primary" size="mini" icon="el-icon-plus" :disabled="disabled" @click="addRow(scope.$index, dataList)"></el-button>
            <el-button type="primary" size="mini" icon="el-icon-minus" :disabled="disabled" @click="deleteRow(scope.$index, dataList)"></el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <div class="dialog-webkit" v-if="dialogVisible">
      <el-dialog
        title="编辑"
        :visible.sync="dialogVisible"
        width="800px"
        :modal-append-to-body="false">
        <addRow :row-data="rowData" :disabled="disabled" @row-data-change="rowDataChange" @close-change="dialogVisible = false" />
        <span slot="footer" class="dialog-footer" style="display: none">

        </span>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import script from './script.js';
export default script
</script>

<style scoped lang="scss">
#list-data-editor{

}
</style>