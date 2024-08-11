/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-10 15:47:59
 * @LastEditTime: 2024-08-11 22:36:10
 * @Description: main 入口文件
 */
import './assets/main.css';

import { createApp } from 'vue';

import App from './App.vue';

import PiniaRegister from './config/pinia';
import RouterRegister from './config/route';
import PrimeVueRegister from './config/primevue';

const app = createApp(App);

/* 注册 */
RouterRegister(app);
PrimeVueRegister(app);
PiniaRegister(app);

app.mount('#app');
