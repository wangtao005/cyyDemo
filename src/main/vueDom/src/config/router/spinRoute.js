import { Loading } from "element-ui";
let fullscreenLoading = false
export default {
  show() {
    fullscreenLoading = Loading.service({
      text: '页面加载中'
    })
  },
  resolve(resolve) {
    return component => {
      setTimeout(()=>{
        fullscreenLoading.close()
        resolve(component);
      }, 10)
    }
  }
}
