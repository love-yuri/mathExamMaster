/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-22 23:38:38
 * @LastEditTime: 2024-08-23 00:06:29
 * @Description: 用户状态
 */
import { StoreNames } from '@/common/constants/storeNames';
import { ref } from 'vue';
import { defineStore } from 'pinia';
import { GetItem, SetItem, StorageType, type LocalUserInfo } from '@/common/utils/localStorageUtils';

export const useUserStore = defineStore(StoreNames.User, () => {
  const user = ref<LocalUserInfo | undefined>(GetItem(StorageType.UserInfo));

  const setInfo = (info: LocalUserInfo) => {
    user.value = info;
    SetItem(StorageType.UserInfo, user.value!);
  };

  /**
   * 获取token
   */
  const getToken = () => {
    return user.value?.token ?? undefined;
  };

  return { user, setInfo, getToken };
});
