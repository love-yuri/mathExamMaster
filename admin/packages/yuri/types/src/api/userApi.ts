/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 17:05:12
 * @LastEditTime: 2025-02-12 18:45:34
 * @Description:
 */
import { BaseEntity, type PageParam } from './base';
import type { UserInfo } from '@vben/types';

export class User extends BaseEntity {
  username!: string;

  override reset() {
    this.username = '';
  }
}

export interface UserPageParam extends PageParam {
  studentFlag: number;
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

export interface UserResult {
  id: string;
  username: string;
  nickname: string;
}

export interface SetTeacherParam {
  departmentId: string;
  teacherId: string;
}