import axios from './http'

let instance = axios()

export default {
  get(url, params, headers) {
    let options = {}

    if (params) {
      options.params = params
    }
    if (headers) {
      options.headers = headers
    }
    return instance.get(url, options)
  },
  post(url, params, headers, data) {
    let options = {}

    if (params) {
      options.params = params
    }
    if (headers) {
      options.headers = headers
    }
    // if (data) {
    //   options.data = Qs.stringify(data)
    // }
    return instance.post(url, data, options)
  },
  downloadExcel(url, params, headers, data) {
    let options = {}
    options.responseType = "blob"
    if (params) {
      options.params = params
    }
    if (headers) {
      options.headers = headers
    }
    // if (data) {
    //   options.data = Qs.stringify(data)
    // }
    return instance.post(url, data, options)
  },
  put(url, params, headers) {
    let options = {}

    if (headers) {
      options.headers = headers
    }
    return instance.put(url, params, options)
  },
  uploadFile(url, headers, data) {
    let options = {}
    if (headers) {
      options.headers = headers
    }
    return instance.post(url, data, options)
  },
  delete(url, params, headers) {
    let options = {}

    if (params) {
      options.params = params
    }
    if (headers) {
      options.headers = headers
    }
    return instance.delete(url, options)
  }
}
