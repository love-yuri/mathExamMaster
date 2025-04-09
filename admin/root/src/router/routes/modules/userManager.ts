/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:35:05
 * @LastEditTime: 2025-02-27 14:44:00
 * @Description: 系统路由
 */
import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    meta: {
      icon: 'lucide:layout-dashboard',
      order: 2,
      menuVisibleWithForbidden: false,
      authority: ['ADMIN'],
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
