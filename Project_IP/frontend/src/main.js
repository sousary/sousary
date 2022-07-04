import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { far } from "@fortawesome/free-regular-svg-icons";
import { fab } from "@fortawesome/free-brands-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faFontAwesome } from '@fortawesome/free-brands-svg-icons';
import $ from 'jquery';

  
library.add(faFontAwesome);
library.add(far);
library.add(fas);
library.add(fab);

const app = createApp(App)


app.use(router)
app.use(VueAxios, axios)
app.component("font-awesome-icon", FontAwesomeIcon)
app.mount('#app')
