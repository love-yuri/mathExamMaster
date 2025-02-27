/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 16:37:25
 * @LastEditTime: 2025-02-07 16:56:47
 * @Description: 认证模块
 */

import type { UserInfo } from '@vben/types';

import { ref } from 'vue';
import { useRouter } from 'vue-router';

import { DEFAULT_HOME_PATH, LOGIN_PATH } from '@vben/constants';
import { resetAllStores, useAccessStore, useUserStore } from '@vben/stores';

import { defineStore } from 'pinia';

import { message, StoreNames } from '@yuri/common';
import { userApi } from '@yuri/common';

export const useAuthStore = defineStore(StoreNames.Auth, () => {
  const accessStore = useAccessStore();
  const userStore = useUserStore();
  const router = useRouter();

  const loginLoading = ref(false);

  /**
   * 异步处理登录操作
   * Asynchronously handle the login process
   * @param params 登录表单数据
   */
  async function authLogin(
    params: LoginAndRegisterParams,
    onSuccess?: () => Promise<void> | void,
  ) {
    try {
      loginLoading.value = true;
      const loginResult = await userApi.login(params);
      const { token: accessToken, user: userInfo } = loginResult;

      // 如果成功获取到 accessToken
      if (userInfo) {
        // 将 accessToken 存储到 accessStore 中
        accessStore.setAccessToken(accessToken);

        // 获取用户信息并存储到 accessStore 中
        userStore.setUserInfo(userInfo);

        if (userInfo.username) {
          message.default.info(`欢饮回来:${userInfo.username}`);
        }
        if (accessStore.loginExpired) {
          accessStore.setLoginExpired(false);
        } else {
          onSuccess
            ? await onSuccess?.()
            : await router.push(DEFAULT_HOME_PATH);
        }
      }
    } finally {
      loginLoading.value = false;
    }
  }

  async function logout(redirect: boolean = true) {
    // 仅在不重定向时调用退出接口
    if (!redirect) {
      await userApi.logout();
    }
    resetAllStores();
    accessStore.setLoginExpired(false);

    // 回登陆页带上当前路由地址
    await router.replace({
      path: LOGIN_PATH,
      query: redirect
        ? {
            redirect: encodeURIComponent(router.currentRoute.value.fullPath),
          }
        : {},
    });
  }

  /**
   * 更新用户信息
   */
  async function fetchUserInfo() {
    const userInfo: UserInfo = await userApi.info();
    userStore.setUserInfo(userInfo);
    return userInfo;
  }

  function $reset() {
    loginLoading.value = false;
  }

  return {
    $reset,
    authLogin,
    fetchUserInfo,
    loginLoading,
    logout,
  };
});
