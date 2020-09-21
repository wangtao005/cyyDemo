export default function(router) {
	console.log(router)
	router.beforeEach((to, from, next) => {
		// console.log(to)
		// console.log(from)
		// if (to.matched.some(res => res.meta.requireAuth)) { // 判断是否需要登陆权限
		// 	if (localStorage.getItem('appToken')) { // 判断是否登陆
		// 		next()
		// 	} else { // 没有登陆，跳转到登陆页面
		// 		next({
		// 			name: 'login',
		// 			query: {
		// 				redirect: to.fullPath
		// 			}
		// 		})
		// 	}
		// 	next()
		// } else {
		// 	next()
		// }
		next()
	})
}
