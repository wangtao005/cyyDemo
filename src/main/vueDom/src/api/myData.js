/*
* 导出数据
* */
import axios from '@/config/axios/index'
import Qs from 'qs'

export default function exportData(data) {
  let url = '/my/data/listData'
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
