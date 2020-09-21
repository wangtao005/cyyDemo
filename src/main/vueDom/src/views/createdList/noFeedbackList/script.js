import tableTool from "@/mixins/tableTool";
import getListByPage from "@/api/getListByPage";
import delAndEdit from "@/api/delAndEdit";
import {MessageBox} from "element-ui";
export default {
  name: "noFeedbackList",
  mixins: [tableTool],
  data() {
    return {
      dataList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    examineFeedback(row) {
      this.$emit('examine-feedback', row.id)
    },
    editFeedback(row) {
      this.$emit('edit-feedback', row.id)
    },
    getList() {
      this.loading = true
      getListByPage({
        experimentType: 'NO_FEEDBACK',
        pageNo: this.pageNo,
        pageSize: this.pageSize,
      }).then(res => {
        this.loading = false
        if (res.code === 200) {
          this.dataList = res.data
          this.totalCount = res.page.totalCount
        }
      })
    },
  }
}
