/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 17:05:12
 * @LastEditTime: 2024-11-15 18:35:32
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

export interface Student {
  id: string;
  username: string;
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

  /**
   * 登陆
   */
  students = async () => {
    return this.add<Student[]>(RequestType.POST, '/students');
  };
}

export const userApi = new Api();
