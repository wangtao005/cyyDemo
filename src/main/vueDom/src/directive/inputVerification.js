export function directiveVue(Vue) {
  Vue.directive('verification', {
    // inserted: function(el) {
    //   console.log(el)
    // }
    bind(el, binding, vnode) {
      // console.log(el, binding, vnode)
      // const element = el.getElementsByTagName('input')[0]
      // const len = binding.arg  // 初始化设置
      // console.log(element, len)
      // element.addEventListener('keypress', (e) => {
      //   return (/[\d]/.test(String.fromCharCode(e.keyCode)))
      // }, false)
    }
  })
}