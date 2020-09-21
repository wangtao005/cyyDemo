/*
* 获取用户信息
* */
import axios from '@/config/axios/index'

export default function infoAjax(data) {
  let url = '/endUser/info'
  return new Promise((resolve, reject) => {
    axios.get(url, {}, {
      // 'Content-Type': 'application/x-www-form-urlencoded'
    }, data || {}).then(res => {
      resolve(res)
    }).catch(error => {
      reject(error)
      new Error(error)
    })
  })
}
