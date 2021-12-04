import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import Notifications from '@kyvg/vue3-notification'

const app = createApp(App)
app.use(Notifications)

app.config.globalProperties.axios = axios

app.mount('#app')

