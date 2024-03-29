import { createApp } from 'vue'
import mitt from 'mitt'
import App from './App.vue'
import router from './router'
// import AppFight from '../fight-module/src/AppFight.vue'
const emitter = mitt()
const app = createApp(App)


app.config.globalProperties.emitter = emitter
app.use(router)
app.mount('#app')
// createApp(AppFight).mount('#fightApp')
