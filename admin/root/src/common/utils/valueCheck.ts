/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 20:09:48
 * @LastEditTime: 2024-10-03 20:28:55
 * @Description: 数据检查
 */

import message from './message';

const EmptyMsg = '数据不能为空!!!';

/**
 * 检查传入参数是否为空
 * @param {any} value - 要检查的值
 * @param {string} msg - 错误信息
 */
export function checkEmpty(value: any, msg: string = EmptyMsg) {
  if (!value) {
    message.error(msg);
    throw new Error(msg);
  }

  if (Array.isArray(value) && value.length === 0) {
    message.error(msg);
  } else if (typeof value === 'string' && value.trim() === '') {
    message.error(msg);
  }
}

/**
 * 检查传入的函数执行结果是否成功
 * @param promise 需要检查的数据
 * @param isCreate 是否是创建操作
 * @param msg 基础信息
 * @returns 原始返回值
 * @throws Error 抛出错误
 */
export async function checkSuccess(
  promise: Promise<boolean>,
  isCreate: boolean = true,
  msg: string = '',
): Promise<unknown> {
  const res: boolean = await promise;
  const messageStr = isCreate ? '创建' : '修改';
  if (res) {
    message.success(`${msg} ${messageStr}成功!`);
  } else {
    message.error(`${msg} ${messageStr}失败!`);
    throw new Error(`${msg} ${messageStr}失败!`);
  }
  return res;
}
