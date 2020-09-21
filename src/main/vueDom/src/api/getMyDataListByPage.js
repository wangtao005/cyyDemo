/*
* 获取答题列表
* */
import axios from '@/config/axios/index'
import Qs from 'qs'

export default function getMyDataList(data) {
  let url = '/my/data/listByPage'
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
