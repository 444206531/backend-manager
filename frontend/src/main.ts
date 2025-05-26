import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// Import Element Plus styles
import 'element-plus/dist/index.css' 
// If you want to use SCSS variables, you might need a different import,
// e.g., 'element-plus/theme-chalk/src/index.scss' and configure SCSS processing in Vite.
// For now, basic CSS is fine.

// Import global custom styles (optional, if you have one)
import './assets/main.css' 

const app = createApp(App)

app.use(createPinia())
app.use(router)
// Element Plus components will be auto-imported via unplugin-vue-components,
// so no app.use(ElementPlus) is needed here with this setup.

app.mount('#app')
