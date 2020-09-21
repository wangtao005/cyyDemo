/*
* 微信支付二维码获取
* */
import axios from '@/config/axios/index'
import Qs from "qs";

export default function orderWeiXinNative(data) {
    let url = '/order/weixinNative'
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
