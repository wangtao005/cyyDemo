/*
* 个性化定制
* */
import axios from '@/config/axios/index'
import Qs from 'qs'

export default function personaliseSave(data) {
  let url = '/personalise/save'
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
