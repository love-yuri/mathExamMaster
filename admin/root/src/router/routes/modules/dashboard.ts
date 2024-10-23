/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-08 15:42:02
 * @LastEditTime: 2024-10-23 21:09:33
 * @Description: 路由
 */
import type { RouteRecordRaw } from 'vue-router';

import { BasicLayout } from '#/layouts';
import { $t } from '#/locales';

const routes: RouteRecordRaw[] = [
  {
    component: BasicLayout,
    meta: {
      icon: 'lucide:layout-dashboard',
      order: -1,
      title: $t('page.dashboard.title'),
    },
    name: 'Dashboard',
    path: '/',
    children: [
      {
        name: 'Analytics',
        path: '/analytics',
        component: () => import('#/views/dashboard/analytics/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          title: '后台管理',
        },
      },
    ],
  },
];

export default routes;
