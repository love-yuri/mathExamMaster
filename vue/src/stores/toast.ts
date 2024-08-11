/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-11 19:00:18
 * @LastEditTime: 2024-08-11 19:58:58
 * @Description: Toast store
 */
import { StoreNames } from '@/common/constants/storeNames';
import { ref } from 'vue';
import { defineStore } from 'pinia';
import type { ToastServiceMethods } from 'primevue/toastservice';

export const useToastStore = defineStore(StoreNames.Toast, () => {
  const toast = ref<ToastServiceMethods>();

  /**
   * 初始化toast的消息实例
   * 如果没有必要，请不要修改此实例
   */
  function init(ex: ToastServiceMethods): void {
    if (toast.value) return;
    toast.value = ex;
  }

  return { toast, init };
});
