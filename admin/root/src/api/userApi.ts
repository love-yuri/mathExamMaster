/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 17:05:12
 * @LastEditTime: 2024-10-03 21:53:49
 * @Description:
 */
import { BaseApi } from '#/common/base/baseApi/baseApi';
import { BaseEntity, RequestType } from '#/common/base/baseApi/types';
import type { UserInfo } from '@vben/types';

export class User extends BaseEntity {
  username!: string;

  override reset() {
    this.username = '';
  }
}

/** 登录接口参数 */
export interface LoginParams {
  password: string;
  username: string;
}

/** 登录接口返回值 */
export interface LoginResult {
  token: string;
  user: UserInfo;
}

export interface RefreshTokenResult {
  data: string;
  status: number;
}

class Api extends BaseApi<User> {
  override baseUrl: string = '/user';

  info = async () => this.add<User>(RequestType.POST, '/info');

  /**
   * 登陆
   */
  login = async (params: LoginParams) => {
    return this.add<LoginResult>(RequestType.POST, '/login', params);
  };
}

export const userApi = new Api();
