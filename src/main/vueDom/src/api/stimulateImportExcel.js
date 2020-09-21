/*
* 上传文件
* */
import axios from '@/config/axios/index'

export default function stimulateImportExcel(data) {
  let url = '/experiment/stimulate/importExcel'
  return new Promise((resolve, reject) => {
    axios.uploadFile(url, {
      'Content-Type': 'application/x-www-form-urlencoded'
    }, data).then(res => {
      resolve(res)
    }).catch(error => {
      reject(error)
      new Error(error)
    })
  })
}
