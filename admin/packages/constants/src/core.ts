/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-20 22:54:05
 * @LastEditTime: 2024-10-23 21:09:05
 * @Description:
 */
/**
 * @zh_CN 登陆页面 url 地址
 */
export const LOGIN_PATH = '/auth/login';

/**
 * @zh_CN 默认首页地址
 */
export const DEFAULT_HOME_PATH = '/question/bank';

export interface LanguageOption {
  label: string;
  value: 'en-US' | 'zh-CN';
}

/**
 * Supported languages
 */
export const SUPPORT_LANGUAGES: LanguageOption[] = [
  {
    label: '简体中文',
    value: 'zh-CN',
  },
  {
    label: 'English',
    value: 'en-US',
  },
];
