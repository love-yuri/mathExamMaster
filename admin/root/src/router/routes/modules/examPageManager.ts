/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2025-02-20 19:33:38
 * @Description: 系统路由
 */
import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
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
      {
        name: 'examPageReleasePageUpdate',
        path: String.raw`/exam/page/release/page/update/:id(\d+)`,
        component: () =>
          import('#/views/examPageManager/release/releasePageUpdate.vue'),
        meta: {
          affixTab: false,
          hideInMenu: true,
          icon: 'lucide:copyright',
          title: '更新发布试卷',
        },
      },
      {
        name: 'examPageReleaseReviewing',
        path: String.raw`/exam/page/release/page/reviewing/:id(\d+)`,
        component: () =>
          import('#/views/examPageManager/release/reviewing.vue'),
        meta: {
          affixTab: false,
          hideInMenu: true,
          icon: 'lucide:copyright',
          title: '阅卷',
        },
      },
    ],
  },
];

export default routes;
