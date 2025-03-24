import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    meta: {
      icon: 'lucide:folder-tree',
      order: 3,
      title: '题目分类管理',
    },
    name: 'questionCategoryManager',
    path: '/questionCategoryManager',
    children: [
      {
        name: 'questionCategory',
        path: '/question/category',
        component: () => import('#/views/questionCategoryManager/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:folder-tree',
          title: '分类管理',
        },
      },
      {
        name: 'questionCategoryCreate',
        path: '/question/category/create/',
        component: () =>
          import('#/views/questionCategoryManager/create/index.vue'),
        meta: {
          affixTab: false,
          icon: 'lucide:folder-plus',
          title: '创建分类',
        },
      },
      {
        name: 'questionCategoryUpdate',
        path: String.raw`/question/category/update/:id(\d+)`,
        component: () =>
          import('#/views/questionCategoryManager/create/index.vue'),
        meta: {
          affixTab: false,
          hideInMenu: true,
          icon: 'lucide:folder-plus',
          title: '更新分类',
        },
      },
    ],
  },
];

export default routes;
