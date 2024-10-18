/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-20 22:54:05
 * @LastEditTime: 2024-10-18 21:17:44
 * @Description: 路由配置
 */
import {
  createRouter,
  createWebHashHistory,
  createWebHistory,
  useRoute,
} from 'vue-router';

import { resetStaticRoutes } from '@vben/utils';

import { createRouterGuard } from './guard';
import { routes } from './routes';

/**
 *  @zh_CN 创建vue-router实例
 */
const router = createRouter({
  history:
    import.meta.env.VITE_ROUTER_HISTORY === 'hash'
      ? createWebHashHistory(import.meta.env.VITE_BASE)
      : createWebHistory(import.meta.env.VITE_BASE),
  // 应该添加到路由的初始路由列表。
  routes,
  scrollBehavior: () => ({ left: 0, top: 0 }),
  // 是否应该禁止尾部斜杠。
  // strict: true,
});

const resetRoutes = () => resetStaticRoutes(router, routes);

// 创建路由守卫
createRouterGuard(router);

export { resetRoutes, router, useRoute };
