/* 添加反馈 */
import axios from '@/config/axios/index'

export default function saveAndEdit(data) {
  let url = '/feedback/saveAndEdit'
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
