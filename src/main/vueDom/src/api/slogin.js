/*
* 登录-短信
* */
import axios from '@/config/axios/index'

export default function sloginAjax(data) {
  let url = '/endUser/slogin'
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
