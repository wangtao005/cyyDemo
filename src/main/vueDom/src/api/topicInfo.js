/*
* 获取题目详情
* */
import axios from '@/config/axios/index'
import Qs from "qs";

export default function topicInfo(data) {
  // let url = '/feedback/info'
  let url = '/feedback/random/info'
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
