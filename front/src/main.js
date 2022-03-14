import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import store from './store/store'
import mitt from 'mitt'
import SocketClient from '@/socketjs/socketClient'

const app = createApp(App)
const emitter = mitt()
app.config.globalProperties.ws = SocketClient
app.config.globalProperties.emitter = emitter
app.use(store)
app.use(router)
app.mount('#app')
