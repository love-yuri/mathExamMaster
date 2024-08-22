/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-22 21:03:24
 * @LastEditTime: 2024-08-22 21:04:20
 * @Description: 系统返回代码，需要和后端统一
 */
export enum SystemCode {
  OK = 200,
  AccessTokenError = 400,
  UNAUTHORIZED = 401,
  AuthError = 402,
  InnerError = 500,
  ParameterValidError = 501,
  AccessDenied = 502,
  BizError = 503,
  MissQuery = 504,
  NoResource = 505,
}

export const SystemCodeMessage: { [key in SystemCode]: string } = {
  [SystemCode.OK]: 'success',
  [SystemCode.AccessTokenError]: '用户登录令牌失效',
  [SystemCode.UNAUTHORIZED]: '用户未登录',
  [SystemCode.AuthError]: '用户名或密码错误',
  [SystemCode.InnerError]: '系统内部错误',
  [SystemCode.ParameterValidError]: '参数验证错误',
  [SystemCode.AccessDenied]: '用户没有权限访问',
  [SystemCode.BizError]: '业务逻辑错误',
  [SystemCode.MissQuery]: '缺少必要参数',
  [SystemCode.NoResource]: '没有找到该URL',
};
