/*
* 注册
* */
import axios from '@/config/axios/index'

export default function registerAjax(data) {
  let url = '/endUser/register'
  return new Promise((resolve, reject) => {
    axios.post(url, {}, {
      // 'Content-Type': 'application/x-www-form-urlencoded'
    }, data || {}).then(res => {
      resolve(res)
    }).catch(error => {
      reject(error)
      new Error(error)
    })
  })
}
