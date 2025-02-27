import { $t, setupI18n } from '#/locales';
import Aura from '@primevue/themes/aura';
import { registerAccessDirective } from '@vben/access';
import { initTippy, registerLoadingDirective } from '@vben/common-ui';
import { MotionPlugin } from '@vben/plugins/motion';
import { preferences } from '@vben/preferences';
import { initStores } from '@vben/stores';
import { useTitle } from '@vueuse/core';
/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-27 11:55:53
 * @LastEditTime: 2025-02-27 19:12:37
 * @Description:
 */
import { createApp, watchEffect } from 'vue';

import '@vben/styles';
import '@vben/styles/ele';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import PrimeVue from 'primevue/config';
import ConfirmationService from 'primevue/confirmationservice';
import DialogService from 'primevue/dialogservice';
import ToastService from 'primevue/toastservice';

import App from './app.vue';
import { router } from './router';

async function bootstrap(namespace: string) {
  // 初始化组件适配器
  // // 设置弹窗的默认配置
  // setDefaultModalProps({
  //   fullscreenButton: false,
  // });
  // // 设置抽屉的默认配置
  // setDefaultDrawerProps({
  //   zIndex: 2000,
  // });
  const app = createApp(App);

  // 注册Vben提供的v-loading和v-spinning指令
  registerLoadingDirective(app, {
    loading: 'loading',
    spinning: 'spinning',
  });

  // 国际化 i18n 配置
  await setupI18n(app);

  // 配置 pinia-tore
  await initStores(app, { namespace });

  // 安装权限指令
  registerAccessDirective(app);

  // 初始化 tippy
  initTippy(app);

  // 配置路由及路由守卫
  app.use(router);

  // 配置Motion插件
  app.use(MotionPlugin);

  // 配置primevue
  app.use(ToastService);
  app.use(ConfirmationService);
  app.use(DialogService);
  app.use(PrimeVue, {
    ripple: true,
    theme: {
      preset: Aura,
    },
  });

  // 动态更新标题
  watchEffect(() => {
    if (preferences.app.dynamicTitle) {
      const routeTitle = router.currentRoute.value.meta?.title;
      const pageTitle =
        (routeTitle ? `${$t(routeTitle)} - ` : '') + preferences.app.name;
      useTitle(pageTitle);
    }
  });

  app.mount('#app');
}

export { bootstrap };
