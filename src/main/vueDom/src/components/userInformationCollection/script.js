export default {
  name: "userInformationCollection",
  data() {
    return {
      userInformationCollectionData: {
        name: '',
        sex: '',
        age: '',
        yearsOfEducation: '',
        sexualOrientation: '',
        height: '',
        bodyWeight: ''
      }
    }
  },
  props: {
    participantBasicInfo: {
      type: Array,
      default() {
        return []
      }
    },
    fontSize: {
      type: Number,
      default: 14
    }
  },
  methods: {
    emitUpdate() {
      let keys = this.participantBasicInfo
      let ontLen = []
      keys.forEach(item => {
        if (this.userInformationCollectionData[item]) {
          ontLen.push(this.userInformationCollectionData[item])
        }
      })
      if (ontLen.length !== keys.length) { // 全部填写完成
        alert('请填写完整的用户信息！')
        return
      }
      this.$emit('endFillIn', this.userInformationCollectionData)
    }
  },
  watch: {
    // userInformationCollectionData: {
    //   handler(val) {
    //     let keys = this.participantBasicInfo
    //     let ontLen = []
    //     keys.forEach(item => {
    //       if (val[item]) {
    //         ontLen.push(val[item])
    //       }
    //     })
    //     if (ontLen.length === keys.length) { // 全部填写完成
    //       this.$emit('endFillIn', val)
    //     }
    //   },
    //   deep: true
    // }
  }
}