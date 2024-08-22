/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-10 15:47:59
 * @LastEditTime: 2024-08-22 20:28:18
 * @Description: main 入口文件
 */
import './assets/main.css';
import '@icon-park/vue-next/styles/index.css'; // iconPark 图标库样式

import { createApp } from 'vue';

import App from './App.vue';

import PiniaRegister from './config/pinia';
import RouterRegister from './config/route';
import PrimeVueRegister from './config/primevue';
import IconParkRegister from './config/iconPark';

const app = createApp(App);

/* 注册 */
RouterRegister(app);
PrimeVueRegister(app);
PiniaRegister(app);
IconParkRegister(app);

app.mount('#app');
