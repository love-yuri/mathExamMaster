/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-10-23 21:32:03
 * @Description: 系统路由
 */
import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
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
          authority: ['ADMIN', 'TEACHER'],
          icon: 'lucide:copyright',
          menuVisibleWithForbidden: false,
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
          authority: ['ADMIN', 'TEACHER'],
          icon: 'lucide:baggage-claim',
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
          authority: ['ADMIN', 'TEACHER'],
          icon: 'lucide:cannabis',
          menuVisibleWithForbidden: true,
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
          authority: ['ADMIN', 'TEACHER'],
          icon: 'lucide:fish',
          menuVisibleWithForbidden: false,
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
          authority: ['ADMIN', 'TEACHER'],
          hideInMenu: true,
          icon: 'lucide:vegan',
          menuVisibleWithForbidden: false,
          title: '修改题目',
        },
      },
    ],
  },
];

export default routes;
