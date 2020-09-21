<template>
  <div class="update-img">
    <div class="img-list-webkit">
      <div class="img-web" v-for="(item, index) in urls" :key="index" :style="{marginRight: '10px', marginBottom: '10px'}">
        <img :src="`${urlRoot}${item.path}`">
        <div class="hover-ot" v-if="!disabled">
          <span class="item-preview">
            <i class="el-icon-delete" @click="deleteIndex(index)" style="font-size: 20px;color: #FFF;"></i>
          </span>
        </div>
      </div>
      <div>
        <el-button type="primary" size="medium" @click="$refs.typeInputUpload.click()" :disabled="disabled" v-if="!disabled">上传</el-button><span style="color: red;padding-left: 10px;font-size: 12px;">可上传多张图片</span>
      </div>
    </div>
    <input style="display: none" ref="typeInputUpload" @change="uploadFile" type="file" accept="image/jpg,image/jpeg,image/png,image/gif,image/bmp"></input>
  </div>
</template>

<script>
import uploadFile from "@/api/fileUpload";
import { urlRoot } from "@/config/imgUrlRoot";

export default {
  name: "updateImg",
  props: {
    urls: {
      type: Array,
      default() {
        return []
      }
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      urlRoot: urlRoot,
      loadingPut: null,
      outUrls: []
    }
  },
  created() {
    // this.outUrls = [...this.urls]
    // this.outUrls.forEach(item => {
    //   if (item.path) {
    //     item.path = `${this.urlRoot}${item.path}`
    //   }
    // })
    console.log(1112, this.urls)
  },
  methods: {
    clickUpdate() {

    },
    deleteIndex(index) {
      this.urls.splice(index, 1)
    },
    uploadFile(event) {
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
        text: '图片上传中',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      //创建 formData 对象
      let formData = new FormData();
      // 向 formData 对象中添加文件
      formData.append('file', event.target.files[0]);
      uploadFile(formData).then(res => {
        this.loadingPut.close()
        if (res.status) {
          // console.log(res)
          this.urls.push({
            path: res.path
          })
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
.update-img{
  .img-list-webkit{
    .img-web{
      display: inline-block;
      width: 100px;
      height: 100px;
      background-position: center top;
      background-size: 100% auto;
      border: 1px solid #adadad;
      border-radius: 5px;
      vertical-align: top;
      position: relative;
      img{
        width: 100%;
        height: 100%;
      }
      .hover-ot{
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,.5);
        transition: opacity .3s;
        opacity: 0;
        text-align: center;
        line-height: 100px;
        position: absolute;
        top: 0;
        left: 0;
        .item-preview{
          padding: 8px;
          cursor: pointer;
        }
      }
      &:hover .hover-ot{
        opacity: 1;
      }
    }
  }
}
</style>