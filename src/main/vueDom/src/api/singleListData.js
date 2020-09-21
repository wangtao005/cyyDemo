/*
* 个人详情导出
* */
import axios from '@/config/axios/index'
import Qs from 'qs'

export default function singleListDate(data) {
    let url = '/my/data/single/listDate'
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
