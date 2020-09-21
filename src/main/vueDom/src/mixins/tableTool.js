import {export_json_to_excel} from '@/utils/Export2Excel'
import exportData from "@/api/myData";
import { MessageBox } from "element-ui";
import delAndEdit from "@/api/delAndEdit";
import saveAndEdit from "@/api/saveAndEdit";

export default {
  data() {
    return {
      pageNo: 0,
      pageSize: 20,
      pageSizes: [20, 30, 40],
      totalCount: 0,
      loading: false,
      stimulateTypeNameData: {
        'TEXT': '文字',
        'IMG': '图片',
        'AUDIO_AND_VIDEO': '音视频',
      }
    }
  },
  methods: {
    copyText(row) {
      let message = `${window.location.origin}/#/answer/${row.id}`;
      // this.$router.push({
      //   path: '/answer',
      //   params: {
      //     id: row.id
      //   }
      // })
      this.$copyText(message).then(res => {
        this.$message.success('复制成功')
      }).catch(err => {
        this.$message.error('复制失败');
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
    stimulateTypeName(type) {
      return this.stimulateTypeNameData[type] || '-'
    },
    copyExperiment(row) {
      let data = {...row}
      data.id = ''
      data.feedbackExperimentStimulate.forEach(item => {
        item.id = ''
      })
      saveAndEdit({
        ...data
      }).then(res => {
        if (res.code === 200) {
          this.$emit('edit-feedback', res.data.id)
        }
      })
    },
    deleteFeedback(row) {
      MessageBox.confirm('确认删除该条试验?删除后该实验下的所有答题记录都将被删除!', '成功', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        modalAppendToBody: false,
        type: 'warning'
      }).then(() => {
        delAndEdit({
          id: row.id
        }).then(res => {
          if (res.code === 200) {
            this.$message.success('删除成功')
            this.getList()
          }
        })
      }).catch(() => {
        // this.$message.error('保存失败');
      })
    },
    exportExcel(row) {
      this.$router.push({
        name: 'answerRecord',
        params: {
          id: row.id
        },
        query: {
          experimentName: row.experimentName
        }
      })
    }
  }
}
