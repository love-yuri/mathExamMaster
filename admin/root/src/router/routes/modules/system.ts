/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-11-05 19:16:33
 * @Description: 系统路由
 */
import type { RouteRecordRaw } from 'vue-router';

import { BasicLayout } from '#/layouts';

const routes: RouteRecordRaw[] = [
  {
    component: BasicLayout,
    meta: {
      icon: 'lucide:layout-dashboard',
      menuVisibleWithForbidden: false,
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
          menuVisibleWithForbidden: false,
          title: '代码生成',
        },
      },
      {
        name: 'inputTest',
        path: '/input/test',
        component: () => import('#/views/system/inputTest/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          menuVisibleWithForbidden: false,
          title: '输入测试',
        },
      },
      {
        name: 'mathTest',
        path: '/math/test',
        component: () => import('#/views/system/mathTest/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:area-chart',
          menuVisibleWithForbidden: false,
          title: '数学测试',
        },
      },
    ],
  },
];

export default routes;
