/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-10 17:03:06
 * @LastEditTime: 2024-08-11 00:47:17
 * @Description: pinia配置
 */
import { createPinia } from 'pinia';
import type { App } from 'vue';

export default (app: App): void => {
  app.use(createPinia());
};
