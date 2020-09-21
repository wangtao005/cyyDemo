import getMyDataList from "@/api/getMyDataListByPage";
import {MessageBox} from "element-ui";
import exportData from "@/api/myData";
import singleListData from "@/api/singleListData";
import {export_json_to_excel} from "@/utils/Export2Excel";
export default {
  name: "answerRecord",
  data() {
    return {
      id: '',
      listData: [

      ],
      pageNo: 0,
      pageSize: 20,
      pageSizes: [20, 30, 40],
      totalCount: 0,
      loading: false,
      experimentName: '',
      elt: [],
      ids: ''
    }
  },
  created() {
    // console.log(this.$route.params)
    let { id } = this.$route.params
    let { experimentName } = this.$route.query
    this.id = id
    this.experimentName = experimentName
    this.getData()
  },
  methods: {
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
      this.getData()
    },
    currentChange: function(item) {
      this.totalPage = item - 1;
      this.loading = true
      this.getData()
    },
    getRowKeys(row) {
      return row.id;
    },
    handleSelectionChange(elt) {
      this.elt = elt
    },
    exportExcel() {
      MessageBox.prompt('请输入要导出的数量，如为空则为导出全部', '导出', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValidator(value) {
          let boolean = new RegExp('^[1-9][1-9]*$').test(value)
          if (value == null || !value) {
            boolean = true
          }
          return boolean;
        },
        inputErrorMessage: '请输入大于零的正整数',
        inputPlaceholder: '请输入条数'
      }).then(({ value }) => {
        exportData({
          feedbackId: this.id,
          size: value
        }).then(res => {
          if (res.code === 200) {
            let {tHeader, filterVal, list} = res.data
            const data = this.formatJson(filterVal, list)
            export_json_to_excel(tHeader, data, this.experimentName)
          }
        })
      })
    },
    exportSingle() {
      if (!this.elt.length) {
        alert('请选择数据')
        return
      }
      let ids = []
      this.elt.forEach(item => {
        ids.push(item.id)
      })
      this.ids = ids.toString()
      let out = setTimeout(() => {
        clearTimeout(out)
        this.$refs.exportSi.click()
      }, 20)
      // singleListData({
      //   ids: ids.toString()
      // }).then(res => {
      //   this.download(res, '个人详情.zip')
      // })
    },
    download(content, fileName) {
      const blob = new Blob([content],{
        type: "application/zip"
      });
      const a = document.createElement("a");
      const url = window.URL.createObjectURL(blob);
      const filename = fileName;
      a.href = url;
      a.download = filename;
      console.log(a.href)
      a.click();
      window.URL.revokeObjectURL(url);
    },
    getData() {
      this.loading = true
      getMyDataList({
        feedbackId: this.id,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
      }).then(res => {
        this.loading = false
        if (res.code === 200) {
          this.listData = res.data
          this.totalCount = res.page.totalCount
        }
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map( v => filterVal.map( j => v[j]) )
    }
  }
}