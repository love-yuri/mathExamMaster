/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-09-10 23:39:53
 * @Description: 系统路由
 */
import type { RouteRecordRaw } from 'vue-router';

import { BasicLayout } from '#/layouts';

const routes: RouteRecordRaw[] = [
  {
    component: BasicLayout,
    meta: {
      icon: 'lucide:layout-dashboard',
      order: 2,
      title: '系统管理',
    },
    name: 'system',
    path: '/',
    children: [
      {
        name: 'generateCode',
        path: '/generate/code',
        component: () => import('#/views/system/generateCode/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          title: '代码生成',
        },
      },
    ],
  },
];

export default routes;
