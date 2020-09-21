import axios from 'axios'
import Qs from 'qs'
import boot from '@/utils/boot'
import store from '@/store'
import { getToken } from '@/utils/auth'
// import { Message } from 'element-ui'
axios.defaults.withCredentials = true
axios.defaults.baseURL = boot.rootPath
let service = axios.create({
  headers: {'Content-Type': 'application/json'},
  timeout: 120000
})
// 拦截器
service.interceptors.request.use(config => {
  if (store.getters.token) {
    // let each request carry token
    // ['X-Token'] is a custom headers key
    // please modify it according to the actual situation
    config.headers['Authorization'] = getToken()
  }
  // 鉴权参数设置
  if(config.method === 'get'){
    //get请求下 参数在params中，其他请求在data中
    config.params = config.params || {};
    // let json = JSON.parse(JSON.stringify(config.params));
    //一些参数处理
  }else if (config.method === 'post') {
    // post、put 提交时，将对象转换为string, 为处理Java后台解析问题
    // config.data = Qs.stringify(config.data)
  } else if (config.method === 'put') {
    // post、put 提交时，将对象转换为string, 为处理Java后台解析问题
    config.data = Qs.stringify(config.data)
  } else if (config.method === 'uploadFile') {

  } else {
    config.data = config.data || {};
    //一些参数处理
  }
  return config
}, error => {
  // Message.error(error)
  console.log(error)
})

service.interceptors.response.use(response => {
  let { data } = response
  return data
}, error => {
  // Message.error(error)
  console.log(error)
})
/**
 * 创建统一封装过的 axios 实例
 * @return {AxiosInstance}
 */
export default function() {
  return service
}
