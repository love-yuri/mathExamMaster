/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-10-18 21:12:36
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
      title: '试题管理',
    },
    name: 'questionManager',
    path: '/questionManager',
    children: [
      {
        name: 'knowledgePoint',
        path: '/knowledge/point',
        component: () =>
          import('#/views/questionManager/knowledgePoint/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          title: '知识点管理',
        },
      },
      {
        name: 'knowledgePointCreate',
        path: '/knowledge/point/create',
        component: () =>
          import('#/views/questionManager/knowledgePoint/create/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          title: '创建知识点',
        },
      },
      {
        name: 'questionBank',
        path: '/question/bank',
        component: () =>
          import('#/views/questionManager/questionBank/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          title: '题库管理',
        },
      },
      {
        name: 'questionBankCreate',
        path: '/question/bank/create',
        component: () =>
          import('#/views/questionManager/questionBank/create/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          title: '创建题目',
        },
      },
      {
        name: 'questionBankUpdate',
        path: String.raw`/question/bank/update/:id(\d+)`,
        component: () =>
          import('#/views/questionManager/questionBank/components/update.vue'),
        meta: {
          affixTab: false,
          hideInMenu: true,
          icon: 'lucide:area-chart',
          title: '修改题目',
        },
      },
    ],
  },
];

export default routes;
