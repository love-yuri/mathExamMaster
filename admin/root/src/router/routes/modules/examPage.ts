/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-11-19 20:17:50
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
      title: '试卷管理',
    },
    name: 'examPageManager',
    path: '/examPageManager',
    children: [
      {
        name: 'examPageManager',
        path: '/exam/page/manager',
        component: () => import('#/views/examPage/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:copyright',
          title: '试卷管理',
        },
      },
      {
        name: 'examPageCreate',
        path: '/exam/page/create',
        component: () => import('#/views/examPage/create/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:copyright',
          title: '创建试卷',
        },
      },
    ],
  },
];

export default routes;
