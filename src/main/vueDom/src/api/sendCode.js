/*
* 获取验证码
* */
import axios from '@/config/axios/index'

export default function sendCode(data) {
  let url = 'endUser/sendCode'
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
