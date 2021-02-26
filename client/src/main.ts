import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import { createLogger, StringifyObjectsHook } from 'vue-logger-plugin'

const logger = createLogger({
  enabled: true,
  level: 'debug',
  beforeHooks: [StringifyObjectsHook]
})

createApp(App).use(router).use(logger).mount('#app')
