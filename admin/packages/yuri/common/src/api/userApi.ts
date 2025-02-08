/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 17:05:12
 * @LastEditTime: 2025-02-08 14:35:02
 * @Description:
 */

import { RequestType, type LoginParams, type LoginResult, type PageResult, type Student, type User, type UserPageParam, type UserResult } from '@yuri/types';
import { BaseApi } from '../base/baseApi/baseApi';

class Api extends BaseApi<User> {
  override baseUrl: string = '/user';

  info = async () => this.add<User>(RequestType.POST, '/info');

  /**
   * 登陆
   */
  login = async (params: LoginParams) => {
    return this.add<LoginResult>(RequestType.POST, '/login', params);
  };

  /**
   * 退出登录
   */
  logout = async () => {
    return this.add(RequestType.POST, '/logout');
  };

  /**
   * 分页
   */
  resultPage = async (param: UserPageParam) => {
    return this.add<PageResult<UserResult>>(RequestType.POST, '/page', param);
  };

  /**
   * 登陆
   */
  students = async () => {
    return this.add<Student[]>(RequestType.POST, '/students');
  };
}

export const userApi = new Api();
