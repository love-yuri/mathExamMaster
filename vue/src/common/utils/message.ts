/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-11 20:09:08
 * @LastEditTime: 2024-08-11 20:49:41
 * @Description: 全局消息通知
 *
 * 如果出现消息无法显示的情况，请检查useToastStore中是否有正确初始化
 */
import { useToastStore } from '@/stores/toast';
import { unref } from 'vue';
import { storeToRefs } from 'pinia';
import type { ToastMessageOptions } from 'primevue/toast';

const { toast } = storeToRefs(useToastStore());

const defaultValue = {
  life: 3000, // 消息显示时间, 单位ms
} as const;

/**
 * 发送通知Toast
 * 基础函数，不检查参数类型
 * 所有消息的基础封装
 */
function sendMsg(msg: ToastMessageOptions): void {
  unref(toast)?.add({
    life: msg.life || defaultValue.life,
    ...msg,
  });
}

function info(msg: ToastMessageOptions | string): void {
  if (typeof msg === 'string') {
    msg = { detail: msg };
  }
  sendMsg({ severity: 'info', ...msg });
}

function success(msg: ToastMessageOptions | string): void {
  if (typeof msg === 'string') {
    msg = { detail: msg };
  }
  sendMsg({ severity: 'success', ...msg });
}

function error(msg: ToastMessageOptions | string): void {
  if (typeof msg === 'string') {
    msg = { detail: msg };
  }
  sendMsg({ severity: 'error', ...msg, life: 5000 });
}

export default {
  success,
  info,
  error,
};
