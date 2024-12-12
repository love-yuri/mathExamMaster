/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2024-12-12 17:49:16
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
      title: '组织用户管理',
    },
    name: 'userManager',
    path: '/userManager',
    children: [
      {
        name: 'userDepartment',
        path: '/user/department',
        component: () => import('#/views/userManager/department.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:copyright',
          title: '组织管理',
        },
      },
    ],
  },
];

export default routes;
