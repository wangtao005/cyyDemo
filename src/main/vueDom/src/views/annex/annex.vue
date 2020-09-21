<template>
  <div class="annex">
    <div style="text-align: right;padding-bottom: 10px">
      <el-button type="primary" style="margin-right: 10px" @click="dialogVisible = true;batchName = ''">上传</el-button>
    </div>
    <el-table
      :data="listData"
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
        fixed="left"
        width="80">
      </el-table-column>
      <el-table-column prop="batchName" label="实验名称" width="400" header-align="center" align="center" show-overflow-tooltip></el-table-column>
      <el-table-column prop="fileName" label="文件名称" header-align="center" align="center" show-overflow-tooltip></el-table-column>
      <el-table-column prop="createDate" label="创建时间" width="160 " header-align="center" align="center" show-overflow-tooltip></el-table-column>
      <el-table-column label="操作" width="180" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button-group size="mini">
            <el-button type="primary" size="mini" @click="copyText(scope.row)">复制链接</el-button>
            <el-button type="danger" size="mini" @click="removeFile(scope.row)" v-if="!scope.row.locking">删除</el-button>
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
    <div style="display: none">
      <input ref="typeInputUpload" @change="uploadFile" type="file" accept=".zip,application/zip,image/*,video/*,audio/*"></input>
    </div>
    <el-dialog
        title="上传"
        :visible.sync="dialogVisible"
        width="30%"
    >
      <span>请输入实验名称<div style="margin-top: 10px"></div><el-input v-model.trim="batchName"></el-input></span>
      <el-button type="primary" style="margin: 10px 10px 0 0" @click="update">选择文件</el-button>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import script from './script.js'
  export default script
</script>

<style scoped lang="scss">
.annex{
  height: 100%;
  padding: 10px;
  .el-table{
    height: calc(100vh - 174px)!important;
  }
}
</style>