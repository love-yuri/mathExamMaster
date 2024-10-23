/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-08 15:42:02
 * @LastEditTime: 2024-10-22 21:22:09
 * @Description: 启动配置
 */
import { createApp } from 'vue';

import { registerAccessDirective } from '@vben/access';
import { initStores } from '@vben/stores';
import '@vben/styles';
import '@vben/styles/ele';

import Aura from '@primevue/themes/aura';
import PrimeVue from 'primevue/config';
import ConfirmationService from 'primevue/confirmationservice';
import ToastService from 'primevue/toastservice';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import Katex from 'katex';
import 'katex/dist/katex.min.css'; // 引入 KaTeX 样式

import { setupI18n } from '#/locales';

import App from './app.vue';
import { router } from './router';

async function bootstrap(namespace: string) {
  const app = createApp(App);

  // 国际化 i18n 配置
  await setupI18n(app);

  // 配置 pinia-tore
  await initStores(app, { namespace });

  // 安装权限指令
  registerAccessDirective(app);

  // 配置路由及路由守卫
  app.use(router);

  // 配置 katex
  app.use(Katex);

  // 配置primevue
  app.use(ToastService);
  app.use(ConfirmationService);
  app.use(PrimeVue, {
    ripple: true,
    theme: {
      preset: Aura,
    },
  });

  app.mount('#app');
}

export { bootstrap };
