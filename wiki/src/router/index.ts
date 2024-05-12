import {createRouter, createWebHistory, NavigationGuardNext, RouteLocationNormalized, RouteRecordRaw} from 'vue-router'
import store from "@/store";
import {Tool} from "@/util/tool"
const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')

  },
  {
    path: '/admin/message',
    name: 'AdminMessage',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/admin-message.vue')
  },
  {
    path: '/admin/course',
    name: 'AdminCourse',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/admin-course.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// router.beforeEach((to:RouteLocationNormalized,form:RouteLocationNormalized,next:NavigationGuardNext)=>{
//   if(to.matched.some(function (item){
//     console.log(item,"是否需要登录校验：",item.meta.loginRequire);
//     return item.meta.loginRequire
//   })) {
//     const loginUser=store.state.user;
//     if(Tool.isEmpty(loginUser)){
//       next('/');
//     }else {
//       next();
//     }
//   }
// });

export default router
