import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import './assets/css/global.css'
import './assets/fonts/iconfont.css'
import TreeTable from 'vue-table-with-tree-grid'
import axios from 'axios'
//导入富文本编辑器
import VueQuillEditor from 'vue-quill-editor'
//导入富文本样式
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'


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

Vue.component('tree-table',TreeTable)

//将富文本编辑器 注册为全局可用的组件
Vue.use(VueQuillEditor)


//全局过滤器
Vue.filter('dateFormat',function (originVal) {
    const dt = new Date(originVal)

    const year = dt.getFullYear()
    const month = (dt.getMonth() + 1 + '').padStart(2,'0')
    const date = (dt.getDate() + '').padStart(2,'0')
    const hour = (dt.getHours() + '').padStart(2,'0')
    const minute = (dt.getMinutes()+'').padStart(2,'0')
    const second = (dt.getSeconds() + '').padStart(2,'')

    return `${year}-${month}-${date} ${hour}:${minute}:${second}`   //注意是反引号
})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
