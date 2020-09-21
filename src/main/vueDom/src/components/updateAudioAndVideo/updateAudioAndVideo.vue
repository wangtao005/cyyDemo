<template>
  <div class="update-audio-and-video">
    <el-input
        style="width: 70%;margin-right: 10px"
        placeholder="请上传"
        :disabled="true"
        size="medium"
        v-model.trim="linkUrl">
    </el-input>
    <el-button type="primary" size="medium" @click="$refs.typeInputUpload.click()" :disabled="disabled" v-if="!disabled">上传</el-button>
    <input style="display: none" type="file" ref="typeInputUpload" @change="uploadFileAudioAndVide" accept="video/*,audio/*"></input>
  </div>
</template>

<script>
import uploadFile from "@/api/fileUpload";

export default {
  name: "updateAudioAndVideo",
  props: {
    linkUrl: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      loadingPut: null
    }
  },
  created() {
  },
  methods: {
    uploadFileAudioAndVide(event) {
      if (!event.target.files[0]) {
        return
      }
      let sizeFile = Math.round(event.target.files[0].size / 1024 / 1024 * 100 ) / 100;
      if (sizeFile > 100) {
        this.$message.error('请上传100MB以内的视频文件');
        return
      }
      this.loadingPut = this.$loading({
        lock: true,
        text: '音视频上传中',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      //创建 formData 对象
      let formData = new FormData();
      // 向 formData 对象中添加文件
      formData.append('file', event.target.files[0]);
      uploadFile(formData).then(res => {
        this.loadingPut.close()
        if (res.status) {
          this.$emit('update:linkUrl', res.path)
        }
      }).catch(error => {
        this.loading = false
        alert(error)
      })
    }
  }
}
</script>

<style scoped lang="scss">
.update-audio-and-video{

}
</style>