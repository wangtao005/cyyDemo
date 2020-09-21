/*
* 获取反馈列表
* */
import axios from '@/config/axios/index'
import Qs from 'qs'

export default function getListByPage(data) {
  let url = `/feedback/listByPage?item=${new Date().getTime()}`
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
