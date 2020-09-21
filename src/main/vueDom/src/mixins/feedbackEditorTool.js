import addRow from "@/components/addRow/addRow.vue";
export default {
  components: {
    addRow
  },
  data() {
    return {
      loading: false,
      rowData: {},
      rowIndex: 0,
      dialogVisible: false,
      stimulateTypeName: {
        TEXT: '文字',
        IMG: '图片',
        AUDIO_AND_VIDEO: '音视频'
      }
    }
  },
  props: {
    nextData: {
      type: Array,
      default() {
        return []
      }
    },
    disabled: {
      type: Boolean,
      default: false
    },
    dels: {
      type: Array,
      default() {
        return []
      }
    },
    experimentType: {
      type: String,
      default: 'NO_FEEDBACK'
    }
  },
  provide() {
    return {
      experimentTypeTxt: this.experimentType
    }
  },
  created() {
    console.log(111, this.experimentType)
  },
  methods: {
    getStimulateTypeName(type) {
      return this.stimulateTypeName[type] || '-'
    },
    setListData(data) {
      this.dataList = data
    },
    addRow(index, dataList) {
      let data = {
        ...dataList[index]
      }
      data.id = ''
      data.createDate = ''
      data.modifyDate = ''
      dataList.splice(index + 1, 0, data);
    },
    deleteRow(index, dataList) {
      if (dataList.length > 1) {
        dataList.splice(index, 1);
      }
    },
    rowClick(index, dataList) {
      this.rowData = dataList[index]
      this.rowIndex = index
      this.dialogVisible = true
    },
    rowDataChange({data}) {
      this.dialogVisible = false
      this.$set(this.dataList, this.rowIndex, data)
    },
  },
  watch: {
    dataList: {
      handler(val) {
        this.$emit('update:next-data', val)
      },
      deep: true
    },
    nextData: {
      handler(val) {
        this.dataList = val
      },
      deep: true
    }
  }
}
