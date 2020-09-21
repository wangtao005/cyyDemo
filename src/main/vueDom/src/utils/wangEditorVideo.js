import {getToken} from "@/utils/auth";
import uploadFile from "@/api/fileUpload";
import { urlRoot } from "@/config/imgUrlRoot";

function romDom(upTriggerId, upFileId) {
  return `
    <div id="${upTriggerId}" class="w-e-up-btn" style="text-align: center;cursor: pointer;">
        <i class="w-e-icon-upload2" style="font-size: 60px"></i>
    </div>
    <div style="display:none;">
        <input id="${upFileId}" type="file" accept="video/*">
    </div>
  `
}
function wangEditorVideo(Vue, editor) {
  editor.customConfig.onchange = (html) => {
    Vue.editorContent = html
  }
  editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
  editor.customConfig.uploadImgServer = 'file/editorFileUpload'  // 上传图片到服务器
  // editor.customConfig.uploadImgServer = 'file/fileUpload'  // 上传图片到服务器
  editor.customConfig.uploadFileName = 'file'
  editor.customConfig.uploadImgMaxLength = 1
  editor.customConfig.uploadImgHeaders = {
    Authorization: getToken(),
  }
  editor.customConfig.uploadImgHooks = {
    customInsert(insertImg, result) {
      // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
      // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

      // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
      let url = result.data
      let imgs = []
      if (url.length) {
        url.forEach(item => {
          imgs.push(`${urlRoot}${item}`)
        })
      }
      insertImg(imgs)
      // result 必须是一个 JSON 格式字符串！！！否则报错
    }
  }
  // editor.customConfig.menus = [
  //   'head',  // 标题
  //   'bold',  // 粗体
  //   'fontSize',  // 字号
  //   'fontName',  // 字体
  //   'italic',  // 斜体
  //   'underline',  // 下划线
  //   'strikeThrough',  // 删除线
  //   'foreColor',  // 文字颜色
  //   'backColor',  // 背景颜色
  //   'list',  // 列表
  //   'justify',  // 对齐方式
  //   'quote',  // 引用
  //   'emoticon',  // 表情
  //   'image',  // 插入图片
  //   'table',  // 表格
  //   'video',
  //   'code',  // 插入代码
  //   'undo',  // 撤销
  //   'redo'  // 重复
  // ];
  editor.create()
  const stTm = setTimeout(() => {
    // console.log(editor.$toolbarElem[0].children)
    editor.$toolbarElem[0].children[16].addEventListener('click', (e) => { // 自定义视频上传功能
      let centDom = document.querySelector('.w-e-panel-container')
      let panelTabContentDiv = centDom.querySelector('.w-e-panel-tab-content > div')
      centDom.querySelector('.block').style.display = 'none'

      let upTriggerId = Number(Math.random().toString().substr(3, 3) + Date.now()).toString(36)
      let upFileId = Number(Math.random().toString().substr(3, 3) + Date.now()).toString(36)

      panelTabContentDiv.querySelector('.w-e-button-container').style.display = 'none'
      panelTabContentDiv.insertAdjacentHTML('afterbegin', romDom(upTriggerId, upFileId))
      let clickEvt = document.createEvent("MouseEvents");
      clickEvt.initEvent("click", true, true);
      let upFileIdEvt = document.getElementById(upFileId)
      // 点击图标触发input视频上传
      document.getElementById(upTriggerId).addEventListener('click', () => {
        upFileIdEvt.dispatchEvent(clickEvt)
      }, false)
      // 用户选择视频后触发
      upFileIdEvt.addEventListener('change', (event) => {
        event.preventDefault();//取消默认行为
        //创建 formData 对象
        let formData = new FormData();
        // 向 formData 对象中添加文件
        let sizeFile = Math.round(event.target.files[0].size / 1024 / 1024 * 100 ) / 100;
        if (sizeFile > 100) {
          // Vue.$message.error('请上传100MB以内的视频文件');
          alert('请上传100MB以内的视频文件')
          centDom.querySelector('.w-e-panel-close').dispatchEvent(clickEvt)
          return
        }
        formData.append('file', event.target.files[0]);
        Vue.loading = true
        uploadFile(formData).then(res => {
          Vue.loading = false
          upFileIdEvt.value = ''
          editor.cmd.do('insertHTML', `<p><iframe style="width: 552px;min-height: 314px;margin: 0 auto;display: block;" src="${urlRoot}${res.path}"></iframe></p>`);
          centDom.querySelector('.w-e-panel-close').dispatchEvent(clickEvt)
        }).catch(error => {
          alert(error)
        })
      }, false)

    }, false)
  }, 20)
  // 组件注销时清除定时器
  Vue.$once('hook:beforeDestroy', () => {
    clearTimeout(stTm)
  })
}

export default wangEditorVideo
