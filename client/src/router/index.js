import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/admin/Home.vue'
import Welcome from '../components/admin/Welcome.vue'
import Users from '../components/admin/Users.vue'

Vue.use(VueRouter)

const routes = [
  {
    path:'/login',
    component:Login
  },
  {
    path:'/home',
    component:Home,
    redirect:'/welcome',
    children:[
      {
        path:'/welcome',
        component:Welcome
      },
      {
        path:'/users',
        component:Users
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

//挂载路由导航守卫
router.beforeEach((to,from,next)=>{
  console.log(from)
  //to 将要访问的路径
  //from 代表从哪个路径跳转而来
  //next 是一个函数表示放行
  //      next() 放行 next('/login') 强制跳转
  if(to.path==='/login') return next();
  //      获取token
  const tokenStr = window.sessionStorage.getItem('token')
  if(!tokenStr) return  next('/login')
  next()
})

export default router
