/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-12-05 18:29:53
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
        name: 'examPage',
        path: '/exam/page',
        component: () => import('#/views/examPageManager/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:copyright',
          title: '试卷管理',
        },
      },
      {
        name: 'examPageCreate',
        path: String.raw`/exam/page/create`,
        component: () => import('#/views/examPageManager/create/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:copyright',
          title: '创建试卷',
        },
      },
      {
        name: 'examPageUpdate',
        path: String.raw`/exam/page/update/:id(\d+)`,
        component: () => import('#/views/examPageManager/create/index.vue'),
        meta: {
          affixTab: false,
          hideInMenu: true,
          icon: 'lucide:copyright',
          title: '更新试卷',
        },
      },
      {
        name: 'examPageRelease',
        path: String.raw`/exam/page/release`,
        component: () => import('#/views/examPageManager/release/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:copyright',
          title: '发布试卷管理',
        },
      },
      {
        name: 'examPageReleasePage',
        path: String.raw`/exam/page/release/page/:id(\d+)`,
        component: () =>
          import('#/views/examPageManager/release/releasePage.vue'),
        meta: {
          affixTab: false,
          hideInMenu: true,
          icon: 'lucide:copyright',
          title: '发布试卷',
        },
      },
    ],
  },
];

export default routes;
