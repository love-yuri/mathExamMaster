/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-10-01 21:44:58
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
    path: '/system',
    children: [
      {
        name: 'generateCode',
        path: '/generate/code',
        component: () => import('#/views/system/generateCode/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          title: '输入测试',
        },
      },
      {
        name: 'Question Bank Management',
        path: '/question/bank',
        component: () => import('#/views/system/questionBank/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          title: '题库管理',
        },
      },
    ],
  },
];

export default routes;
