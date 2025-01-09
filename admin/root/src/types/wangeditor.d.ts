/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-20 21:47:03
 * @LastEditTime: 2025-01-09 18:43:53
 * @Description: wangEditor 类型声明文件
 */

declare module '@wangeditor-next/editor-for-vue' {
  import { DefineComponent } from 'vue';

  // 声明 Editor 组件
  const Editor: DefineComponent<object, object, any>;

  // 声明 Toolbar 组件
  const Toolbar: DefineComponent<object, object, any>;
}
