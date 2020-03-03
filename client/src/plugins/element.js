import Vue from 'vue'
import Element, {MessageBox} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(Element)
//挂载到vue对象上
Vue.prototype.$confirm = MessageBox.confirm
