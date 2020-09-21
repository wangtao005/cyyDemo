import {getToken} from "@/utils/auth";
import boot from "@/utils/boot";
import uploadFile from "@/api/fileUpload";
import { typeButtonDataDefault } from '@/utils/defaultJson.js'
import { urlRoot } from "@/config/imgUrlRoot";
export default {
  name: "addRow",
  inject: ['experimentTypeTxt'],
  props: {
    rowData: {
      type: Object,
      default() {
        return {}
      }
    },
    disabled: {
      type: Boolean,
      default: false
    },
    stimulateType: {
      type: String,
      default: 'TEXT'
    }
  },
  data() {
    return {
      rootPath: boot.rootPath,
      urlRoot: urlRoot,
      rowDataOts: {},
      buttonContentSliderValue: 0,
      fileList: [
        {name: 'name', url: 'url'}
      ],
      dialogImageUrl: '',
      dialogImageUrlPreview: '',
      dialogVisible2: false,
      loading: false,
      experimentType: 'NO_FEEDBACK',
      REACTION_TIME: {
        value: false,
        timeCost: 4000,
        type: 'TEXT',
        text: '',
        url: '',
        type2: 'TEXT',
        text2: '',
        url2: '',
      },
      LAST_ANSWER: {
        answer: '',
        type: 'TEXT',
        text: '',
        url: '',
        type2: 'TEXT',
        text2: '',
        url2: '',
      },
      isUpdateImgAndVideo: true
    }
  },
  created() {
    this.rowDataOts = {}
    this.rowDataOts = JSON.parse(JSON.stringify(this.rowData))
    if (this.rowDataOts.stimulateContentUrl === '' && this.stimulateType === 'IMG') {
      this.rowDataOts.stimulateContentUrl = []
    } else if (this.rowDataOts.stimulateContentUrl !== '' && this.stimulateType === 'IMG') {
      this.rowDataOts.stimulateContentUrl = JSON.parse(this.rowDataOts.stimulateContentUrl)
    }
    if (this.rowDataOts.buttonContent && this[this.rowDataOts.buttonType]) {
      this[this.rowDataOts.buttonType](this.rowDataOts.buttonContent)
    } else {
      this.setDataInt('buttonContentOrbit')
    }
    this.experimentType = this.experimentTypeTxt
    if (this.experimentType === 'WITH_FEEDBACK' && this.rowDataOts.buttonType !== 'MOUSE_AND_MOUSE_TRACK' && this.rowDataOts.experimentalLogicContent !== '') {
      this[this.rowDataOts.experimentalLogicType] = JSON.parse(this.rowDataOts.experimentalLogicContent)
    }
  },
  methods: {
    getToken: getToken,
    BUTTON_WITH_OPTIONS(stringData) {
      this.rowDataOts.buttonContentButton = JSON.parse(stringData)
      this.setDataInt('buttonContentButton')
    },
    KEYBOARD(stringData) {
      this.rowDataOts.buttonContentKeyUp = JSON.parse(stringData)
      this.setDataInt('buttonContentKeyUp')
    },
    SLIDER(stringData) {
      this.rowDataOts.buttonContentSlider = JSON.parse(stringData)
      this.rowDataOts.buttonContentSlider.min = +this.rowDataOts.buttonContentSlider.min
      this.rowDataOts.buttonContentSlider.max = +this.rowDataOts.buttonContentSlider.max
      console.log(stringData)
      this.setDataInt('buttonContentSlider')
    },
    MOUSE_AND_MOUSE_TRACK(stringData) {
      this.rowDataOts.buttonContentOrbit = JSON.parse(stringData)
      this.dialogImageUrl = this.rowDataOts.buttonContentOrbit.path
      this.setDataInt('buttonContentOrbit')
    },
    setUpdateImgAndVideo() {
      if (!this.isUpdateImgAndVideo) return
      this.$refs.typeInputUpload.click()
    },
    setDataInt(type) {
      for (let key in typeButtonDataDefault) {
        if (type !== key) {
          this.rowDataOts[key] = typeButtonDataDefault[key]
        }
      }
    },
    addRow(index, dataList) {
      let objData = {}
      switch (this.rowDataOts.buttonType) {
        case 'BUTTON_WITH_OPTIONS': // 按钮
          objData = {
            name: '选项' + (dataList.length + 1),
          }
          break;
        case 'KEYBOARD': // 键盘
          objData = {
            name: '',
            keyUp: '',
          }
          break;
      }
      dataList.splice(index + 1, 0, objData);
    },
    deleteRow(index, dataList) {
      if (dataList.length > 1) {
        dataList.splice(index, 1);
      }
    },
    uploadFile(event) {
      let sizeFile = Math.round(event.file.size / 1024 / 1024 * 100 ) / 100;
      if (sizeFile > 100) {
        this.$message.error('请上传100MB以内的视频文件');
        return
      }
      //创建 formData 对象
      let formData = new FormData();
      // 向 formData 对象中添加文件
      formData.append('file', event.file);
      uploadFile(formData).then(res => {
        this.dialogImageUrl = res.path
        this.rowDataOts.buttonContentOrbit = res
      }).catch(error => {
        alert(error)
      })
    },
    uploadFileImgVide(event) {
      let sizeFile = Math.round(event.target.files[0].size / 1024 / 1024 * 100 ) / 100;
      if (sizeFile > 100) {
        this.$message.error('请上传100MB以内的视频文件');
        return
      }
      //创建 formData 对象
      let formData = new FormData();
      // 向 formData 对象中添加文件
      formData.append('file', event.target.files[0]);
      this.loading = true
      this.isUpdateImgAndVideo = false
      uploadFile(formData).then(res => {
        this.loading = false
        this.isUpdateImgAndVideo = true
        if (res.status) {
          if (this.stimulateType === 'IMG') {
            this.rowDataOts.stimulateContentUrl.push(res)
          } else {
            this.rowDataOts.stimulateContentUrl = res.path
          }
        }
      }).catch(error => {
        this.loading = false
        alert(error)
      })
    },
    handlePictureCardPreview(imgUrl) {
      this.dialogImageUrlPreview = imgUrl
      this.dialogVisible2 = true;
    },
    handlePictureCardPreviewDelete(index) {
      this.rowDataOts.stimulateContentUrl.splice(index, 1)
    },
    handleRemove() {
      this.rowDataOts.dialogImageUrl = ''
      this.rowDataOts.buttonContentOrbit = {}
    },
    uploadFileImgVideFeedback(event) {
      //创建 formData 对象
      let formData = new FormData();
      // 向 formData 对象中添加文件
      formData.append('file', event.target.files[0]);
      let sizeFile = Math.round(event.target.files[0].size / 1024 / 1024 * 100 ) / 100;
      if (sizeFile > 100) {
        this.$message.error('请上传100MB以内的视频文件');
        return
      }
      this.loading = true
      if (!this.isUpdateImgAndVideo) return
      this.isUpdateImgAndVideo = false
      uploadFile(formData).then(res => {
        this.isUpdateImgAndVideo = true
        if (res.status) {
          this.loading = false
          this[this.rowDataOts.experimentalLogicType].url =  res.path
        }
      }).catch(error => {
        this.loading = false
        alert(error)
      })
    },
    uploadFileImgVideFeedback2(event) {
      //创建 formData 对象
      let formData = new FormData();
      // 向 formData 对象中添加文件
      if (!this.isUpdateImgAndVideo) return
      this.isUpdateImgAndVideo = false
      let sizeFile = Math.round(event.target.files[0].size / 1024 / 1024 * 100 ) / 100;
      if (sizeFile > 100) {
        this.$message.error('请上传100MB以内的视频文件');
        return
      }
      formData.append('file', event.target.files[0]);
      this.loading = true
      uploadFile(formData).then(res => {
        this.isUpdateImgAndVideo = true
        if (res.status) {
          this.loading = false
          this[this.rowDataOts.experimentalLogicType].url2 =  res.path
        }
      }).catch(error => {
        this.loading = false
        alert(error)
      })
    },
    butIconSwitch(bun) {
      if (this.disabled) {
        return
      }
      this[this.rowDataOts.experimentalLogicType].value = bun
    },
    rowDataChange() {
      if (this.disabled) {
        this.$emit('close-change')
        return;
      }
      console.log(!this.rowDataOts.specifyTheReactionTime)
      if (!+this.rowDataOts.specifyTheReactionTime && this.experimentType === 'WITH_FEEDBACK') {
        alert('反应时间不能为0或者为空')
        return;
      }
      switch (this.rowDataOts.buttonType) {
        case 'BUTTON_WITH_OPTIONS':
          this.rowDataOts.buttonContent = JSON.stringify(this.rowDataOts.buttonContentButton)
          break;
        case 'KEYBOARD':
          this.rowDataOts.buttonContent = JSON.stringify(this.rowDataOts.buttonContentKeyUp)
          break;
        case 'SLIDER':
          this.rowDataOts.buttonContent = JSON.stringify(this.rowDataOts.buttonContentSlider)
          break;
        case 'MOUSE_AND_MOUSE_TRACK':
          this.rowDataOts.buttonContent = JSON.stringify(this.rowDataOts.buttonContentOrbit)
          break;
        default:
          this.rowDataOts.buttonContent = ''
          break;
      }
      if (this.experimentType === 'WITH_FEEDBACK' && this.rowDataOts.buttonType !== 'MOUSE_AND_MOUSE_TRACK') {
        this.experimentalLogicContent = JSON.stringify(this[this.rowDataOts.experimentalLogicType])
        this.rowDataOts.experimentalLogicContent = this.experimentalLogicContent
      }
      if (this.stimulateType === 'IMG') {
        this.rowDataOts.stimulateContentUrl = JSON.stringify(this.rowDataOts.stimulateContentUrl)
      }
      this.$emit('row-data-change', {
        type: '',
        data: this.rowDataOts
      })
    }
  },
  watch: {
    rowData: {
      handler(val) {

      },
      deep: true
    }
  }
}
