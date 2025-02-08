/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2025-02-08 16:49:53
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
    name: 'examManager-stu',
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
        name: 'overExam',
        path: '/over/exam',
        component: () => import('#/views/examManager/components/overExam.vue'),
        meta: {
          affixTab: false,
          hideInMenu: true,
          hideInTab: true,
          icon: 'lucide:copyright',
          title: '考试结束',
        },
      },
    ],
  },
];

export default routes;
