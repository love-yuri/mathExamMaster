/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-10 16:40:18
 * @LastEditTime: 2024-08-11 00:47:29
 * @Description: PrimeVue配置文件
 */

import type { App } from 'vue';
import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import ToastService from 'primevue/toastservice';

export default (app: App): void => {
  app.use(ToastService);
  app.use(PrimeVue, {
    ripple: true,
    theme: {
      preset: Aura,
    },
  });
};
