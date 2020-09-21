/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  // const valid_map = ['admin', 'editor']
  // return valid_map.indexOf(str.trim()) >= 0
  const reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
  if (reg.test(str)) { // 是电子邮箱
    return true
  } else {
    // 判断是否是电话号码
    const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
    if (Number.isInteger(+str)) {
      return phoneReg.test(+str)
    } else {
      return false
    }
  }
}
