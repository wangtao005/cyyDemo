<template>
  <div id="with-feedback-list">
    <el-table
      :data="dataList"
      v-loading="loading"
      :header-cell-style="{background:'#d4d9e2', color:'#606266'}"
      border
      height="100%"
    >
      <el-table-column
        type="index"
        align="center"
        label="序号"
        :index="indexMethod"
        width="80">
      </el-table-column>
      <el-table-column prop="experimentName" label="实验名称" width="auto" header-align="left" align="left" show-overflow-tooltip></el-table-column>
      <el-table-column prop="itemName" label="指导语呈现时长(毫秒)" width="180" header-align="center" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.instructionType === 'FIXED_DURATION'">{{scope.row.instructionDuration}}</span>
          <span v-else>等待被用户看完</span>
        </template>
      </el-table-column>
<!--      <el-table-column prop="stimulateType" label="刺激类型" width="160" header-align="center" align="center" show-overflow-tooltip>-->
<!--        <template slot-scope="scope">{{stimulateTypeName(scope.row.stimulateType)}}</template>-->
<!--      </el-table-column>-->
      <el-table-column prop="conclusionType" label="结束语呈现时长(毫秒)" width="180" header-align="center" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.conclusionType === 'FIXED_DURATION'">{{scope.row.conclusionDuration}}</span>
          <span v-else>等待被用户看完</span>
        </template>
      </el-table-column>
<!--      <el-table-column prop="specifyTheReactionTime" label="反应时间" header-align="center" align="center" width="180" show-overflow-tooltip></el-table-column>-->
<!--      <el-table-column prop="correctAnswer" label="正确答案" header-align="center" align="center" width="180" show-overflow-tooltip></el-table-column>-->
      <el-table-column prop="createDate" label="创建时间" width="180" header-align="center" align="center" show-overflow-tooltip></el-table-column>
      <el-table-column label="操作" width="440" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button-group size="mini">
            <el-button type="primary" size="mini" @click="copyExperiment(scope.row)">复制实验</el-button>
            <el-button type="primary" size="mini" @click="examineFeedback(scope.row)">查看</el-button>
            <el-button type="primary" size="mini" @click="editFeedback(scope.row)" v-if="!scope.row.locking">编辑</el-button>
            <el-button type="danger" size="mini" @click="deleteFeedback(scope.row)">删除</el-button>
            <el-button type="primary" size="mini" @click="copyText(scope.row)">复制链接</el-button>
            <el-button type="primary" size="mini" @click="exportExcel(scope.row)">答题记录</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="text-align: center;margin-top: 12px;"
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="pageNo + 1"
      :page-size="10"
      :page-sizes="pageSizes"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount">
    </el-pagination>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>