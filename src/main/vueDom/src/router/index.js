import Vue from 'vue'
import Router from 'vue-router'
import spinRoute from "@/config/router/spinRoute.js";
Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404.vue'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index.vue'),
      meta: { title: '我的账户', icon: 'dashboard' }
    }]
  },
  {
    path: '/noFeedback',
    component: Layout,
    redirect: '/noFeedback',
    children: [{
      path: 'noFeedback',
      name: 'NoFeedback',
      component: () => import('@/views/noFeedback/index.vue'),
      meta: { title: '无反馈实验', icon: 'dashboard' }
    }]
  },
  {
    path: '/withFeedback',
    component: Layout,
    redirect: '/withFeedback',
    children: [{
      path: 'withFeedback',
      name: 'withFeedback',
      component: () => import('@/views/withFeedback/withFeedback.vue'),
      meta: { title: '带反馈实验', icon: 'dashboard' },
    }]
  },
  {
    path: '/download',
    component: Layout,
    redirect: '/download',
    // meta: { title: '实验数据', icon: 'dashboard' },
    name: 'Download',
    // alwaysShow: true,
    //hidden: true,
    children: [
      {
        path: '/download/created',
        name: 'Created',
        component: () => import('@/views/createdList/index.vue'),
        meta: { title: '实验与实验数据', icon: 'dashboard' },
        // children: [
        //   {
        //     hidden: true,
        //     path: 'examine/:id',
        //     component: () => import('@/views/examine/examine.vue'),
        //     name: 'ExamineFeedback',
        //     meta: { title: '查看', noCache: true, activeMenu: '@/views/createdList/index.vue' }
        //   },
        //   {
        //     hidden: true,
        //     path: 'editor/:id',
        //     component: () => import('@/views/editorFeedback/editorFeedback.vue'),
        //     name: 'EditorFeedback',
        //     meta: { title: '编辑', noCache: true, activeMenu: '@/views/createdList/index.vue' }
        //   },
        //   {
        //     hidden: true,
        //     path: 'examine/:id',
        //     component: () => import('@/views/examineWith/examine.vue'),
        //     name: 'ExamineFeedbackWith',
        //     meta: { title: '查看', noCache: true, activeMenu: '@/views/createdList/index.vue' }
        //   },
        //   {
        //     hidden: true,
        //     path: 'editor/:id',
        //     component: () => import('@/views/editorFeedbackWith/editorFeedback.vue'),
        //     name: 'EditorFeedbackWith',
        //     meta: { title: '编辑', noCache: true, activeMenu: '@/views/createdList/index.vue' }
        //   },
        // ]
      }
      // {
      //   path: 'downloadList',
      //   name: 'DownloadList',
      //   component: () => import('@/views/dashboard/index.vue'),
      //   meta: { title: '下载', icon: 'dashboard' }
      // }
    ]
  },
  {
    path: '/download/created',
    component: Layout,
    redirect: '/download/created',
    meta: { title: '实验数据', icon: 'dashboard' },
    hidden: true,
    name: 'Created',
    children: [
      {
        hidden: true,
        path: 'examine/:id',
        component: () => import('@/views/examine/examine.vue'),
        name: 'ExamineFeedback',
        meta: { title: '查看', noCache: true, activeMenu: '@/views/createdList/index.vue' }
      },
      {
        hidden: true,
        path: 'editor/:id',
        component: () => import('@/views/editorFeedback/editorFeedback.vue'),
        name: 'EditorFeedback',
        meta: { title: '编辑', noCache: true, activeMenu: '@/views/createdList/index.vue' }
      },
      {
        hidden: true,
        path: 'examineWith/:id',
        component: () => import('@/views/examineWith/examine.vue'),
        name: 'ExamineFeedbackWith',
        meta: { title: '查看', noCache: true, activeMenu: '@/views/createdList/index.vue' }
      },
      {
        hidden: true,
        path: 'editorWith/:id',
        component: () => import('@/views/editorFeedbackWith/editorFeedback.vue'),
        name: 'EditorFeedbackWith',
        meta: { title: '编辑', noCache: true, activeMenu: '@/views/createdList/index.vue' }
      },
      {
        hidden: true,
        path: 'answerRecord/:id',
        component: () => import('@/views/answerRecord/answerRecord.vue'),
        name: 'answerRecord',
        meta: { title: '答题记录', noCache: true, activeMenu: '@/views/createdList/index.vue' }
      }
    ]
  },
  {
    path: '/annex',
    component: Layout,
    redirect: '/annex',
    children: [{
      path: 'annex',
      name: 'Annex',
      component: () => import('@/views/annex/annex.vue'),
      meta: { title: '我的附件', icon: 'dashboard' }
    }]
  },
  {
    path: '/customize',
    component: Layout,
    redirect: '/customize',
    children: [
      {
        path: 'customize',
        name: 'Customize',
        component: () => import('@/views/customize/customize.vue'),
        meta: { title: '个性化定制', icon: 'dashboard' }
      }
    ]
  },
  // {
  //   path: '/contactUs',
  //   component: Layout,
  //   redirect: '/contactUs',
  //   children: [{
  //     path: 'contactUs',
  //     name: 'ContactUs',
  //     component: () => import('@/views/contactUs/contactUs.vue'),
  //     meta: { title: '联系我们', icon: 'dashboard' }
  //   }]
  // },
  // {
  //   path: '/example',
  //   component: Layout,
  //   redirect: '/example/table',
  //   name: 'Example',
  //   meta: { title: 'Example', icon: 'el-icon-s-help' },
  //   children: [
  //     {
  //       path: 'table',
  //       name: 'Table',
  //       component: () => import('@/views/table/index'),
  //       meta: { title: 'Table', icon: 'table' }
  //     },
  //     {
  //       path: 'tree',
  //       name: 'Tree',
  //       component: () => import('@/views/tree/index'),
  //       meta: { title: 'Tree', icon: 'tree' }
  //     }
  //   ]
  // },
  //
  // {
  //   path: '/form',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Form',
  //       component: () => import('@/views/form/index'),
  //       meta: { title: 'Form', icon: 'form' }
  //     }
  //   ]
  // },
  //
  // {
  //   path: '/nested',
  //   component: Layout,
  //   redirect: '/nested/menu1',
  //   name: 'Nested',
  //   meta: {
  //     title: 'Nested',
  //     icon: 'nested'
  //   },
  //   children: [
  //     {
  //       path: 'menu1',
  //       component: () => import('@/views/nested/menu1/index'), // Parent router-view
  //       name: 'Menu1',
  //       meta: { title: 'Menu1' },
  //       children: [
  //         {
  //           path: 'menu1-1',
  //           component: () => import('@/views/nested/menu1/menu1-1'),
  //           name: 'Menu1-1',
  //           meta: { title: 'Menu1-1' }
  //         },
  //         {
  //           path: 'menu1-2',
  //           component: () => import('@/views/nested/menu1/menu1-2'),
  //           name: 'Menu1-2',
  //           meta: { title: 'Menu1-2' },
  //           children: [
  //             {
  //               path: 'menu1-2-1',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
  //               name: 'Menu1-2-1',
  //               meta: { title: 'Menu1-2-1' }
  //             },
  //             {
  //               path: 'menu1-2-2',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
  //               name: 'Menu1-2-2',
  //               meta: { title: 'Menu1-2-2' }
  //             }
  //           ]
  //         },
  //         {
  //           path: 'menu1-3',
  //           component: () => import('@/views/nested/menu1/menu1-3'),
  //           name: 'Menu1-3',
  //           meta: { title: 'Menu1-3' }
  //         }
  //       ]
  //     },
  //     {
  //       path: 'menu2',
  //       component: () => import('@/views/nested/menu2/index'),
  //       name: 'Menu2',
  //       meta: { title: 'menu2' }
  //     }
  //   ]
  // },
  //
  // {
  //   path: 'external-link',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
  //       meta: { title: 'External Link', icon: 'link' }
  //     }
  //   ]
  // },

  // 404 page must be placed at the end !!!
  {
    path: '/answer/:id',
    component: resolve => {
      spinRoute.show();
      require(['@/views/answer/answer.vue'], spinRoute.resolve(resolve))
    },
    hidden: true,
    name: 'Answer'
  },
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
