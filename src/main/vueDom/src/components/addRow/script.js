import {getToken} from "@/utils/auth";
import boot from "@/utils/boot";
import uploadFile from "@/api/fileUpload";
import { typeButtonDataDefault } from '@/utils/defaultJson.js'
import { urlRoot } from "@/config/imgUrlRoot";
import updateImg from "@/components/updateImg/updateImg";
import updateAudioAndVideo from "@/components/updateAudioAndVideo/updateAudioAndVideo";
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
  },
  components: {
    updateImg,
    updateAudioAndVideo
  },
  data() {
    return {
      rootPath: boot.rootPath,
      urlRoot: urlRoot,
      stimulateType: 'TEXT', // 刺激类别(0:文字;1:图片;2:音视频)
      rowDataOts: {},
      buttonContentSliderValue: 0,
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
        imgUrl: [],
        audioAndVideoUrl: '',
        type2: 'TEXT',
        text2: '',
        imgUrl2: [],
        audioAndVideoUrl2: '',
      },
      LAST_ANSWER: {
        answer: '',
        type: 'TEXT',
        text: '',
        imgUrl: [],
        audioAndVideoUrl: '',
        type2: 'TEXT',
        text2: '',
        imgUrl2: [],
        audioAndVideoUrl2: '',
      },
      isUpdateImgAndVideo: true,
      IMG_StimulateContentUrl: [],
      AUDIO_AND_VIDEO_StimulateContentUrl: ''
    }
  },
  computed: {

  },
  created() {
    this.rowDataOts = {}
    this.rowDataOts = JSON.parse(JSON.stringify(this.rowData))
    console.log(this.rowDataOts)
    if (this.rowDataOts.stimulateContentUrl === '' && this.rowDataOts.stimulateType === 'IMG') {
      this.rowDataOts.stimulateContentUrl = []
      this.IMG_StimulateContentUrl = []
    } else if (this.rowDataOts.stimulateContentUrl !== '' && this.rowDataOts.stimulateType === 'IMG') {
      this.rowDataOts.stimulateContentUrl = JSON.parse(this.rowDataOts.stimulateContentUrl)
      this.IMG_StimulateContentUrl = [...this.rowDataOts.stimulateContentUrl]
    }
    if (this.rowDataOts.buttonContent && this[this.rowDataOts.buttonType]) {
      this[this.rowDataOts.buttonType](this.rowDataOts.buttonContent)
    } else {
      this.setDataInt('buttonContentOrbit')
    }
    this.experimentType = this.experimentTypeTxt
    if (this.experimentType === 'WITH_FEEDBACK' && this.rowDataOts.experimentalLogicContent !== '') {
      // this[this.rowDataOts.experimentalLogicType] = JSON.parse(this.rowDataOts.experimentalLogicContent)
      let cetObj = {...this[this.rowDataOts.experimentalLogicType]}
      let obj = JSON.parse(this.rowDataOts.experimentalLogicContent)
      if (Object.prototype.toString.call(obj.imgUrl) !== "[object Array]") {
        obj.imgUrl = JSON.parse(obj.imgUrl || '[]')
      }
      if (Object.prototype.toString.call(obj.imgUrl2) !== "[object Array]") {
        obj.imgUrl2 = JSON.parse(obj.imgUrl2 || '[]')
      }
      this[this.rowDataOts.experimentalLogicType] = Object.assign(cetObj, obj)
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
    handlePictureCardPreview(imgUrl) {
      this.dialogImageUrlPreview = imgUrl
      this.dialogVisible2 = true;
    },
    handleRemove() {
      this.rowDataOts.dialogImageUrl = ''
      this.rowDataOts.buttonContentOrbit = {}
    },
    buttonTypeSwatch(item) {
      if (item === 'MOUSE_AND_MOUSE_TRACK') {
        this.rowDataOts.experimentalLogicType = 'REACTION_TIME'
      }
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
      // if (!+this.rowDataOts.specifyTheReactionTime && this.experimentType === 'WITH_FEEDBACK') {
      //   alert('反应时间不能为0或者为空')
      //   return;
      // }
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
      if (this.experimentType === 'WITH_FEEDBACK') {
        this.experimentalLogicContent = JSON.stringify(this[this.rowDataOts.experimentalLogicType])
        this.rowDataOts.experimentalLogicContent = this.experimentalLogicContent
      }
      if (this.rowDataOts.stimulateType === 'IMG') {
        this.rowDataOts.stimulateContentUrl = JSON.stringify(this.IMG_StimulateContentUrl)
      }
      if (this.rowDataOts.stimulateType === 'AUDIO_AND_VIDEO') {
        this.rowDataOts.stimulateContentUrl = this.AUDIO_AND_VIDEO_StimulateContentUrl
      }
      if (this.rowDataOts.stimulateType === 'TEXT') {
        this.rowDataOts.stimulateContentUrl = ''
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
    },
    IMG_StimulateContentUrl(val) {
      console.log('img', val)
    },
    AUDIO_AND_VIDEO_StimulateContentUrl(val) {
      console.log('and', val)
    },
    REACTION_TIME: {
      handler(val) {
        console.log('REACTION_TIME', val)
      },
      deep: true
    },
    LAST_ANSWER: {
      handler(val) {
        console.log('LAST_ANSWER', val)
      },
      deep: true
    }
  }
}
