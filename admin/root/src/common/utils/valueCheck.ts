/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-03 20:09:48
 * @LastEditTime: 2024-11-15 19:32:17
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
  const showError = () => {
    message.error(msg);
    throw new Error(msg);
  };

  // 普通判断
  if (value === null || value === undefined) {
    showError();
  }

  // map判断
  if (value instanceof Map && value.size === 0) {
    showError();
  }

  // 数组判断
  if (Array.isArray(value) && value.length === 0) {
    showError();
  }

  // 字符串判断
  if (typeof value === 'string' && value.trim() === '') {
    showError();
  }
}

/**
 * 检查传入数组参数是否为空
 * @param {T} value - 要检查的数组数据
 * @param {string} msg - 错误信息
 * @param needToCheck - 待检查的某一项，默认直接检查元素
 */
export function checkListEmpty<T>(
  value: T[],
  msg: string = EmptyMsg,
  needToCheck?: (item: T) => any,
) {
  if (!value) {
    message.error(msg);
    throw new Error(msg);
  }

  if (value.length === 0) {
    return;
  }

  value.forEach((item) => {
    if (needToCheck) {
      checkEmpty(needToCheck(item), msg);
    } else {
      checkEmpty(item, msg);
    }
  });
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
  callBack?: (v: boolean) => void,
): Promise<unknown> {
  const res: boolean = await promise;
  const messageStr = isCreate ? '创建' : '修改';
  if (res) {
    message.success(`${msg} ${messageStr}成功!`);
  } else {
    message.error(`${msg} ${messageStr}失败!`);
    throw new Error(`${msg} ${messageStr}失败!`);
  }
  if (callBack) {
    callBack(res);
  }
  return res;
}

export function checkEqual<T>(a: T, b: T, msg: string = '数据不相等!') {
  if (a !== b) {
    message.error(msg);
    throw new Error(msg);
  }
}
