import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/admin/Home.vue'
import Welcome from '../components/admin/Welcome.vue'
import Users from '../components/admin/Users.vue'
import Rights from '../components/power/Rights.vue'
import Roles from '../components/power/Roles.vue'
import Cate from '../components/goods/Cate.vue'
import Params from '../components/goods/Params.vue'
import GoodList from '../components/goods/List.vue'
import OrderList from '../components/order/Order.vue'
import Add from '../components/goods/Add.vue'
import NotPermission from '../components/admin/NotPermission.vue'

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
      },
      {
        path:'/rights',
        component:Rights
      },
      {
        path:'/roles',
        component:Roles
      },
      {
        path:'/sorts',
        component:Cate
      },
        {
            path:'/params',
            component:Params
        },
      {
        path:'/goods',
        component:GoodList
      },
      {
        path:'/orders',
        component:OrderList
      },
      {
        path:'/goods/add',
        component:Add
      },
      {
        path:'/notPermission',
        component:NotPermission
      }
    ]
  }
]

const router = new VueRouter({
 // mode: 'history',
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
