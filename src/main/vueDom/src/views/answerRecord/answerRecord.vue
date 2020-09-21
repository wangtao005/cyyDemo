<template>
  <div id="answer-record">
    <div style="text-align: right;padding-bottom: 10px">
      <el-button type="primary" style="margin-right: 10px" @click="exportSingle">详细被试数据导出</el-button>
      <el-button type="primary" style="margin-right: 10px" @click="exportExcel">导出</el-button>
      <a style="display: none" ref="exportSi" :href="`/my/data/single/listDate?ids=${ids}`" download="详细被试数据.zip"></a>
    </div>
    <el-table
      :data="listData"
      v-loading="loading"
      :header-cell-style="{background:'#d4d9e2', color:'#606266', height: 48}"
      :header-row-style="{height: 48}"
      border
      height="100%"
      :row-key="getRowKeys"
      @selection-change="handleSelectionChange"
    >
      <template v-if="listData.length">
        <el-table-column
          type="selection"
          align="center"
          :reserve-selection="true"
          width="55">
        </el-table-column>
        <el-table-column
          type="index"
          align="center"
          label="序号"
          :index="indexMethod"
          fixed="left"
          width="80">
        </el-table-column>
        <el-table-column prop="createDate" label="提交答卷时间" width="160" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="spentTime" label="所用时间" width="160" header-align="center" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{`${scope.row.spentTime / 1000}秒`}}
          </template>
        </el-table-column>
        <el-table-column prop="source" label="来源" width="160" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="sourceInfo" label="来自" width="200" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="name" label="姓名" width="100" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="sex" label="性别" width="100" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="age" label="年龄" width="100" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="yearsOfEducation" label="受教育年限" width="100" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="sexualOrientation" label="性取向" width="220" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="height" label="身高" width="100" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="bodyWeight" label="体重" width="100" header-align="center" align="center" show-overflow-tooltip></el-table-column>
        <template  v-for="(item, index) in listData[0].answerContents">
          <el-table-column prop="answer" :label="item.topic" width="300" :key="item.id" header-align="center" align="center" show-overflow-tooltip>
            <template slot-scope="scope">{{listData[scope.$index].answerContents[index].answer}}</template>
          </el-table-column>
        </template>
      </template>
<!--      <el-table-column label="操作" width="120" align="center" fixed="right">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-group size="mini">-->
<!--            <el-button type="primary" size="mini" @click="exportExcel(scope.row)">导出</el-button>-->
<!--          </el-button-group>-->
<!--        </template>-->
<!--      </el-table-column>-->
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
#answer-record{
  height: 100%;
  padding: 10px;
  .el-table{
    height: calc(100vh - 174px)!important;
  }
}
</style>