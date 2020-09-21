/*
* 上传附件
* */
import axios from '@/config/axios/index'

export default function cloudDiskSave(data) {
  let url = '/cloudDisk/save'
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