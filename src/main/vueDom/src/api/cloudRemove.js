/*
* 删除附件
* */
import axios from '@/config/axios/index'
import Qs from "qs";

export default function cloudRemove(data) {
  let url = `/cloudDisk/remove?item=${new Date().getTime()}`
  return new Promise((resolve, reject) => {
    axios.post(url, {}, {
      'Content-Type': 'application/x-www-form-urlencoded'
    }, Qs.stringify(data) || {}).then(res => {
      resolve(res)
    }).catch(error => {
      reject(error)
      new Error(error)
    })
  })
}