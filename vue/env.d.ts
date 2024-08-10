/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-04-11 22:51:42
 * @LastEditTime: 2024-04-11 22:56:28
 * @Description: vite env 环境变量
 */
/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_BASE_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
