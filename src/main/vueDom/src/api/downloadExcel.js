/*
* 下载excel
* */
import axios from '@/config/axios/index'

export default function downloadExcel(data = {}) {
  let url = '/file/downloadExcel'
  return new Promise((resolve, reject) => {
    axios.downloadExcel(url, {
      'Content-Type': 'application/x-www-form-urlencoded'
    }, data).then(res => {
      resolve(res)
    }).catch(error => {
      reject(error)
      new Error(error)
    })
  })
}
