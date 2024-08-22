/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-10 15:47:59
 * @LastEditTime: 2024-08-22 20:11:49
 * @Description: 路由配置
 */

import type { App } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/auth/Login.vue'),
    },
  ],
});

export default (app: App): void => {
  app.use(router);
};
