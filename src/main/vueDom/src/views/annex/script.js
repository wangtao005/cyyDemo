import cloudDiskSave from "@/api/cloudDiskSave";
import cloudDiskList from "@/api/cloudDiskList";
import cloudRemove from "@/api/cloudRemove";
import { urlRoot } from "@/config/imgUrlRoot";

export default {
  name: "annex",
  data() {
    return {
      listData: [],
      loading: false,
      pageNo: 0,
      pageSize: 20,
      pageSizes: [20, 30, 40],
      totalCount: 0,
      dialogVisible: false,
      batchName: '',
      loadingPut: null
    }
  },
  created() {
    this.getData()
  },
  methods: {
    copyText(row) {
      let message = `/${row.fileUrl}`;
      this.$copyText(message).then(res => {
        this.$message.success('复制成功')
      }).catch(err => {
        this.$message.error('复制失败');
      })
    },
    removeFile(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        cloudRemove({
          ...row
        }).then(res => {
          this.getData()
          if (res.code === 200) {
            this.$message({
              type: 'success',
              message: res.data
            });
          }
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    update() {
      this.$refs.typeInputUpload.click()
    },
    uploadFile(event) {
      //创建 formData 对象
      let formData = new FormData();
      // 向 formData 对象中添加文件
      formData.append('file', event.target.files[0]);
      formData.append('type', 1);
      formData.append('batchName', this.batchName);
      // let sizeFile = Math.round(event.target.files[0].size / 1024 / 1024 * 100 ) / 100;
      // if (sizeFile > 100) {
      //   this.$message.error('请上传100MB以内的视频文件');
      //   return
      // }
      this.loadingPut = this.$loading({
        lock: true,
        text: '文件上传中',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      cloudDiskSave(formData).then(res => {
        this.dialogVisible = false
        this.loadingPut.close()
        if (res.code !== 200) {
          this.$message.error(res.desc)
        }
        this.getData()
      })
    },
    indexMethod (index) {
      let num = 0;
      if (this.totalPage) {
        num = this.pageSize * this.totalPage + index + 1
      } else {
        num = index + 1;
      }
      return num
    },
    sizeChange: function(item) {
      this.totalPage = 0;
      this.pageSize = item;
      this.loading = true
    },
    currentChange: function(item) {
      this.totalPage = item - 1;
      this.loading = true
    },
    getData() {
      this.loading = true
      cloudDiskList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
      }).then(res => {
        this.loading = false
        if (res.code === 200) {
          this.listData = res.data
          this.totalCount = res.page.totalCount
        }
      })
    }
  }
}