/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 17:05:12
 * @LastEditTime: 2025-02-27 16:49:13
 * @Description:
 */

import type {
  LoginParams,
  LoginResult,
  PageResult,
  SetTeacherParam,
  User,
  UserPageParam,
  UserResult,
} from '@yuri/types';

import type { UserInfo } from '@vben/types';

import { RequestType } from '@yuri/types';

import { BaseApi } from '../base/baseApi/baseApi';

class Api extends BaseApi<User> {
  override baseUrl: string = '/user';

  info = async () => this.add<UserInfo>(RequestType.POST, '/info');

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

  setTeacher = async (param: SetTeacherParam) => {
    return this.add<boolean>(RequestType.POST, '/set/teacher', param);
  };

  teachers = async () => {
    return this.add<UserResult[]>(RequestType.POST, '/teachers');
  };
}

export const userApi = new Api();
