import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import './assets/css/global.css'
import './assets/fonts/iconfont.css'
import axios from 'axios'
//设置默认请求路径
axios.defaults.baseURL = 'http://localhost:8181/'
axios.interceptors.request.use(config=>{
  console.log(config)
  //最后必须返回config
  config.headers.Authorization = window.sessionStorage.getItem('token')
  return config
})

//挂载在vue对象上
Vue.prototype.$http = axios

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
