/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-12-19 18:27:40
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
      title: '练习管理',
    },
    name: 'examManager',
    path: '/examManager',
    children: [
      {
        name: 'examManager',
        path: '/question/bank',
        component: () => import('#/views/examManager/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:copyright',
          title: '练习管理',
        },
      },
      {
        name: 'doExam',
        path: String.raw`/do/exam/:id(\d+)`,
        component: () => import('#/views/examManager/doExam.vue'),
        meta: {
          hideInBreadcrumb: true,
          hideInMenu: true,
          hideInTabs: true,
          icon: 'lucide:copyright',
          title: '',
        },
      },
    ],
  },
];

export default routes;
