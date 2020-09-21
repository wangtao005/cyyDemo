import { mapGetters } from 'vuex'
import { MessageBox, Message } from "element-ui";
import saveAndEdit from "@/api/saveAndEdit";
import topicInfo from "@/api/topicInfo";
import startInstruction from '@/components/startInstruction/startInstruction.vue'
import endInstruction from '@/components/endInstruction/endInstruction.vue'
import textEditor from '@/components/textEditor/textEditor.vue'
// import imgEditor from '@/components/imgEditor/imgEditor.vue'
// import audioVideoEditor from '@/components/audioVideoEditor/audioVideoEditor.vue'
import listDataEditor from '@/components/listDataEditor/listDataEditor.vue'
import stimulateImportExcel from "@/api/stimulateImportExcel";
import downloadExcel from "@/api/downloadExcel";
let loadingPut = null
export default {
  name: "noFeedback",
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data() {
    return {
      locking: false,
      experimentType: 'NO_FEEDBACK', // NO_FEEDBACK:无反馈实验;WITH_FEEDBACK:有反馈
      homeForm: {

      },
      stimulateType: 'TEXT', // 刺激类别(0:文字;1:图片;2:音视频)
      originalStimulateType: '',
      originalFeedbacks: [],
      textData: [],
      imgData: [],
      audioVideoData: [],
      listDataEditorData: [],
      endInstruction: {

      },
      childIds: [],
      yesOrNoViewProgressBar: 'NO', // 是否显示进度条
      screenColor: '#FFF', // 背景颜色
      fontColor: '#000', // 字体颜色
      fontSize: 14, // 字体大小
      participantBasicInfo: [], // 被试基本信息
      yesOrNoRandom: 'NO', // 题目是否随机出现
      yesOrNoShowFirstStimulate: 'NO', // 实验刺激呈现完之后再出现选项
      immediatelyOver: 'NO', // 反应设置
      fontSizeData: [

      ]
    }
  },
  props: {
    infoId: {
      type: String,
      default: ''
    },
    lockingOut: {
      type: Boolean,
      default: false
    },
    loading: {
      type: Boolean,
      default: false
    },
    experimentTypePop: {
      type: String,
      default: 'NO_FEEDBACK'
    }
  },
  created() {
    // console.log(this.name)
    // 设置字体默认值
    this.experimentType = this.experimentTypePop
    for (let i = 0; i < 60; i++) {
      if(i%2 && i > 0) { // 取偶数
        this.fontSizeData.push(
          {
            value: i + 1,
            label: `${i+1}px`
          }
        )
      }
    }
    if (this.infoId) { // 查看或者编辑
      this.locking = this.lockingOut
      loadingPut = this.$loading({
        lock: true,
        text: '页面加载中',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.getInfo()
    }
  },
  methods: {
    dowDownloadExcel() {
      downloadExcel().then(res => {
        this.download(res, 'exp_template.xls')
      })
    },
    download(content, fileName) {
      const blob = new Blob([content],{
        type: "application/vnd.ms-excel"
      });
      const a = document.createElement("a");
      const url = window.URL.createObjectURL(blob);
      const filename = fileName;
      a.href = url;
      a.download = filename;
      a.click();
      window.URL.revokeObjectURL(url);
    },
    uploadExcel(event) {
      // let sizeFile = Math.round(event.target.files[0].size / 1024 / 1024 * 100 ) / 100;
      // if (sizeFile > 100) {
      //   this.$message.error('请上传100MB以内的视频文件');
      //   return
      // }
      //创建 formData 对象
      let formData = new FormData();
      // 向 formData 对象中添加文件
      formData.append('file', event.target.files[0]);
      formData.append('type', this.stimulateType);
      formData.append('experimentType', this.experimentType);
      let loadingPut = this.$loading({
        lock: true,
        text: '上传中',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      stimulateImportExcel(formData). then(res => {
        loadingPut.close()
        this.$refs.uploadExcel.value = ''
        if (res.status) {
          this.$refs.listDataEditor.setListData(res.data)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    getInfo() {
      topicInfo({
        id: this.infoId
      }).then(res => {
        loadingPut.close()
        if (res.code === 200) {
          let {experimentName, whetherFullScreen, instructionType, instruction, instructionDuration, feedbackExperimentStimulate} = res.data
          let {conclusion, conclusionType, conclusionDuration, experimentType} = res.data
          let { stimulateType } = res.data
          let { yesOrNoViewProgressBar, screenColor, fontColor, fontSize, participantBasicInfo, immediatelyOver, yesOrNoRandom, yesOrNoShowFirstStimulate } = res.data
          // this.experimentType = experimentType
          this.stimulateType = stimulateType
          this.updateStimulateType = stimulateType
          this.originalFeedbacks = feedbackExperimentStimulate
          this.yesOrNoViewProgressBar = yesOrNoViewProgressBar
          this.screenColor = screenColor
          this.fontColor = fontColor
          this.fontSize = fontSize
          this.immediatelyOver = immediatelyOver
          this.yesOrNoRandom = yesOrNoRandom
          this.yesOrNoShowFirstStimulate = yesOrNoShowFirstStimulate
          this.participantBasicInfo = participantBasicInfo ? participantBasicInfo.split(',') : []
          this.homeForm ={
            experimentName, // 实验名称
            whetherFullScreen, // 是否全屏(0:全屏;1:非全屏)
            instructionType, // 指导语类别(0:固定时长(毫秒);1:随机时间,时间段;2:等待用户)
            instruction, // 指导语
            instructionDuration
          }
          this.listDataEditorData = feedbackExperimentStimulate
          this.endInstruction = {
            conclusion, // 结束语
            conclusionType, //0:固定时长(毫秒);1:随机时间,时间段;2:等待用户
            conclusionDuration
          }
        }
      })
    },
    updateAjax() {
      let feedbackData = []
      if (this.updateStimulateType  !== this.stimulateType) { // 切换刺激类型后，删除原有刺激实验
        this.childIds = []
        this.originalFeedbacks.forEach(item => {
          this.childIds.push(item.id)
        })
      }
      feedbackData = this.listDataEditorData
      feedbackData.forEach((item, index) => {
        item.code = index
      })
      saveAndEdit({
        id: this.infoId, // 当id不为空时，即为编辑保存
        experimentType: this.experimentType,
        ...this.homeForm,
        // stimulateType: this.stimulateType,
        yesOrNoViewProgressBar: this.yesOrNoViewProgressBar, // 是否显示进度条
        screenColor: this.screenColor, // 背景颜色
        fontColor: this.fontColor, // 字体颜色
        fontSize: this.fontSize, // 字体大小
        participantBasicInfo: this.participantBasicInfo.toString(), // 被试基本信息
        yesOrNoRandom: this.yesOrNoRandom, // 题目是否随机出现
        yesOrNoShowFirstStimulate: this.yesOrNoShowFirstStimulate, // 实验刺激呈现完之后再出现选项
        immediatelyOver: this.immediatelyOver, // 反应设置
        feedbackExperimentStimulate: feedbackData,
        ...this.endInstruction,
        childIds: this.childIds.toString()
      }).then(res => {
        console.log(res)
        if (res.code === 200 && Object.prototype.toString.call(res.data) === "[object Object]") {
          MessageBox.confirm('保存成功,该题目在进行一次答题后将被锁定，无法再次编辑或删除', '成功', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            modalAppendToBody: false,
            type: 'warning'
          }).then(() => {
            // window.location.reload()
            this.$router.replace({
              name: 'Created',
            })
          }).catch(() => {
            // this.$message.error('保存失败');
          })
        } else {
          Message.error(res.desc)
        }
      })
    }
  },
  components: {
    startInstruction,
    endInstruction,
    textEditor,
    // imgEditor,
    // audioVideoEditor,
    listDataEditor
  },
  watch: {
    // homeForm: {
    //   handler(val) {
    //     console.log(val.editorTxt)
    //   },
    //   deep: true
    // }
  }
}
