/* 保存答案 */
import axios from '@/config/axios/index'

export default function saveAnswer(data) {
  let url = '/my/data/saveAndEdit'
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
