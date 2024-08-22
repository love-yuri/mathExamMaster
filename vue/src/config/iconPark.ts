/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-22 20:26:27
 * @LastEditTime: 2024-08-22 20:36:51
 * @Description: iconPark图标库
 */
import { install } from '@icon-park/vue-next/es/all';
import type { App } from 'vue';

export default (app: App) => {
  install(app, 'i'); // use default prefix 'icon', eg: icon is People, name is icon-people.
};
